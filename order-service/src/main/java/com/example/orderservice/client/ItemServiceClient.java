package com.example.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.orderservice.vo.RequestUpdateItemVO;
import com.example.orderservice.vo.ResponseItemVO;

@FeignClient(name = "ITEM-SERVICE", 
			 fallback = com.example.orderservice.client.ItemServiceClient.ItemFallback.class)
public interface ItemServiceClient {

	@PutMapping("/item-service/{itemId}/items")
	public ResponseItemVO updateItemStock(@PathVariable String itemId,
			@RequestBody RequestUpdateItemVO requestUpdateItemVO);

	@GetMapping("/item-service/items/{itemId}")
	public ResponseItemVO getItem(@PathVariable String itemId);
	
	@Component
	public class ItemFallback implements ItemServiceClient {

		@Override
		public ResponseItemVO updateItemStock(String itemId, RequestUpdateItemVO requestUpdateItemVO) {
			return null;
		}

		@Override
		public ResponseItemVO getItem(String itemId) {
			return null;
		}
		
	}
}
