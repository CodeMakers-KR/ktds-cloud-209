package com.example.userservice.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.userservice.vo.ResponseOrderVO;

@FeignClient(name = "ORDER-SERVICE", 
			 fallback = com.example.userservice.client.OrderServiceClient.OrderFallback.class)
public interface OrderServiceClient {

	@GetMapping("/order-service/{userId}/orders")
	public List<ResponseOrderVO> fetchAllOrders(@PathVariable String userId);
	
	@Component
	public class OrderFallback implements OrderServiceClient {

		@Override
		public List<ResponseOrderVO> fetchAllOrders(String userId) {
			return new ArrayList<>();
		}
		
	}
}
