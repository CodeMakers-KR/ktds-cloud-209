package com.example.itemservice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.itemservice.vo.CreateItemVO;
import com.example.itemservice.vo.RequestUpdateItemVO;
import com.example.itemservice.vo.ResponseItemVO;

@Mapper
public interface ItemDao {

	List<ResponseItemVO> selectAllItems();

	int insertItem(CreateItemVO createItemVO);

	ResponseItemVO selectItemById(String id);

	int updateItemStock(RequestUpdateItemVO requestUpdateItemVO);

	ResponseItemVO selectItemByItemId(String itemId);

}
