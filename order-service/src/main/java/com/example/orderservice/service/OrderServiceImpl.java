package com.example.orderservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.orderservice.dao.OrderDao;
import com.example.orderservice.vo.RequestOrderVO;
import com.example.orderservice.vo.ResponseOrderVO;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Override
	public ResponseOrderVO createNewOrder(RequestOrderVO requestOrderVO) {
		int count = this.orderDao.insertNewOrder(requestOrderVO);
		if (count == 0) {
			return null;
		}
		return this.orderDao.selectOneOrderByOrderId(requestOrderVO.getOrderId());

	}

	@Override
	public List<ResponseOrderVO> fetchAllOrdersByUserId(String userId) {
		return this.orderDao.selectAllOrdersByUserId(userId);
	}

	@Override
	public boolean deleteOrder(String orderId) {
		return this.orderDao.deleteOrder(orderId) == 1;
	}

}





