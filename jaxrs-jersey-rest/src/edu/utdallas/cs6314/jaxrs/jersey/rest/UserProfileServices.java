package edu.utdallas.cs6314.jaxrs.jersey.rest;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
 
@Path("/userprofileservice")
public class UserProfileServices{
	@Path("/showprofile")
	@POST
	@Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
	public Response searchItem(MultivaluedMap<String, String> formParam) {
		String userId = formParam.getFirst("userId");
		
		JSONObject json = new JSONObject();
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/registry","root","root");    
			Statement stmt=con.createStatement();
			String query = "select * from user where user_id = '"+userId+"'";
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next())
			{
				json.put("userId", rs.getString("user_id"));
				json.put("name", rs.getString("name"));
				json.put("email", rs.getString("email"));
				json.put("password", rs.getString("password"));
				json.put("address", rs.getString("address"));
				json.put("phone", rs.getString("phone"));
				json.put("type", rs.getString("type"));
				json.put("message", "Token is valid");
				json.put("success", true);
			}	
			con.close();  
			}catch(Exception e){e.printStackTrace();}  
		
		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
	}
	
	
}
