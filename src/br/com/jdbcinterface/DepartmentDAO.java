package br.com.jdbcinterface;

import java.sql.SQLException;
import java.util.List;

import br.com.objects.Department;

public interface DepartmentDAO
{
	public List<Department> showListDepartments(int idCompanyUnit) throws SQLException;
}