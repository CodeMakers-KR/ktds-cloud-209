package com.example.orderservice.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderservice.client.ItemServiceClient;
import com.example.orderservice.exceptions.OrderException;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrderVO;
import com.example.orderservice.vo.RequestUpdateItemVO;
import com.example.orderservice.vo.ResponseItemVO;
import com.example.orderservice.vo.ResponseOrderVO;

import feign.FeignException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/order-service")
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private ItemServiceClient itemServiceClient;

	@Autowired
	private OrderService orderService;

	@PostMapping("/{userId}/orders")
	public ResponseEntity<? extends Object> makeNewOrder(
			@PathVariable String userId,
			@RequestHeader("USER_ID") String authUserId, 
			@RequestBody @Valid RequestOrderVO requestOrderVO,
			BindingResult bindingResult) {

		if (!userId.equals(authUserId)) {
			// Method Not Allowed
			return new ResponseEntity<>(HttpStatusCode.valueOf(405));
		}

		if (bindingResult.hasErrors()) {
			throw new OrderException(bindingResult.getFieldErrors());
		}

		try {
			ResponseItemVO itemInfo = this.itemServiceClient.getItem(requestOrderVO.getItemId());
			logger.info("Item ID: {}", itemInfo.getItemId());
			logger.info("Item Name: {}", itemInfo.getItemName());
			logger.info("Item Stock: {}", itemInfo.getStock());

			if (itemInfo.getStock() < requestOrderVO.getItemOrderCount()) {
				return new ResponseEntity<String>("재고가 부족합니다.", HttpStatusCode.valueOf(400));
			}
		} catch (FeignException fe) {
			logger.error(fe.getMessage(), fe);
		}

		requestOrderVO.setUserId(userId);
		ResponseOrderVO newOrder = this.orderService.createNewOrder(requestOrderVO);

		RequestUpdateItemVO requestUpdateItemVO = new RequestUpdateItemVO();
		requestUpdateItemVO.setItemId(requestOrderVO.getItemId());
		requestUpdateItemVO.setStock(requestOrderVO.getItemOrderCount());

		try {
			ResponseItemVO responseItemVO = this.itemServiceClient.updateItemStock(requestOrderVO.getItemId(),
					requestUpdateItemVO);
			logger.info("Item ID: {}", responseItemVO.getItemId());
			logger.info("Item Name: {}", responseItemVO.getItemName());
			logger.info("Item Stock: {}", responseItemVO.getStock());
		} catch (FeignException fe) {
			logger.error(fe.getMessage(), fe);
			this.orderService.deleteOrder(newOrder.getOrderId());
			
			return new ResponseEntity<ResponseOrderVO>(HttpStatusCode.valueOf(400));
		}

		return new ResponseEntity<ResponseOrderVO>(newOrder, HttpStatusCode.valueOf(201));
	}

	@GetMapping("/{userId}/orders")
	public ResponseEntity<List<ResponseOrderVO>> fetchAllOrders(
			@PathVariable String userId,
			@RequestHeader("USER_ID") String authUserId) {

		if (!userId.equals(authUserId)) {
			// Method Not Allowed
			return new ResponseEntity<>(HttpStatusCode.valueOf(405));
		}

		List<ResponseOrderVO> usersOrderList = this.orderService.fetchAllOrdersByUserId(userId);
		return new ResponseEntity<>(usersOrderList, HttpStatus.OK);
	}

}
