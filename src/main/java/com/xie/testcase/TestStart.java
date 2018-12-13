package com.xie.testcase;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;
import com.xie.beans.ParamBeans;
import com.xie.dataprovider.DataProvider;
import com.xie.tools.DBtools;
import com.xie.util.HttpUtil;

public class TestStart {

	@Test
	public static void testStart() {
		// List<NameValuePair> params = new ArrayList<NameValuePair>();
		String token = "";
		String expJson = "";
		String result = "";
		String entity = "";
		String id = "";
		JsonObject returnData=new JsonObject();
		DataProvider dataProvider = new DataProvider();
		HttpUtil httpUtil = new HttpUtil();
		DBtools dBtools = new DBtools();
		SqlSession session = dBtools.getSession("mybatis1.cfg.xml");
		List<ParamBeans> list = session.selectList("mymapper1.selecttestcase");
		for (int i = 0; i < list.size(); i++) {
			entity = list.get(i).getParams();			
			returnData = new JsonParser().parse(entity)
					.getAsJsonObject();//把string类型的参数转成json格式
			if (list.get(i).getIs_need_token() == 1) {
			//	entity = entity + "&token=" + dataProvider.getToken();
				result = httpUtil.post(list.get(i).getUrl(), dataProvider.getToken(),returnData);
			} else {
				// 执行post，获取返回值
				result = httpUtil.post(list.get(i).getUrl(), returnData);
			}
			expJson = list.get(i).getExp_json();
			if (result.contains("token")) {
				token = JsonPath.read(result, "$.result.token");
				expJson = expJson.replace("tihuan", token);
			}
			if (result.contains("id")) {
				id = JsonPath.read(result, "$.result.id");
				expJson = expJson.replace("tihuan", id);
			}
			Assert.assertEquals(result, expJson);
			
			System.out.println("接口" + i + "测试成功");
		}
	}
}
