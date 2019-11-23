package br.com.jdbcinterface;

import java.sql.SQLException;
import java.util.List;
import br.com.objects.CompanyUnit;

public interface CompanyUnitDAO
{
	public List<CompanyUnit> showListCompanyUnits() throws SQLException;
}