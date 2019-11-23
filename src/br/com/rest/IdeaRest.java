package br.com.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response; 
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jackson.map.ObjectMapper;

import br.com.db.connection.ConnectionDB;
import br.com.jdbc.JDBCIdeaDAO;
import br.com.objects.Idea;

@Path("ideaRest")
public class IdeaRest extends UtilRest
{
	public IdeaRest()
	{
		
	}
	
	@POST
	@Path("/addIdea")
	@Consumes("application/*")
	public Response addIdea(String ideaInfo)
	{
		try
		{
			Idea idea = new ObjectMapper().readValue(ideaInfo, Idea.class);
			
			ConnectionDB conec = new ConnectionDB();
			Connection connection = conec.openConnection();
			
			JDBCIdeaDAO jdbcIdea = new JDBCIdeaDAO(connection);
			boolean validation = jdbcIdea.addIdea(idea);
			jdbcIdea.setScore(idea.getIdAuthor(), 10);
			
			conec.closeConnection();
			
			if(validation)
			{
				return this.buildResponse("Ideia cadastrada com sucesso!");
			} else
			{
				return this.buildResponse("Dados inválidos. Tente novamente!");
			}
		} catch(Exception e)
		{
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/searchIdeaByUser/user/{idUser}/status/{searchStatus}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchIdeaByUser(@PathParam("idUser") int idUser, @PathParam("searchStatus") int status)
	{
		try
		{
			List<Idea> ideas = new ArrayList<Idea>();
			
			ConnectionDB conec = new ConnectionDB();
			Connection connection = conec.openConnection();
			
			JDBCIdeaDAO jdbcIdea = new JDBCIdeaDAO(connection);
			ideas = jdbcIdea.searchIdeaByUser(idUser, status);
			
			conec.closeConnection();
			
			return this.buildResponse(ideas);
		} catch(Exception e)
		{
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/searchIdeaById/id/{idIdea}/status/{idStatus}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchIdeaById(@PathParam("idIdea") int idIdea, @PathParam("idStatus") int idStatus)
	{
		try
		{
			Idea idea = new Idea();
			
			ConnectionDB conec = new ConnectionDB();
			Connection connection = conec.openConnection();
			
			JDBCIdeaDAO jdbcIdea = new JDBCIdeaDAO(connection);
			idea = jdbcIdea.searchIdeaById(idIdea, idStatus);
			
			conec.closeConnection();
			
			return this.buildResponse(idea);
		} catch(Exception e)
		{
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
}