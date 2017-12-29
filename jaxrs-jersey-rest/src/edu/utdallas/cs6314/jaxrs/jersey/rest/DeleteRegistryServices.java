package edu.utdallas.cs6314.jaxrs.jersey.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.sql.*;
import org.json.JSONObject;
 
@Path("/deleteregistryservices")
public class DeleteRegistryServices{
	@Path("/deleteregistry")
	@POST
	@Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
	public Response searchItem(MultivaluedMap<String, String> formParam) {
		String registryId = formParam.getFirst("registryId");
		JSONObject json = new JSONObject();
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/registry","root","root");   
			Statement stmt=con.createStatement();
			String query = "delete from user_registry where registery_id='"+registryId+"'";
			stmt.executeUpdate(query);
			query = "delete from shared_registry where registery_id='"+registryId+"'";
			stmt.executeUpdate(query);
			query = "delete from registery_item where registery_id='"+registryId+"'";
			stmt.executeUpdate(query);
			json.put("message", "Registry deleted successfully.");
			json.put("success", true);	
			con.close();  
			}catch(Exception e){e.printStackTrace();}  
		
		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
	}	
}
