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
 
@Path("/additemservices")
public class AddItemServices{
	@Path("/additem")
	@POST
	@Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
	public Response searchItem(MultivaluedMap<String, String> formParam) {
		String item_name = formParam.getFirst("itemName");
		String description = formParam.getFirst("description");
		String category = formParam.getFirst("category");
		String price = formParam.getFirst("price");
		String photo = formParam.getFirst("photo");
		String quantity = formParam.getFirst("quantity");
		JSONObject json = new JSONObject();
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/registry","root","root");   
			Statement stmt=con.createStatement();
			String query = "insert into item (item_name,description,category,price,photo,quantity) values ('"+item_name+"','"+description+"','"+category+"','"+price+"','"+photo+"','"+quantity+"')";
			System.out.println(query);
			stmt.executeUpdate(query);
			json.put("name", item_name);
			json.put("description", description);
			json.put("category", category);
			json.put("price", price);
			json.put("photo", photo);
			json.put("quantity", quantity);	
			con.close();  
			}catch(Exception e){e.printStackTrace();}  
		
		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
	}	
}
