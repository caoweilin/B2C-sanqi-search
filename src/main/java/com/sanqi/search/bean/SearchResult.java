package com.sanqi.search.bean;

import java.util.List;

/**
*@author作者weilin
*@version 创建时间:2019年7月4日下午7:49:06
*类说明
*/
public class SearchResult {

	private Long total;
	
	private List<?> list;
	
	public SearchResult(){
		
	}
	
	public SearchResult(Long total, List<?> list) {
		super();
		this.total = total;
		this.list = list;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
	
	
}
