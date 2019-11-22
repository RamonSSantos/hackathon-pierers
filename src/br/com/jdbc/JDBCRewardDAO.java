package br.com.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.jdbcinterface.RewardDAO;
import br.com.objects.Reward;

public class JDBCRewardDAO implements RewardDAO
{
private Connection connection;
	
	public JDBCRewardDAO(Connection connection)
	{
		this.connection = connection;
	}
	
	public List<Reward> searchRewards()
	{
		String command = "SELECT * FROM rewards ";
		command += "ORDER BY score";
		
		List<Reward> rewardList = new ArrayList<Reward>();
		Reward reward = null;
		
		try
		{
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(command);
			
			while(rs.next())
			{
				reward = new Reward();
				
				reward.setId(rs.getInt("idreward"));
				reward.setScore(rs.getInt("score"));
				reward.setDescription(rs.getString("description"));
				
				rewardList.add(reward);
			}
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		return rewardList;
	}
}