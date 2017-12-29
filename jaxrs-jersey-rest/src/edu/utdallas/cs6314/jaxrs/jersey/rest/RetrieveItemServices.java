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

import org.json.JSONArray;
import org.json.JSONObject;
 
@Path("/retrieveitemservices")
public class RetrieveItemServices{
	@Path("/retrieveitem")
	@POST
	@Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
	public Response searchItem(MultivaluedMap<String, String> formParam) {
		String key = formParam.getFirst("key");
		JSONArray jsonMain = new JSONArray();
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/registry","root","root");    
			Statement stmt=con.createStatement();
			String query = "select * from item where(description LIKE '%"+key+"%' OR item_name LIKE '%"+key+"%' OR category LIKE '%"+key+"%')";
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next())
			{
				JSONObject json = new JSONObject();
				json.put("itemId", rs.getString("item_id"));
				json.put("description", rs.getString("description"));
				json.put("name", rs.getString("item_name"));
				json.put("category", rs.getString("category"));
				json.put("price", rs.getString("price"));
				json.put("photo", rs.getString("photo"));
				json.put("quantity", rs.getString("quantity"));
				jsonMain.put(json);
			}	
			con.close();  
			}catch(Exception e){e.printStackTrace();}  
		
		return Response.ok(jsonMain.toString(), MediaType.APPLICATION_JSON).build();
	}
}
