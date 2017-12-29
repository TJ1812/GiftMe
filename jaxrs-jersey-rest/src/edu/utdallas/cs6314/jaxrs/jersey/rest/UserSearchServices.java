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

@Path("/usersearchservice")
public class UserSearchServices {
	@Path("/searchuser")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchItem(MultivaluedMap<String, String> formParam) {
		String key = formParam.getFirst("key");
		JSONArray jsonMain = new JSONArray();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registry", "root", "root");
			Statement stmt = con.createStatement();
			String query = "select * from user where (name LIKE '%" + key + "%' OR email LIKE '%" + key
					+ "%' OR phone LIKE '%" + key + "%')";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				JSONObject json = new JSONObject();
				json.put("userId", rs.getString("user_id"));
				json.put("name", rs.getString("name"));
				json.put("email", rs.getString("email"));
				json.put("address", rs.getString("address"));
				json.put("phone", rs.getString("phone"));
				json.put("type", rs.getString("type"));
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
