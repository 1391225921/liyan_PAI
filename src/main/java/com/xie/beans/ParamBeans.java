package com.xie.beans;

import com.jayway.jsonpath.JsonPath;
import com.xie.util.HttpUtil;

public class ParamBeans {
	private String url;
	private String params;
	private String ret;
	private String id;
	private int is_special;

	public String getUrl() {
		return url;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String toString() {
		return "id=" + id + ",url=" + url + ",params=" + params + ",ret=" + ret
				+ "";
	}

	public int getIs_special() {
		return is_special;
	}

	public void setIs_special(int is_special) {
		this.is_special = is_special;
	}

	public String getToken() {
		HttpUtil httpUtil = new HttpUtil();
		String entity = "username=谢家玲&password=123456";
		// 执行post，获取返回值
		String result = httpUtil.post(
				"http://qysys.liyantech.cn/app/webLogin.action", entity);
		System.out.println("result-----" + result);
		String token = JsonPath.read(result, "$.result.token");
		System.out.println(token);
		return token;
	}

}
