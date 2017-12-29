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

@Path("/retrieveuserregistryservices")
public class RetrieveUserRegistryServices {
	@Path("/retrieveuserregistry")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchItem(MultivaluedMap<String, String> formParam) {
		String userId = formParam.getFirst("userId");
		System.out.println(userId);
		JSONArray jsonMain = new JSONArray();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registry", "root", "root");
			Statement stmt = con.createStatement();
			String query = "select * from user_registry where user_id='" + userId + "'";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				JSONObject json = new JSONObject();
				json.put("registryId", rs.getString("registery_id"));
				json.put("userId", rs.getString("user_id"));
				json.put("public", rs.getString("public"));
				json.put("name", rs.getString("name"));
				json.put("message", "Valid user");
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
