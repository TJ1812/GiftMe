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

@Path("/loginservices")
public class LoginServices {
	@Path("/checkuservalidity")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response isValidUser(MultivaluedMap<String, String> formParam) {
		String email = formParam.getFirst("username");
		String pass = formParam.getFirst("password");
		JSONObject json = new JSONObject();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registry", "root", "root");
			Statement stmt = con.createStatement();
			String query = "select * from user where email='" + email + "' and password='" + pass + "'";
			ResultSet rs = stmt.executeQuery(query);
			int count = 0;
			String userId = null;
			String name = null;
			while (rs.next()) {
				userId = rs.getString("user_id");
				name = rs.getString("name");
				count++;
			}
			if (count >= 1) {
				json.put("message", "Successfully logged in");
				json.put("success", true);
				json.put("userId", userId);
				json.put("name", name);
			} else {
				json.put("message", "Failed to log in");
				json.put("success", false);
				json.put("userId", userId);
				json.put("name", name);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
	}
}