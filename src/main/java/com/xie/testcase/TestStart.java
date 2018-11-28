package com.xie.testcase;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.xie.beans.ParamBeans;
import com.xie.dataprovider.DataProvider;
import com.xie.tools.DBtools;
import com.xie.util.HttpUtil;

public class TestStart {

	@Test
	public static void rightLogin() {
		// List<NameValuePair> params = new ArrayList<NameValuePair>();
		String token = "";
		String expJson = "";
		String result = "";
		String entity = "";
		DataProvider dataProvider = new DataProvider();
		HttpUtil httpUtil = new HttpUtil();
		DBtools dBtools = new DBtools();
		SqlSession session = dBtools.getSession("mybatis1.cfg.xml");
		List<ParamBeans> list = session.selectList("mymapper1.selecttestcase");
		for (int i = 0; i < list.size(); i++) {
			entity = list.get(i).getParams();
			// params.add(new BasicNameValuePair("username",
			// paramBeans.getUserName()));
			// params.add(new BasicNameValuePair("password",
			// paramBeans.getPassword()));
			if (list.get(i).getIs_need_token() == 1) {
				entity = entity + "&token=" + dataProvider.getToken();
			}
			System.out.println("entity为：" + entity);
			// 执行post，获取返回值
			result = httpUtil.post(list.get(i).getUrl(), entity);
			expJson = list.get(i).getExp_json();
			if (result.contains("token")) {
				token = JsonPath.read(result, "$.result.token");
				expJson = expJson.replace("tihuan", token);
			}
			Assert.assertEquals(result, expJson);
			System.out.println("接口" + i + "测试成功");
		}
	}
}
