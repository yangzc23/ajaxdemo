package com.testin.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBUtil {
	private static String driver;//数据库驱动名
	private static String url;//数据库连接地址
	private static String user;//数据库账号
	private static String passwd;//数据库密码
	
	static{
		//创建一个工具（用来加载属性配置文件）
		Properties prop = new Properties();
		try {
			//加载数据库连接配置文件
			prop.load(DBUtil.class.getClassLoader().getResourceAsStream("db.properties"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver = prop.getProperty("Driver");//获取驱动名
		url = prop.getProperty("URL");//获取连接地址
		user = prop.getProperty("User");//获取账号
		passwd = prop.getProperty("Passwd");//获取密码
	}
	
	/**获取数据库连接*/
	public static Connection getConnection() 
		throws Exception{
		Connection con = null;
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,passwd);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return con;
	}
	
	/**关闭数据库连接*/
	public static void close(Connection con) throws SQLException{
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
}
