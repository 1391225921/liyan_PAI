package com.xie.util;

import java.io.IOException;

import org.apache.http.Consts;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;

public class HttpUtil {
	// 带token的post请求(无参数，token放在http头部)
	public String post(String url, String token) {
		// 创建默认HttpClient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		String result = "";
		try {
			// 设置header里的token
			httpPost.addHeader("access-token", token);
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

	// 不带token的post请求(json格式)
	public String post(String url, JsonObject data) {
		// 创建默认HttpClient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// post请求
		HttpPost httpPost = new HttpPost(url);
		String result = "";
		try {
			// 新建String类型entity
			StringEntity entity = new StringEntity(data.toString(), "utf-8");
			// entity的类型为json
			entity.setContentType("application/json");
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

	// 带token的post请求(json格式，token在头部)
	public String post(String url, String token, JsonObject data) {
		// 创建默认HttpClient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// post请求
		HttpPost httpPost = new HttpPost(url);
		String result = "";
		try {
			httpPost.addHeader("access-token", token);
			// 新建String类型entity
			StringEntity entity = new StringEntity(data.toString(), "utf-8");
			// entity的类型为json
			entity.setContentType("application/json");
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
