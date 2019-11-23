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
import br.com.jdbc.JDBCDepartmentDAO;
import br.com.objects.Department;

public class ListDepartments extends HttpServlet
{
	private static final long serialVersionUID=1L;
	
	public ListDepartments()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		try
		{
			process(request, response);
		} catch (SQLException e) {
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
		List<Department> listDepartments = new ArrayList<Department>();
		
		String id_CompanyUnit = request.getParameter("idCompanyUnit");
		int idCompanyUnit = Integer.parseInt(id_CompanyUnit);
		
		ConnectionDB conec = new ConnectionDB();
		Connection connection = conec.openConnection();
		
		JDBCDepartmentDAO jdbcCompanyUnit = new JDBCDepartmentDAO(connection);
		listDepartments = jdbcCompanyUnit.showListDepartments(idCompanyUnit);
		
		conec.closeConnection();
		
		String json = new Gson().toJson(listDepartments);
		
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