package br.com.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.jdbcinterface.CompanyUnitDAO;
import br.com.objects.CompanyUnit;

public class JDBCCompanyUnitDAO implements CompanyUnitDAO
{
	private Connection connection;
	
	public JDBCCompanyUnitDAO(Connection connection)
	{
		this.connection = connection;
	}
	
	public List<CompanyUnit> showListCompanyUnits() throws SQLException
	{
		String command = "SELECT idcompany_unit, trade_name FROM company_units ORDER BY trade_name";
		
		ArrayList<CompanyUnit> listCompanyUnits = new ArrayList<CompanyUnit>();
		
		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = connection.createStatement();
			rs = stmt.executeQuery(command);
			
			while(rs.next())
			{
				CompanyUnit companyUnits = new CompanyUnit();
				
				companyUnits.setId(rs.getInt("idcompany_unit"));
				companyUnits.setTradeName(rs.getString("trade_name"));
				
				listCompanyUnits.add(companyUnits);
			}
		} catch(SQLException e)
		{
			e.printStackTrace();
		} finally 
		{
			stmt.close();
			rs.close();
		}
		return listCompanyUnits;
	}
}