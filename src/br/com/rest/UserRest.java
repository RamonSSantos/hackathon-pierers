package br.com.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
//import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response; 
import javax.ws.rs.GET;
//import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
//import org.codehaus.jackson.map.ObjectMapper;

import br.com.db.connection.ConnectionDB;
//import br.com.encryption.Base64Encryption;
//import br.com.encryption.MD5Encryption;
import br.com.jdbc.JDBCUserDAO;
import br.com.objects.User;

@Path("userRest")
public class UserRest extends UtilRest
{
	public UserRest()
	{
		
	}
	
	@GET
	@Path("/searchUserByName/{searchUser}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchUserByName(@PathParam("searchUser") String searchUser)
	{
		try
		{
			List<User> users = new ArrayList<User>();
			
			ConnectionDB conec = new ConnectionDB();
			Connection connection = conec.openConnection();
			
			JDBCUserDAO jdbcUser = new JDBCUserDAO(connection);
			users = jdbcUser.searchUserByName(searchUser);
			
			conec.closeConnection();
			
			return this.buildResponse(users);
		} catch(Exception e)
		{
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/searchUserById/{idUser}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchUserById(@PathParam("idUser") int idUser)
	{
		try
		{
			User user = new User();
			
			ConnectionDB conec = new ConnectionDB();
			Connection connection = conec.openConnection();
			
			JDBCUserDAO jdbcUser = new JDBCUserDAO(connection);
			user = jdbcUser.searchUserById(idUser);
			
			conec.closeConnection();
			
			return this.buildResponse(user);
		} catch(Exception e)
		{
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
}