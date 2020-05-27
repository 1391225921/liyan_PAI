package com.xie.testcase;

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

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class insertArpStandardList {
	public static final String url = "jdbc:mysql://192.168.30.92:3306/apitest?useUnicode=true&characterEnconding=UTF-8";
	public static final String name = "com.mysql.cj.jdbc.Driver";  
	public static final String user = "liyan";
	public static final String password = "liyan";
	public static Connection conn = null;  
	public static PreparedStatement pst = null;
	public static void DBHelper(String sql) {  
		try {  
			Class.forName(name);//指定连接类型  
			conn = DriverManager.getConnection(url, user, password);//获取连接  
			pst = conn.prepareStatement(sql);//准备执行语句  
			pst.execute();
			pst.close();
			conn.close();
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
		File file = new File("C:\\Users\\sgy\\Desktop\\t_user.xlsx");
		InputStream in = new FileInputStream(file);
		List<List<Object>> dataList = new ArrayList<>();
		String tableName = "";
		Workbook wb = new XSSFWorkbook(in);

		/**
		 * 设置当前excel中sheet的下标：0开始
		 */
		Sheet sheet = wb.getSheetAt(0);

		if (sheet != null) {
			int rowNos = sheet.getLastRowNum();// 得到excel的总记录条数
			for (int i = 0; i <= rowNos; i++) {// 遍历行
				List<Object> rowValue = new LinkedList<Object>();
				List<String> table = new LinkedList<String>();
				Row row = sheet.getRow(i);
				String s = "";
				
				if (row != null) {
					int columNos = row.getLastCellNum();// 表头总共的列数
					for (int j = 0; j < columNos; j++) {
						Cell cell = row.getCell(j);

						// 获取table名称
						if (cell.toString().contains("Table")) {
							System.out.print(cell.toString());
							tableName = cell.toString().substring(6);
							z = cell.getRowIndex() + 1;
							System.out.print("-------");
							break;
							
						} else if (i == z) {
							break;
						}
						rowValue.add(getCellValue(cell));
					}
					
				}
				if (!rowValue.isEmpty() ) {
					//System.out.print(rowValue);
					String sql ="INSERT INTO "+tableName+ " Value (" + "'"+StringUtils.join(rowValue.toArray(), "','")+"');";
					System.out.println(sql);
					DBHelper(sql);
					
				}			
				
			}
			// System.out.print(dataList);
		}

	}

}


