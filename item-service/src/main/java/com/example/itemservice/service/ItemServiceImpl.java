package com.example.itemservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.itemservice.dao.ItemDao;
import com.example.itemservice.vo.CreateItemVO;
import com.example.itemservice.vo.RequestUpdateItemVO;
import com.example.itemservice.vo.ResponseItemVO;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemDao itemDao;

	@Override
	public List<ResponseItemVO> fetchAllItems() {
		return this.itemDao.selectAllItems();
	}

	@Override
	public ResponseItemVO createItems(CreateItemVO createItemVO) {
		int count = this.itemDao.insertItem(createItemVO);
		if (count == 0) {
			return null;
		}

		return this.itemDao.selectItemById(createItemVO.getItemId());
	}

	@Override
	public ResponseItemVO updateItemStock(RequestUpdateItemVO requestUpdateItemVO) {
		this.itemDao.updateItemStock(requestUpdateItemVO);
		return this.itemDao.selectItemByItemId(requestUpdateItemVO.getItemId());
	}

	@Override
	public ResponseItemVO fetchOneItem(String itemId) {
		return this.itemDao.selectItemByItemId(itemId);
	}

}
