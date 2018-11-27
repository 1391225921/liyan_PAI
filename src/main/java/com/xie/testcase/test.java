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
	// public static void main(String[] args) {
	// List<NameValuePair> params = new ArrayList<NameValuePair>();
	// SqlSession session = DBtools.getSession();
	// ParamBeans paramBeans =
	// session.selectOne("mymapper.selectrightlogin");
	// System.out.println(paramBeans.getUserName());
	// params.add(new BasicNameValuePair("username",
	// paramBeans.getUserName()));
	// params.add(new BasicNameValuePair("password",
	// paramBeans.getPassword()));
	// rightLogin();
	// }

	@Test
	public static void rightLogin() {
		// List<NameValuePair> params = new ArrayList<NameValuePair>();
		ParamBeans beans = new ParamBeans();
		// beans.getToken();
		String actRet = "";
		HttpUtil httpUtil = new HttpUtil();
		DBtools dBtools = new DBtools();
		SqlSession session1 = dBtools.getSession("mybatis1.cfg.xml");
		List<ParamBeans> list = session1
				.selectList("mymapper1.selectrightlogin");
		SqlSession session2 = dBtools.getSession("mybatis2.cfg.xml");
		List<beans> list1 = session2.selectList("mymapper2.selectarticlid");
		
		for (beans user1 : list1) {
			System.out.println(user1.toString());
		}
		for (ParamBeans user : list) {
			System.out.println(user.toString());
		}
		for (int i = 0; i < list.size(); i++) {// ParamBeans paramBeans : list
			String entity = list.get(i).getParams();
			// params.add(new BasicNameValuePair("username",
			// paramBeans.getUserName()));
			// params.add(new BasicNameValuePair("password",
			// paramBeans.getPassword()));
			if (list.get(i).getIs_special() == 1) {
				entity = entity +"&token=" +beans.getToken();
				System.out.println("带token的entity："+entity);
				// 执行post，获取返回值
				String result1 = httpUtil.post(list.get(i).getUrl(), entity);
//				String token = JsonPath.read(result1, "$..browsingNum");
				System.out.println(JsonPath.read(result1, "$..browsingNum"));
				Assert.assertEquals(result1, list.get(i).getRet());
			} else {
				try {
					// 执行post，获取返回值
					String result2 = httpUtil.post(list.get(i).getUrl(), entity);
					// 把返回值转成json格式
					JsonObject resultData = new JsonParser().parse(result2)
							.getAsJsonObject();
					// 获取返回值中的ret值
					actRet = resultData.get("ret").toString();
					System.out.println(actRet);
					// 验证返回的ret值和期望值是否一致
					Assert.assertEquals(actRet, list.get(i).getRet());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
