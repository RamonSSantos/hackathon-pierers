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
import br.com.jdbc.JDBCIdeaDAO;
import br.com.objects.Idea;

@Path("homeRest")
public class HomeRest extends UtilRest
{
	public HomeRest()
	{
		
	}
	
	@GET
	@Path("/searchIdeaByStatus/status/{searchStatus}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchIdeaByUser(@PathParam("searchStatus") int status)
	{
		try
		{
			List<Idea> ideas = new ArrayList<Idea>();
			
			ConnectionDB conec = new ConnectionDB();
			Connection connection = conec.openConnection();
			
			JDBCIdeaDAO jdbcIdea = new JDBCIdeaDAO(connection);
			ideas = jdbcIdea.searchIdeaByStatus(status);
			
			conec.closeConnection();
			
			return this.buildResponse(ideas);
		} catch(Exception e)
		{
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
}