package br.com.servlet;

import java.io.IOException;
import java.io.Serializable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;

import com.google.gson.Gson;

import br.com.db.connection.ConnectionDB;
import br.com.jdbc.JDBCUserDAO;
import br.com.objects.User;
import br.com.encryption.Base64Encryption;
import br.com.encryption.MD5Encryption;

public class LoginServlet extends HttpServlet implements Serializable
{
	private static final long serialVersionUID=1L;
	
	public LoginServlet()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			process(request, response);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			process(request, response);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		User user = new User();
			
		Base64Encryption base64Encryption = new Base64Encryption();
		MD5Encryption md5Encryption = new MD5Encryption();
		
		user.setEmail(request.getParameter("email"));
		user.setPassword(md5Encryption.encryptPassword(base64Encryption.encodePassword(request.getParameter("password"))));
		
		ConnectionDB conec = new ConnectionDB();
		Connection connection = conec.openConnection();
		
		String msg = "";
		JDBCUserDAO jdbcLogin = new JDBCUserDAO(connection);
		boolean loginValidate = jdbcLogin.loginValidation(user);
		
		conec.closeConnection();
		
		if(loginValidate)
		{
			HttpSession session = request.getSession();
			session.setAttribute("email", request.getParameter("email"));
			//response.sendRedirect("resources/home/home.html");
			
			msg = "SUCCESS";
			
		} else
		{
			//response.sendRedirect("index.html");
			msg = "ERROR";
		}
		
		String json = new Gson().toJson(msg);
		
		try
		{	
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			
		} catch (IOException e)
		{	
			e.printStackTrace();
		}
	}
}