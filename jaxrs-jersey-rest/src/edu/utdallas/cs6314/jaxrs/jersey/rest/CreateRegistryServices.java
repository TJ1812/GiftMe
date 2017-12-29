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

@Path("/createregistryservices")
public class CreateRegistryServices {
	@Path("/createregistry")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchItem(MultivaluedMap<String, String> formParam) {
		String userId = formParam.getFirst("userId");
		String showPublic = formParam.getFirst("showPublic");
		String name = formParam.getFirst("name");
		System.out.println(userId + " "+ showPublic+" "+name);
		JSONObject json = new JSONObject();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registry", "root", "root");
			Statement stmt = con.createStatement();
			String query = "insert into user_registry(user_id,public,name) values ('" + userId + "','"+ showPublic + "','"+ name + "')";
			stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.getGeneratedKeys();
			Integer registryId = 0;
			while(rs.next())
			{
				registryId = rs.getInt(1);
			}
			System.out.println(registryId);
			json.put("userId", "");
			json.put("registryId", registryId.toString());
			json.put("name", name);
			json.put("showPublic", new Boolean(showPublic));
			json.put("itemId", "");
			json.put("sharedUserId", "");
			json.put("message", "Created Registry successfully.");
			json.put("success", true);
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
	}
}
