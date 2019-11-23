package br.com.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.jdbcinterface.RewardDAO;
import br.com.objects.Reward;
import br.com.objects.User;

public class JDBCRewardDAO implements RewardDAO
{
private Connection connection;
	
	public JDBCRewardDAO(Connection connection)
	{
		this.connection = connection;
	}
	
	public List<Reward> searchRewards() throws SQLException
	{
		String command = "SELECT * FROM rewards ";
		command += "ORDER BY score";
		
		List<Reward> rewardList = new ArrayList<Reward>();
		
		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = connection.createStatement();
			rs = stmt.executeQuery(command);
			
			while(rs.next())
			{
				Reward reward = new Reward();
				
				reward.setId(rs.getInt("idreward"));
				reward.setScore(rs.getInt("score"));
				reward.setDescription(rs.getString("description"));
				
				rewardList.add(reward);
			}
		} catch(Exception e)
		{
			e.printStackTrace();
		} finally
		{
			stmt.close();
			rs.close();
		}
		return rewardList;
	}
	
	public List<Reward> getQuantityReceived(int idUser, List<Reward> rewardList) throws SQLException
	{
		Statement stmt = null;
		ResultSet rs = null;
		
		String command = "";
		for(Reward reward : rewardList)
		{
			command = "SELECT COUNT(idreward) FROM rewards_has_users ";
			command += "WHERE iduser = " + idUser;
			command += " AND idreward = " + reward.getId();
			
			try
			{
				stmt = connection.createStatement();
				rs = stmt.executeQuery(command);
				
				while(rs.next())
				{
					reward.setQuantityReceived(rs.getInt("COUNT(idreward)"));
				}		
			} catch(Exception e)
			{
				e.printStackTrace();
			} finally 
			{
				stmt.close();
				rs.close();
			}
		}
		return rewardList;
	}

	public User getCurrentScoreUser(int idUser) throws SQLException
	{
		String command = "SELECT current_score FROM users WHERE iduser = " + idUser;
		
		User user = null;
		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = connection.createStatement();
			rs = stmt.executeQuery(command);
			
			while(rs.next())
			{
				user = new User();
				
				user.setId(idUser);
				user.setCurrentScore(rs.getInt("current_score"));
			}
		} catch(Exception e)
		{
			e.printStackTrace();
		} finally 
		{
			stmt.close();
			rs.close();
		}
		return user;
	}

	public boolean getReward(User user, int idReward) throws SQLException
	{
		String getScoreReward = "SELECT score FROM rewards WHERE idreward = " + idReward;
		
		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			int scoreReward = 0;
			stmt = connection.createStatement();
			rs = stmt.executeQuery(getScoreReward);
			
			while(rs.next())
			{
				scoreReward = rs.getInt("score");
			}
			
			String getReward = "";
			if(user.getCurrentScore() >= scoreReward)
			{
				getReward = "UPDATE users SET current_score = ? WHERE iduser = " + user.getId();
			} else
			{
				//return false;
			}
			
			PreparedStatement p;
			p = this.connection.prepareStatement(getReward);
	
			p.setInt(1, user.getCurrentScore() - scoreReward);
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

	public boolean insertRewardsByUser(int idUser, int idReward) throws SQLException
	{
		String command = "INSERT INTO rewards_has_users (idreward, iduser) VALUES(?, ?)";
		
		PreparedStatement p = null;
		try
		{
			p = this.connection.prepareStatement(command);
			
			p.setInt(1, idReward);
			p.setInt(2, idUser);
			p.execute();
		} catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		} finally 
		{
			p.close();
		}
		return true;
	}
}