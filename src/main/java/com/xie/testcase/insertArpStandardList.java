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
							// System.out.print(cell.toString());
							tableName = cell.toString().substring(6);
							z = cell.getRowIndex() + 1;
							//System.out.print(aaa);
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
					System.out.print(sql);
					DBHelper(sql);
					
				}			
				
			}
			// System.out.print(dataList);
		}
		/*
		 * for (List<Object> str : dataList) System.out.print(str);
		 */
	}

}

// 获取要设置的Arp基准的List后,插入Arp基准表中
/*
 * public boolean insertArpStandardList() { Connection conn = null;
 * PreparedStatement ps = null; ResultSet rs = null;
 * //MySql的JDBC连接的url中要加rewriteBatchedStatements参数
 * ，并保证5.1.13以上版本的驱动，才能实现高性能的批量插入。 //优化插入性能，用JDBC的addBatch方法，但是注意在连接字符串加上面写的参数。
 * List<String> list = new ArrayList<>(); String sql =
 * "insert into testcase values (?,?,?,?,?,?)";
 * 
 * try{ conn = DriverManager.getConnection(
 * "jdbc:mysql://113.204.112.125:51020/mysql_mybites?useUnicode=true&characterEnconding=UTF-8"
 * , "root", "Gh3msrootabc5a"); ps = conn.prepareStatement(sql);
 * 
 * //优化插入第一步 设置手动提交 conn.setAutoCommit(false);
 * 
 * int len = list.size(); for(int i=0; i<len; i++) { ps.setString(1,
 * list.get(i)); ps.setString(2, list.get(i)); ps.setString(3,
 * list.get(i).getDeviceName()); ps.setString(4, list.get(i).getDeviceIp());
 * ps.setString(5, list.get(i).getIpAddress()); ps.setString(6,
 * list.get(i).getMacAddress()); ps.setString(7, list.get(i).getCreateTime());
 * 
 * //if(ps.executeUpdate() != 1) r = false; 优化后，不用传统的插入方法了。
 * 
 * //优化插入第二步 插入代码打包，等一定量后再一起插入。 ps.addBatch(); //if(ps.executeUpdate() !=
 * 1)result = false; //每200次提交一次 if((i!=0 && i%200==0) ||
 * i==len-1){//可以设置不同的大小；如50，100，200，500，1000等等 ps.executeBatch(); //优化插入第三步
 * 提交，批量插入数据库中。 conn.commit(); ps.clearBatch(); //提交后，Batch清空。 } }
 * 
 * } catch (Exception e) {
 * System.out.println("MibTaskPack->getArpInfoList() error:" + e.getMessage());
 * return false; //出错才报false } finally { DBConnection.closeConection(conn, ps,
 * rs); } return true; }
 */

