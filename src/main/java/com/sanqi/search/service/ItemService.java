package com.sanqi.search.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanqi.search.bean.Item;

/**
*@author作者weilin
*@version 创建时间:2019年7月10日下午8:24:33
*类说明
*/
@Service
public class ItemService {

	@Autowired
	private ApiService apiService;
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	public Item queryById(Long itemId) {
		String url = "http://manage.sanqi.com/rest/api/item"+itemId;
		try {
			String jsonData = this.apiService.doGet(url);
			return mapper.readValue(jsonData, Item.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
}
