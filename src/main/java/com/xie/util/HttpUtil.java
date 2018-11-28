package com.xie.util;

import java.io.IOException;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	// 不带token的post请求
	public String post(String url, String params) {
		// 创建默认的httpclient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		String result = "";
		try {
			// 填充post实体，类型为x-www-form-urlencoded
			StringEntity entity = new StringEntity(params, "UTF-8");
			// StringEntity entity=new
			// UrlEncodedFormEntity(params,Consts.UTF_8);
			entity.setContentType("application/x-www-form-urlencoded");
			httpPost.setEntity(entity);
			try {
				// 获取response
				CloseableHttpResponse response = httpClient.execute(httpPost);
				try {
					result = EntityUtils.toString(response.getEntity(),
							Consts.UTF_8);
				} catch (Exception e) {
					response.close();
				}
			} catch (Exception e) {
				httpClient.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("接口返回的json为："+result);
		return result;
	}

	// 带token的post请求
	public String post(String url, String token, List<NameValuePair> params) {
		// 创建默认HttpClient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		String result = "";
		try {
			// 设置header里的token
			httpPost.addHeader("access-token", token);
			// 填充post实体,类型为x-www-form-urlencoded, UTF-8
			StringEntity entity = new UrlEncodedFormEntity(params, Consts.UTF_8);
			// 填充post实体
			httpPost.setEntity(entity);
			try {
				// 获取response
				CloseableHttpResponse response = httpClient.execute(httpPost);
				try {
					result = EntityUtils.toString(response.getEntity(),
							Consts.UTF_8);
				} catch (Exception e) {
					e.printStackTrace();
					response.close();
				} finally {
					response.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
				httpClient.close();
			} finally {
				httpClient.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;

	}
}
