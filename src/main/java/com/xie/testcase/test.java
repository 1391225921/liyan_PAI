package com.xie.testcase;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;
import com.xie.beans.ParamBeans;
import com.xie.beans.beans;
import com.xie.tools.DBtools;
import com.xie.util.HttpUtil;

public class test {

	@Test
	public static void rightLogin() {
		// List<NameValuePair> params = new ArrayList<NameValuePair>();
		String token = "";
		String exp_json = "";
		ParamBeans beans = new ParamBeans();
		HttpUtil httpUtil = new HttpUtil();
		DBtools dBtools = new DBtools();
		SqlSession session1 = dBtools.getSession("mybatis1.cfg.xml");
		List<ParamBeans> list = session1
				.selectList("mymapper1.selectrightlogin");
		// SqlSession session2 = dBtools.getSession("mybatis2.cfg.xml");
		// List<beans> list1 = session2.selectList("mymapper2.selectarticlid");

		for (int i = 0; i < list.size(); i++) {
			String entity = list.get(i).getParams();
			// params.add(new BasicNameValuePair("username",
			// paramBeans.getUserName()));
			// params.add(new BasicNameValuePair("password",
			// paramBeans.getPassword()));
			if (list.get(i).getIs_need_token() == 1) {
				entity = entity + "&token=" + beans.getToken();
			}
			System.out.println("entity为：" + entity);
			// 执行post，获取返回值
			String result1 = httpUtil.post(list.get(i).getUrl(), entity);
			exp_json = list.get(i).getExp_json();
			if (result1.contains("token")) {
				token = JsonPath.read(result1, "$.result.token");
				exp_json = exp_json.replace("tihuan", token);
			}
			Assert.assertEquals(result1, exp_json);
		}
	}
}
