package com.xie.learnmybatis;

import java.util.List;

import junit.framework.Assert;

import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;
import com.xie.beans.ParamBeans;
import com.xie.tools.DBtools;
import com.xie.util.HttpUtil;

public class DataProviderTest {
	private static Object[][] obj;
	String token = "";
	static DBtools dBtools = new DBtools();
	static SqlSession session = dBtools.getSession("mybatis1.cfg.xml");
	static List<ParamBeans> list = session
			.selectList("mymapper1.selecttestcase");

	@DataProvider(name = "test")
	public static Object[][] testStart() {
		// 初始化参数个数
		obj = new Object[list.size()][];
		for (int i = 0; i < list.size(); i++) {
			obj[i] = new Object[] { list.get(i) };
		}
		return obj;
	}

	@Test(dataProvider = "test")
	public void testAdd2(ParamBeans s) {
		String expJson = "";
		String result = "";
		String id = "";
		String userId = "";
		JsonObject returnData = new JsonObject();
		HttpUtil httpUtil = new HttpUtil();
		expJson = s.getExp_json();
		returnData = new JsonParser().parse(s.getParams()).getAsJsonObject();// 把string类型的参数转成json格式
		if (s.getParams() != null) {
			if (s.getIs_need_token() == 1) {
				result = httpUtil.post(s.getUrl(), token, returnData);
			} else {
				// 执行post，获取返回值
				result = httpUtil.post(s.getUrl(), returnData);
			}
			System.out.println("result为" + result);
			if (result.contains("token")) {
				token = JsonPath.read(result, "$.result.token");
				System.out.println("token" + token);
				expJson = expJson.replace("tihuan_token", token);
			}
			if (result.contains("id")) {
				id = JsonPath.read(result, "$.result.id");
				expJson = expJson.replace("tihuan_id", id);
			}
			if (result.contains("userId")) {
				userId = JsonPath.read(result, "$.result.userId");
				expJson = expJson.replace("tihuan_userId", userId);
			}
		} else {
			result = httpUtil.post(s.getUrl(), token);
		}
		JsonObject a = new JsonParser().parse(result).getAsJsonObject();
		JsonObject b = new JsonParser().parse(expJson).getAsJsonObject();
		Assert.assertEquals(a, b);
		System.out.println("接口:" + s.getRemarks() + "测试成功");
	}
}
