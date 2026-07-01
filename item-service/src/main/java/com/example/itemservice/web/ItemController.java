package com.example.itemservice.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.itemservice.exceptions.ItemException;
import com.example.itemservice.service.ItemService;
import com.example.itemservice.vo.CreateItemVO;
import com.example.itemservice.vo.RequestUpdateItemVO;
import com.example.itemservice.vo.ResponseItemVO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/item-service")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@GetMapping("/items")
	public ResponseEntity<List<ResponseItemVO>> getAllItems() {
		List<ResponseItemVO> itemList = this.itemService.fetchAllItems();
		return new ResponseEntity<List<ResponseItemVO>>(itemList, HttpStatusCode.valueOf(200));
	}
	
	@GetMapping("/items/{itemId}")
	public ResponseEntity<ResponseItemVO> getItem(@PathVariable String itemId) {
		ResponseItemVO item = this.itemService.fetchOneItem(itemId);
		return new ResponseEntity<>(item, HttpStatusCode.valueOf(200));
	}

	@PostMapping("/items")
	public ResponseEntity<ResponseItemVO> createItems(
			@Valid @RequestBody CreateItemVO createItemVO,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ItemException(bindingResult.getFieldErrors());
		}

		ResponseItemVO responseItem = this.itemService.createItems(createItemVO);
		return new ResponseEntity<>(responseItem, HttpStatusCode.valueOf(201));
	}

	@PutMapping("/{itemId}/items")
	public ResponseEntity<ResponseItemVO> updateItemStock(
			@PathVariable String itemId,
			@RequestBody RequestUpdateItemVO requestUpdateItemVO) {
		requestUpdateItemVO.setItemId(itemId);
		ResponseItemVO item = this.itemService.updateItemStock(requestUpdateItemVO);

		return new ResponseEntity<ResponseItemVO>(item, HttpStatus.OK);
	}

}
