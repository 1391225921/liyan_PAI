package com.xie.testcase;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;
import com.xie.beans.ParamBeans;
import com.xie.tools.DBtools;
import com.xie.util.HttpUtil;

public class TestStart {

	@Test
	public static void testStart() {
		String token = "";
		String expJson = "";
		String result = "";
		String entity = "";
		String id = "";
		String userId = "";
		JsonObject returnData = new JsonObject();
		HttpUtil httpUtil = new HttpUtil();
		DBtools dBtools = new DBtools();
		SqlSession session = dBtools.getSession("mybatis1.cfg.xml");
		List<ParamBeans> list = session.selectList("mymapper1.selecttestcase");
		for (int i = 0; i < list.size(); i++) {
			entity = list.get(i).getParams();
			expJson = list.get(i).getExp_json();

			if (entity != null) {
				returnData = new JsonParser().parse(entity).getAsJsonObject();// 把string类型的参数转成json格式
				if (list.get(i).getIs_need_token() == 1) {
					result = httpUtil.post(list.get(i).getUrl(), token,
							returnData);
				} else {
					// 执行post，获取返回值
					result = httpUtil.post(list.get(i).getUrl(), returnData);
				}
				if (result.contains("token")) {
					token = JsonPath.read(result, "$.result.token");
					expJson = expJson.replace("tihuan", token);
				}
				if (result.contains("id")) {
					id = JsonPath.read(result, "$.result.id");
					expJson = expJson.replace("tihuan", id);
				}
				if (result.contains("userId")) {
					userId = JsonPath.read(result, "$.result.userId");
					expJson = expJson.replace("tihuan", userId);
				}
			} else {
				result = httpUtil.post(list.get(i).getUrl(), token);
			}
			JsonObject a = new JsonParser().parse(result).getAsJsonObject();
			JsonObject b = new JsonParser().parse(expJson).getAsJsonObject();
			Assert.assertEquals(a, b);
			System.out.println("接口:" + list.get(i).getRemarks() + "测试成功");
		}
	}
}
