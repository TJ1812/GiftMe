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

@Path("/assignemailtoregistryitemservices")
public class AssignEmailToRegistryItemServices {
	@Path("/assignemailtoregistryitem")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchItem(MultivaluedMap<String, String> formParam) {
		String registryId = formParam.getFirst("registryId");
		String itemId = formParam.getFirst("itemId");
		String assignedEmail = formParam.getFirst("assignedEmail");
		JSONObject json = new JSONObject();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registry", "root", "root");
			Statement stmt = con.createStatement();
			String query = "update registery_item set assigned_id = '"+assignedEmail+"' where registery_id='"+registryId+"'and item_id='"+itemId+"'";
			stmt.executeUpdate(query);
			json.put("name", "");
			json.put("itemId", itemId);
			json.put("assignedEmail", assignedEmail);
			json.put("message", "You are successfully assigned to this item");
			json.put("success", true);
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
	}
}
