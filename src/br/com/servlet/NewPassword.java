package br.com.servlet;

import java.io.IOException;
import java.io.Serializable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import java.sql.Connection;
//import com.google.gson.Gson;
//
//import br.com.db.connection.ConnectionDB;
//import br.com.jdbc.JDBCUserDAO;
//import br.com.objects.User;
//import br.com.encryption.Base64Encryption;
//import br.com.encryption.MD5Encryption;

public class NewPassword extends HttpServlet implements Serializable
{
	private static final long serialVersionUID=1L;
	
	public NewPassword()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		process(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		process(request, response);
	}
	
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}
}