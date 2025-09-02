package com.litmus7.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.util.Properties;


public class DatabaseConnectionUtil {

	private static String path;
	private static String username;
	private static String password;
	
	static
	{
		
		Properties properties=new Properties();
		
		try(FileInputStream fs=new FileInputStream("C:\\Users\\ACER\\OneDrive\\Documents\\Litmus 7 assignments\\Java_Threads_Assignment_Phase3\\InventoryManagement\\src\\resources\\dbproperties.txt"))
		{
			
			
			
				
			properties.load(fs);
			
			path=properties.getProperty("url");
			
			username=properties.getProperty("username");
			
			password=properties.getProperty("password");
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		}
	
	public static Connection getConnection() throws SQLException
	{
		return DriverManager.getConnection(path,username,password);
	}

}
