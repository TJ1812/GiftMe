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

@Path("/retrieveregistriessharedservices")
public class RetrieveRegistriesSharedServices {
	@Path("/retrieveregistriesshared")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchItem(MultivaluedMap<String, String> formParam) {
		String userId = formParam.getFirst("userId");
		JSONArray jsonMain = new JSONArray();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registry", "root", "root");
			Statement stmt = con.createStatement();
			String query = "select * from shared_registry,user_registry where shared_registry.registery_id = user_registry.registery_id and (shared_registry.user_id='" + userId+"' or user_registry.public = 'true')";
			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				System.out.println("Hi");
				JSONObject json = new JSONObject();
				//json.put("description", rs.getString("description"));
				json.put("registryId", rs.getString("registery_id"));
				//json.put("category", rs.getString("category"));
				//json.put("price", rs.getString("price"));
				//json.put("photo", rs.getString("photo"));
				//json.put("quantity", rs.getString("quantity"));
				json.put("userId", rs.getString("user_id"));
				json.put("public", rs.getString("public"));
				json.put("name", rs.getString("name"));
				json.put("message", "Valid User");
				json.put("success", true);
				jsonMain.put(json);
			}
			query = "select * from user_registry where public = 'true'";
			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				JSONObject json = new JSONObject();
				//json.put("description", rs.getString("description"));
				json.put("registryId", rs.getString("registery_id"));
				//json.put("category", rs.getString("category"));
				//json.put("price", rs.getString("price"));
				//json.put("photo", rs.getString("photo"));
				//json.put("quantity", rs.getString("quantity"));
				json.put("userId", rs.getString("user_id"));
				json.put("public", rs.getString("public"));
				json.put("name", rs.getString("name"));
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
