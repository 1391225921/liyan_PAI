package com.xie.tools;

import java.io.Reader;
import java.util.Objects;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBtools {

	public static SqlSessionFactory sessionFactory;

	// 创建能执行映射文件中sql的sqlSession
	public SqlSession getSession(String datasource) {
		try {
			// 使用MyBatis提供的Resources类加载mybatis的配置文件
			Reader reader = Resources.getResourceAsReader(datasource);
			// 构建sqlSession的工厂
			sessionFactory = new SqlSessionFactoryBuilder().build(reader);
			if (Objects.isNull(sessionFactory)) {
				throw new NullPointerException("session为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sessionFactory.openSession();
	}
}
