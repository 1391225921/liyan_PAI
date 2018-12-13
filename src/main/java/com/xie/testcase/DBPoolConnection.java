package com.xie.testcase;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class DBPoolConnection {

	private static DruidDataSource druidDataSource = null;

	static {
				
		//Properties properties = loadPropertiesFile("resource/db_server.properties" 1);
		try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(new File("resource/db_server.properties")));
			Properties properties = new Properties();
			properties.load(new InputStreamReader(inputStream, "UTF-8"));
			
			druidDataSource = (DruidDataSource) DruidDataSourceFactory
					.createDataSource(properties); // DruidDataSrouce工厂模式
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 返回druid数据库连接
	 * 
	 * @return
	 * @throws SQLException
	 */
	public DruidPooledConnection getConnection() throws SQLException {
		return druidDataSource.getConnection();
	}

	private static Object getCellValue(Cell cell) {
		Object obj = null;
		switch (cell.getCellType()) {
		case BOOLEAN:
			obj = cell.getBooleanCellValue();
			break;
		case ERROR:
			obj = cell.getErrorCellValue();
			break;
		case NUMERIC:
			obj = cell.getNumericCellValue();
			break;
		case STRING:
			obj = cell.getStringCellValue();
			break;
		default:
			break;
		}
		return obj;
	}

	@Test
	public void testttt() throws IOException {
		int z = 0;
		File file = new File("F:\\t_user.xlsx");
		InputStream in = new FileInputStream(file);
		new ArrayList<>();
		String tableName = "";
		Workbook wb = new XSSFWorkbook(in);
		
		PreparedStatement stmt = null;
        /**
		 * 设置当前excel中sheet的下标：0开始
		 */
		Sheet sheet = wb.getSheetAt(0);
		if (sheet != null) {
			/**
			 * 获取总行数，为循环列做准备
			 */
			int rowNos = sheet.getLastRowNum();// 得到excel的总记录条数
			for (int i = 0; i <= rowNos; i++) {// 遍历行
				List<Object> rowValue = new LinkedList<Object>();
				new LinkedList<String>();
				Row row = sheet.getRow(i);
				if (row != null) {
					/**
					 * 获取总列数，为循环列做准备
					 */
					int columNos = row.getLastCellNum();
					for (int j = 0; j < columNos; j++) {
						Cell cell = row.getCell(j);
						// 获取table名称
						if (cell.toString().contains("Table")) {
							tableName = cell.toString().substring(6);
							z = cell.getRowIndex() + 1;
							break;

						} else if (i == z) {
							break;
						}
						rowValue.add(getCellValue(cell));
					}

				}
				if (!rowValue.isEmpty()) {

					String sql = "INSERT INTO " + tableName + " Value (" + "'"
							+ StringUtils.join(rowValue.toArray(), "','")
							+ "');";
					System.out.print(sql);
					//DBHelper(sql);
					try {
						Connection connection = druidDataSource.getConnection();
						stmt = connection.prepareStatement(sql);
						stmt.execute();
						stmt.close();
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}
