package com.sanqi.web.httpclient;
/**
*@author作者weilin
*@version 创建时间:2019年5月2日下午12:44:44
*类说明
*/
public class HttpResult {
	
	private Integer code;
	
	private String body;

	public HttpResult() {
		super();
	}

	public HttpResult(Integer code, String body) {
		super();
		this.code = code;
		this.body = body;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
