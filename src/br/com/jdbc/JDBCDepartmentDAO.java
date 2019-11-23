package br.com.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.jdbcinterface.DepartmentDAO;
import br.com.objects.Department;

public class JDBCDepartmentDAO implements DepartmentDAO
{
	private Connection connection;
	
	public JDBCDepartmentDAO(Connection connection)
	{
		this.connection = connection;
	}
	
	public List<Department> showListDepartments(int idCompanyUnit) throws SQLException
	{
		String command = "SELECT iddepartment, description FROM departments WHERE idcompany_unit = " + idCompanyUnit + " ORDER BY description";
		
		ArrayList<Department> listDepartments = new ArrayList<Department>();
		
		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = connection.createStatement();
			rs = stmt.executeQuery(command);
			
			while(rs.next())
			{
				Department departments = new Department();
				
				departments.setId(rs.getInt("iddepartment"));
				departments.setDescription(rs.getString("description"));
				
				listDepartments.add(departments);
			}
		} catch(SQLException e)
		{
			e.printStackTrace();
		} finally 
		{
			stmt.close();
			rs.close();
		}
		return listDepartments;
	}
}