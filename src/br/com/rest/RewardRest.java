package br.com.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response; 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.db.connection.ConnectionDB;
import br.com.jdbc.JDBCRewardDAO;
import br.com.objects.Reward;
import br.com.objects.User;

@Path("rewardRest")
public class RewardRest extends UtilRest
{
	public RewardRest()
	{
		
	}
	
	@GET
	@Path("/searchRewards/{iduser}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchRewards(@PathParam("iduser") int idUser)
	{
		try
		{
			List<Reward> rewards = new ArrayList<Reward>();
			
			ConnectionDB conec = new ConnectionDB();
			Connection connection = conec.openConnection();
			
			JDBCRewardDAO jdbcReward = new JDBCRewardDAO(connection);
			rewards = jdbcReward.searchRewards();
			rewards = jdbcReward.getQuantityReceived(idUser, rewards);
			
			conec.closeConnection();
			
			return this.buildResponse(rewards);
		} catch(Exception e)
		{
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/getReward/idUser/{idUser}/idReward/{idReward}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReward(@PathParam("idUser") int idUser, @PathParam("idReward") int idReward)
	{
		try
		{
			User user = new User();
			
			ConnectionDB conec = new ConnectionDB();
			Connection connection = conec.openConnection();
			
			JDBCRewardDAO jdbcReward = new JDBCRewardDAO(connection);
			
			user = jdbcReward.getCurrentScoreUser(idUser);
			boolean validationGetReward = jdbcReward.getReward(user, idReward);
			
			boolean validationUpdateRewards = false;
			if(validationGetReward)
			{
				validationUpdateRewards = jdbcReward.insertRewardsByUser(user.getId(), idReward);
			}
			
			conec.closeConnection();
			
			if(validationUpdateRewards)
			{
				return this.buildResponse("Prêmio resgatado com sucesso!");
			} else
			{
				return this.buildResponse("Não foi possível resgatar o prêmio. Tente novamente!");
			}
		} catch(Exception e)
		{
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
}