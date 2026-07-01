package com.example.orderservice.service;

import java.util.List;

import com.example.orderservice.vo.RequestOrderVO;
import com.example.orderservice.vo.ResponseOrderVO;

public interface OrderService {

	ResponseOrderVO createNewOrder(RequestOrderVO requestOrderVO);
	
	List<ResponseOrderVO> fetchAllOrdersByUserId(String userId);

	boolean deleteOrder(String orderId);
}
