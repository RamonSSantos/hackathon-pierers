package br.com.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response; 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.db.connection.ConnectionDB;
import br.com.jdbc.JDBCRewardDAO;
import br.com.objects.Reward;

@Path("rewardRest")
public class RewardRest extends UtilRest
{
	public RewardRest()
	{
		
	}
	
	@GET
	@Path("/searchRewards")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchRewards()
	{
		try
		{
			List<Reward> rewards = new ArrayList<Reward>();
			
			ConnectionDB conec = new ConnectionDB();
			Connection connection = conec.openConnection();
			
			JDBCRewardDAO jdbcReward = new JDBCRewardDAO(connection);
			rewards = jdbcReward.searchRewards();
			
			conec.closeConnection();
			
			return this.buildResponse(rewards);
		} catch(Exception e)
		{
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
}