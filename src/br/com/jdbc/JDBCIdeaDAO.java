package br.com.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import br.com.jdbcinterface.IdeaDAO;
import br.com.objects.Idea;

public class JDBCIdeaDAO implements IdeaDAO
{
	private Connection connection;
	
	public JDBCIdeaDAO(Connection connection)
	{
		this.connection = connection;
	}

	public boolean addIdea(Idea idea) throws SQLException
	{
		String command = "INSERT INTO ideas " + "(status, register_date, title, description, user_identification, expected_results, likes, comments, idapprover, iddepartment, idauthor) " + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement p = null;
		try
		{
			p = this.connection.prepareStatement(command);
			
			DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime current = LocalDateTime.now();
			
			p.setInt(1, 1);
			p.setString(2, date.format(current));
			p.setString(3, idea.getTitle());
			p.setString(4, idea.getDescription());
			p.setInt(5, idea.getUserIdentification());
			p.setString(6, idea.getExpectedResults());
			p.setInt(7, 0);
			p.setInt(8, 0);
			p.setInt(9, 0);
			p.setInt(10, idea.getIdDepartment());
			p.setInt(11, idea.getIdAuthor());
			p.execute();
		} catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}finally
		{
			p.close();
		}
		return true;
	}

	public List<Idea> searchIdeaByUser(int idUser, int status) throws SQLException
	{
		String command = "SELECT ididea, status, title, user_identification, likes, comments FROM ideas ";
		command += "WHERE idauthor = " + idUser;
		
		if(status != 0)
		{
			command += " AND status = " + status;
		}
		
		command += " ORDER BY status, ididea DESC";
		
		List<Idea> ideaList = new ArrayList<Idea>();
		
		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = connection.createStatement();
			rs = stmt.executeQuery(command);
			
			while(rs.next())
			{
				Idea idea = new Idea();
				
				idea.setId(rs.getInt("ididea"));
				idea.setStatus(rs.getInt("status"));
				idea.setTitle(rs.getString("title"));
				idea.setUserIdentification(rs.getInt("user_identification"));
				idea.setLikes(rs.getInt("likes"));
				idea.setComments(rs.getInt("comments"));
				
				ideaList.add(idea);
			}
		} catch(Exception e)
		{
			e.printStackTrace();
		} finally 
		{
			stmt.close();
			rs.close();
		}
		return ideaList;
	}
	
	public Idea searchIdeaById(int idIdea, int statusIdea) throws SQLException
	{
		String command = "SELECT i.*, u.fullname, ";
		
		if(statusIdea != 1)
		{
			command += "a.fullname, ";
		}
		command += "d.description, c.trade_name FROM ideas i ";
		command += "INNER JOIN users u ON ";
		command += "(i.idauthor = u.iduser) "; 
		
		if(statusIdea != 1)
		{
			command += "INNER JOIN users a ON ";
			command += "(i.idapprover = u.iduser)  ";
		}
		
		command += "INNER JOIN departments d ON "; 
		command += "(i.iddepartment = d.iddepartment) "; 
		command += "INNER JOIN company_units c ON "; 
		command += "(d.idcompany_unit = c.idcompany_unit) "; 
		command += "WHERE i.ididea = " + idIdea;
		
		Idea idea = null;
		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = connection.createStatement();
			rs = stmt.executeQuery(command);
			
			while(rs.next())
			{
				idea = new Idea();
				
				idea.setId(rs.getInt("i.ididea"));
				idea.setStatus(rs.getInt("i.status"));
				idea.setRegisterDate(formatDate(rs.getString("i.register_date")));
				idea.setTitle(rs.getString("i.title"));
				idea.setDescription(rs.getString("i.description"));
				idea.setExpectedResults(rs.getString("i.expected_results"));
				idea.setUserIdentification(rs.getInt("i.user_identification"));
				idea.setNameAuthor(rs.getString("u.fullname"));
				idea.setNameDepartment(rs.getString("d.description"));
				idea.setNameCompanyUnit(rs.getString("c.trade_name"));
				
				if(idea.getStatus() != 1)
				{
					idea.setNameApprover(rs.getString("a.fullname"));
					idea.setObservationApproval(rs.getString("i.observation_approval"));
				}
			}
		} catch(Exception e)
		{
			e.printStackTrace();
		} finally 
		{
			stmt.close();
			rs.close();
		}
		return idea;
	}
	
	public List<Idea> searchIdeaByStatus(int status) throws SQLException
	{
		String command = "SELECT i.ididea, i.status, i.title, i.description, i.user_identification, i.likes, i.comments, u.fullname FROM ideas i ";
		command += "INNER JOIN users u ON ";
		command += "(i.idauthor = u.iduser) ";
		
		if(status != 0)
		{
			command += "WHERE i.status = " + status;
		}
		
		command += " ORDER BY i.status, i.ididea DESC";
		
		List<Idea> ideaList = new ArrayList<Idea>();
		
		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = connection.createStatement();
			rs = stmt.executeQuery(command);
			
			while(rs.next())
			{
				Idea idea = new Idea();
				
				idea.setId(rs.getInt("i.ididea"));
				idea.setStatus(rs.getInt("i.status"));
				idea.setTitle(rs.getString("i.title"));
				idea.setDescription(rs.getString("i.description"));
				idea.setUserIdentification(rs.getInt("i.user_identification"));
				idea.setLikes(rs.getInt("i.likes"));
				idea.setComments(rs.getInt("i.comments"));
				idea.setNameAuthor(rs.getString("u.fullname"));
				
				ideaList.add(idea);
			}
		} catch(Exception e)
		{
			e.printStackTrace();
		} finally 
		{
			stmt.close();
			rs.close();
		}
		return ideaList;
	}
	
	public boolean setScore(int idUser, int score) throws SQLException
	{
		String getScoreByUser = "SELECT current_score, total_score FROM users WHERE iduser = " + idUser;
		
		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = connection.createStatement();
			rs = stmt.executeQuery(getScoreByUser);
			
			int currentScore = 0;
			int totalScore = 0;
			while(rs.next())
			{
				currentScore = rs.getInt("current_score");
				totalScore = rs.getInt("total_score");
			}
			
			String setScoreByUser = "UPDATE users SET current_score = ?, total_score = ? WHERE iduser = " + idUser;
			
			PreparedStatement p;
			p = this.connection.prepareStatement(setScoreByUser);

			p.setInt(1, currentScore + score);
			p.setInt(2, totalScore + score);
			p.execute();
		} catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		} finally 
		{
			stmt.close();
			rs.close();
		}
		return true;
	}
	
	private String formatDate(String dateDatabase)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = null;
		try
		{
			date = sdf.parse(dateDatabase);
			
		}catch(ParseException e)
		{
			e.printStackTrace();
		}
		
		sdf.applyPattern("dd/MM/yyyy");
		String dateFormat = sdf.format(date);
		
		return dateFormat;
	}
}