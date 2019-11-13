package com.xie.testcase;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class insertArpStandardList {
	public static final String url = "jdbc:mysql://113.204.112.125:51020/mysql_mybites";  
	public static final String name = "com.mysql.cj.jdbc.Driver";  
	public static final String user = "root";  
	public static final String password = "Gh3msrootabc5a"; 
	public static Connection conn = null;  
	public static PreparedStatement pst = null;
	public static void DBHelper(String sql) {  
		try {  
			Class.forName(name);//指定连接类型  
			conn = DriverManager.getConnection(url, user, password);//获取连接  
			pst = conn.prepareStatement(sql);//准备执行语句  
			pst.execute();
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
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
		List<List<Object>> dataList = new ArrayList<>();
		String tableName = "";
		Workbook wb = new XSSFWorkbook(in);

		/**
		 * 设置当前excel中sheet的下标：0开始
		 */
		Sheet sheet = wb.getSheetAt(0);

		if (sheet != null) {
			/**
			 * 获取总行数，为循环列做准备
			 */
			int rowNos = sheet.getLastRowNum();
			for (int i = 0; i <= rowNos; i++) {
				List<Object> rowValue = new LinkedList<>();
				//List<String> table = new LinkedList<>();多表插入时使用
				Row row = sheet.getRow(i);
				String s = "";
				/**
				 * 获取总列数，为循环列做准备
				 */
				if (row != null) {
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
				if (!rowValue.isEmpty() ) {
					String sql ="INSERT INTO "+tableName+ " Value (" + "'"+StringUtils.join(rowValue.toArray(), "','")+"');";
					System.out.print(sql);
					DBHelper(sql);
					
				}			
				
			}
		}

	}

}


