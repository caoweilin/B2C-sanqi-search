package com.sanqi.search.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanqi.web.httpclient.HttpResult;

/**
*@author作者weilin
*@version 创建时间:2019年5月2日上午11:28:16
*类说明
*/
@Service
public class ApiService implements BeanFactoryAware{

	private BeanFactory beanFactory;
	
	@Autowired
	private RequestConfig requestConfig;
	
	
	/*
	 * GET请求，响应200，返回响应内容，响应404，500返回null
	 * 
	 * */
	public String doGet(String url) throws ClientProtocolException, IOException {
		
	        // 创建http GET请求
	        HttpGet httpGet = new HttpGet(url);
	        httpGet.setConfig(requestConfig);
	        CloseableHttpResponse response = null;
	        try {
	            // 执行请求
	            response = getHttpclient().execute(httpGet);
	            // 判断返回状态是否为200
	            if (response.getStatusLine().getStatusCode() == 200) {
	                return EntityUtils.toString(response.getEntity(), "UTF-8");
	            }
	        } finally {
	            if (response != null) {
	                response.close();
	            }
	            
	        }
			return null;
	}
	
	/*
	 * 带有参数的GET请求
	 * 
	 * */
	
	public String doGet(String url, Map<String, String> params) throws ClientProtocolException, IOException, URISyntaxException {
		URIBuilder builder = new URIBuilder(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.setParameter(entry.getKey(), entry.getValue());
		}
		return this.doGet(builder.build().toString());
	}
	
	/*
	 * 带有参数的POST请求，返回响应码和内容
	 * 
	 * */
	
	public HttpResult doPost(String url,Map<String, String> params) throws ClientProtocolException, IOException {

        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
        if(params != null) {
        	for (Map.Entry<String, String> entry : params.entrySet()) {
        	    parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        	}
        }
        
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(formEntity);

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = getHttpclient().execute(httpPost);
            return new HttpResult(response.getStatusLine().getStatusCode(),EntityUtils.toString(response.getEntity(), "UTF-8"));
        
        } finally {
            if (response != null) {
                response.close();
            }
        }
	}

	
	/*
	 * POST请求，返回响应码和内容
	 * 
	 * */
	public HttpResult doPost(String url) throws ClientProtocolException, IOException {
		return this.doPost(url,null);
	}

	/*
	 * 单例对象使用多例对象实现beanfactoryaware接口
	 * 
	 * */
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		//spring容器初始化时调用该方法，传入beanfactory
		this.beanFactory = beanFactory;
	}
	
	private CloseableHttpClient getHttpclient() {
		return this.beanFactory.getBean(CloseableHttpClient.class);
	}
	
}
