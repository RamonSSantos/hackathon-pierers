package br.com.db.connection;

import java.sql.Connection;

public class ConnectionDB
{
	private Connection connection;
	
	public Connection openConnection()
	{
	
		try
		{
			Class.forName("org.gjt.mm.mysql.Driver");
			
			connection = java.sql.DriverManager
						.getConnection(
						"jdbc:mysql://localhost:3307/piardb",
						"root",
						"");
			
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		return connection;
	}
	
	public void closeConnection()
	{
		
		try
		{
			connection.close();
			
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}