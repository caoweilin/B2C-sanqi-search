package com.sanqi.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sanqi.search.bean.SearchResult;
import com.sanqi.search.service.SearchService;

/**
*@author作者weilin
*@version 创建时间:2019年7月4日上午10:33:11
*类说明
*/
@Controller
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	private static final  Integer ROWS = 32;
	
	@RequestMapping(value="search",method=RequestMethod.GET)
	public ModelAndView search(@RequestParam("q")String keyWords,@RequestParam(value="page",
			defaultValue="1")Integer page) {
		ModelAndView mv = new ModelAndView("search");
		SearchResult searchResult = null;
		try {
			keyWords = new String(keyWords.getBytes("ISO-8859-1"),"UTF-8");
			searchResult = this.searchService.search(keyWords, page, ROWS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			searchResult = new SearchResult(0L, null);
		}
		//搜索关键字
		mv.addObject("query",keyWords);

		//搜索结果集
		mv.addObject("itemList",searchResult.getList());
		
		//当前页数
		mv.addObject("page",page);
		
		int total = searchResult.getTotal().intValue();
		int pages = total%ROWS==0 ? total%ROWS : total%ROWS+1;

		//总页数
		mv.addObject("pages",pages);
		
		return mv;
		
	}

}
