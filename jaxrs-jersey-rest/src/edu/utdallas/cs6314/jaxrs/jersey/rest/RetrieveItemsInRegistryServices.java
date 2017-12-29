package edu.utdallas.cs6314.jaxrs.jersey.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.sql.*;

import org.json.JSONArray;
import org.json.JSONObject;

@Path("/retrieveitemsinregistryservices")
public class RetrieveItemsInRegistryServices {
	@Path("/retrieveitemsinregistry")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchItem(MultivaluedMap<String, String> formParam) {
		String registryId = formParam.getFirst("registryId");
		JSONArray jsonMain = new JSONArray();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registry", "root", "root");
			Statement stmt = con.createStatement();
			String query = "select * from registery_item,item where registery_id='" + registryId
					+ "' and registery_item.item_id=item.item_id";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				JSONObject json = new JSONObject();
				json.put("name", rs.getString("item_name"));
				json.put("itemId", rs.getString("item_id"));
				json.put("assignedEmail", rs.getString("assigned_id"));
				json.put("message", "Valid User");
				json.put("success", true);
				jsonMain.put(json);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.ok(jsonMain.toString(), MediaType.APPLICATION_JSON).build();
	}
}
