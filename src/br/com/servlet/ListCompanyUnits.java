package br.com.servlet;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

import br.com.db.connection.ConnectionDB;
import br.com.jdbc.JDBCCompanyUnitDAO;
import br.com.objects.CompanyUnit;

public class ListCompanyUnits extends HttpServlet
{
	private static final long serialVersionUID=1L;
	
	public ListCompanyUnits()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		try {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		List<CompanyUnit> listCompanyUnits = new ArrayList<CompanyUnit>();
		
		ConnectionDB conec = new ConnectionDB();
		Connection connection = conec.openConnection();
		
		JDBCCompanyUnitDAO jdbcCompanyUnit = new JDBCCompanyUnitDAO(connection);
		listCompanyUnits = jdbcCompanyUnit.showListCompanyUnits();
		
		conec.closeConnection();
		
		String json = new Gson().toJson(listCompanyUnits);
		
		try
		{
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		} catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}