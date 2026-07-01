package com.example.orderservice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.orderservice.vo.RequestOrderVO;
import com.example.orderservice.vo.ResponseOrderVO;

@Mapper
public interface OrderDao {

	int insertNewOrder(RequestOrderVO requestOrderVO);

	ResponseOrderVO selectOneOrderByOrderId(String orderId);
	
	List<ResponseOrderVO> selectAllOrdersByUserId(String userId);

	int deleteOrder(String orderId);
}