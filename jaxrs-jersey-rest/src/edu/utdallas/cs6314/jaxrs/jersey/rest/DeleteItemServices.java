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

@Path("/deleteitemservices")
public class DeleteItemServices {
	@Path("/deleteitem")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchItem(MultivaluedMap<String, String> formParam) {
		String itemId = formParam.getFirst("itemId");
		JSONObject json = new JSONObject();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registry", "root", "root");
			Statement stmt = con.createStatement();
			String query = "delete from item where item_id='" + itemId + "'";
			stmt.executeUpdate(query);
			json.put("itemId", itemId);
			json.put("description", "");
			json.put("name", "");
			json.put("category", "");
			json.put("price", "");
			json.put("photo", "");
			json.put("quantity", "");
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
	}
}
