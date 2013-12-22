package com.classauto.site.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
	
	private static Connection conn;
	
	
	public static Connection getConn() {
		
		open();
		
		return conn;
	}
	
	public static void open() {
		 
		try {
			
			//String drive  = prop.getString(JAVA_DRIVE);
			//String url    = prop.getString(JAVA_URL);
			//String user   = prop.getString(JAVA_USER);
			//String passwd = prop.getString(JAVA_PASSWD);
			
			Class.forName("com.mysql.jdbc.Driver");
			
			//System.out.println("Connecting " + prop.getString(JAVA_USER));
			
			//conn = DriverManager.getConnection(prop.getString(JAVA_URL), prop.getString(JAVA_USER), prop.getString(JAVA_PASSWD));
			//conn = DriverManager.getConnection("jdbc:mysql://54.243.253.253:3306/brandrace", "appwww", "!allappwwwgud@");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "");
	
		} catch (Exception e) {
		                                                    
			e.printStackTrace();
			
		}
	}

}
