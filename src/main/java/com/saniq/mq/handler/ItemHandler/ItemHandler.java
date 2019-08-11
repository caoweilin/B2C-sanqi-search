package com.saniq.mq.handler.ItemHandler;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanqi.search.bean.Item;
import com.sanqi.search.service.ItemService;

/**
*@author作者weilin
*@version 创建时间:2019年7月10日上午10:13:02
*类说明
*/
public class ItemHandler {

	@Autowired
	private HttpSolrServer httpSolrServer;
	
	@Autowired
	private ItemService itemService;
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	private void execute(String msg) {
		try {
			JsonNode jsonNode = mapper.readTree(msg);
			long itemId = jsonNode.get("itemId").asLong();
			String type = jsonNode.get("type").asText();
			
			if(StringUtils.equals(type, "insert") || StringUtils.equals(type, "update")) {
				Item item = this.itemService.queryById(itemId);
				try {
					this.httpSolrServer.addBean(item);
					this.httpSolrServer.commit();
				} catch (SolrServerException e) {
					e.printStackTrace();
				}
			}else if(StringUtils.equals(type, "delete")) {
				try {
					this.httpSolrServer.deleteById(String.valueOf(itemId));
					this.httpSolrServer.commit();
				} catch (SolrServerException e) {
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
