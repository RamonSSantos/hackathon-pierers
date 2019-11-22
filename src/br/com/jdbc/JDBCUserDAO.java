package br.com.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.jdbcinterface.UserDAO;
import br.com.objects.User;

public class JDBCUserDAO implements UserDAO
{
	private Connection connection;
	
	public JDBCUserDAO(Connection connection)
	{
		this.connection = connection;
	}
	
	public boolean loginValidation(User user)
	{
		String command = "SELECT email, password FROM users WHERE email = '" + user.getEmail() + "'";
		User userInfo = new User();
		
		try
		{
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(command);
			
			while(rs.first())
			{
				userInfo.setEmail(rs.getString("email"));
				userInfo.setPassword(rs.getString("password")); //Senha do banco de dados transformada em MD5
				
				if(user.getEmail().equals(userInfo.getEmail()))
				{
					if(user.getPassword().equals(userInfo.getPassword()))
					{
						return true;
						
					} else
					{
						return false;
					}
				}
			}
		} catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		return false; //IMPORTANTE
	}
	
	public User loggedUsername(String email)
	{
		String command = "SELECT iduser, fullname FROM users WHERE email = '" + email + "'";
		User user = null;
		
		try
		{
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(command);
			
			while(rs.next())
			{
				user = new User();
				
				user.setId(rs.getInt("iduser"));
				user.setFullname(rs.getString("fullname"));
			}
		} catch(SQLException e)
		{
			e.printStackTrace();
		}
		return user;
	}

	public List<User> searchUserByName(String searchUser)
	{
		String command = "SELECT u.iduser, u.fullname, u.current_score, u.total_score, c.trade_name, d.description FROM users u ";
		command += "INNER JOIN departments d ON "; 
		command += "(u.iddepartment = d.iddepartment) ";
		command += "INNER JOIN company_units c ON "; 
		command += "(d.idcompany_unit = c.idcompany_unit) "; 
		
		if(!searchUser.equals("null") && !searchUser.equals(""))
		{
			command += "WHERE u.fullname LIKE '" + searchUser + "%'";
		}
		
		command += " ORDER BY u.current_score, u.total_score";
		
		List<User> userList = new ArrayList<User>();
		User user = null;
		
		try
		{
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(command);
			
			while(rs.next())
			{
				user = new User();
				
				user.setId(rs.getInt("u.iduser"));
				user.setFullname(rs.getString("u.fullname"));
				user.setCurrentScore(rs.getInt("u.current_score"));
				user.setTotalScore(rs.getInt("u.total_score"));
				user.setNameCompanyUnit(rs.getString("c.trade_name"));
				user.setNameDepartment(rs.getString("d.description"));
				
				userList.add(user);
			}
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		return userList;
	}

	public User searchUserById(int idUser)
	{
		String command = "SELECT u.iduser, u.fullname, u.current_score, u.total_score, c.trade_name, d.description FROM users u ";
		command += "INNER JOIN departments d ON "; 
		command += "(u.iddepartment = d.iddepartment) ";
		command += "INNER JOIN company_units c ON "; 
		command += "(d.idcompany_unit = c.idcompany_unit) "; 
		command += "WHERE u.iduser = " + idUser;
		
		User user = null;
		
		try
		{
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(command);
			
			while(rs.next())
			{
				user = new User();
				
				user.setId(rs.getInt("u.iduser"));
				user.setFullname(rs.getString("u.fullname"));
				user.setCurrentScore(rs.getInt("u.current_score"));
				user.setTotalScore(rs.getInt("u.total_score"));
				user.setNameCompanyUnit(rs.getString("c.trade_name"));
				user.setNameDepartment(rs.getString("d.description"));
			}
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		return user;
	}
}