package com.xie.dataprovider;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jayway.jsonpath.JsonPath;
import com.xie.beans.ParamBeans;
import com.xie.tools.DBtools;
import com.xie.util.HttpUtil;

public class DataProvider {
	HttpUtil httpUtil = new HttpUtil();
	DBtools dBtools = new DBtools();
	SqlSession sqlSession = dBtools.getSession("mybatis1.cfg.xml");
	List<ParamBeans> list = sqlSession.selectList("mymapper1.selecttestcase");

	public String getToken() {
		String entity = list.get(0).getParams();
		// 执行post，获取返回值
		String result = httpUtil.post(list.get(0).getUrl(), entity);
		String token = JsonPath.read(result, "$.result.token");
		System.out.println("token为:"+token);
		return token;
	}

	public void insertUserInform() {
		DBtools dBtools = new DBtools();
		SqlSession session = dBtools.getSession("mybatis2.cfg.xml");
		// session.insert("mymapper2.insertuserinform",);
	}
}