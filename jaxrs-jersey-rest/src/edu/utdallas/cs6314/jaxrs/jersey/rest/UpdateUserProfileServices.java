package edu.utdallas.cs6314.jaxrs.jersey.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

@Path("/updateuserprofileservices")
public class UpdateUserProfileServices {
	@Path("/updateuserprofile")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(MultivaluedMap<String, String> formParam) {
		String name = formParam.getFirst("name");
		String email = formParam.getFirst("email");
		String password = formParam.getFirst("password");
		String address = formParam.getFirst("address");
		String phone = formParam.getFirst("phone");
		String type = formParam.getFirst("type");
		JSONObject json = new JSONObject();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registry", "root", "root");
			Statement stmt = con.createStatement();

			String query1 = "update user set name = '" + name + "', password = '" + password + "',address = '" + address
					+ "',phone = '" + phone + "',type = '" + type + "' where email='" + email + "'";
			stmt.executeUpdate(query1);
			json.put("name", name);
			json.put("email", email);
			json.put("password", password);
			json.put("address", address);
			json.put("phone", phone);
			json.put("type", type);
			json.put("message", "User profile updated successfully.");
			json.put("success", true);

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
	}

}
