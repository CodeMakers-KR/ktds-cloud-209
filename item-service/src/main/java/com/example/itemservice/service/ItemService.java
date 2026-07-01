package com.example.itemservice.service;

import java.util.List;

import com.example.itemservice.vo.CreateItemVO;
import com.example.itemservice.vo.RequestUpdateItemVO;
import com.example.itemservice.vo.ResponseItemVO;

public interface ItemService {

	List<ResponseItemVO> fetchAllItems();
	
	ResponseItemVO createItems(CreateItemVO createItemVO);

	ResponseItemVO updateItemStock(RequestUpdateItemVO requestUpdateItemVO);

	ResponseItemVO fetchOneItem(String itemId);
	
}
