package br.com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;

import br.com.db.connection.ConnectionDB;
import br.com.jdbc.JDBCUserDAO;
import br.com.objects.User;

public class LoggedUsername extends HttpServlet
{
	private static final long serialVersionUID=1L;
	
	public LoggedUsername()
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
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{	
		HttpSession session = ((HttpServletRequest)request).getSession();
		
		String logged_username = null;
		logged_username = (String)session.getAttribute("email");
		
		ConnectionDB conec = new ConnectionDB();
		Connection connection = conec.openConnection();
		
		JDBCUserDAO jdbcLogin = new JDBCUserDAO(connection);
		User user = jdbcLogin.loggedUsername(logged_username);
		
		conec.closeConnection();
		
		String json = new Gson().toJson(user);
		
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