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
 
@Path("/signupservices")
public class SignupServices{
	@Path("/adduser")
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
		String answer = formParam.getFirst("answer");
		JSONObject json = new JSONObject();
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/registry","root","root");    
			Statement stmt=con.createStatement();
			String query = "select * from user where email='"+email+"'";
			ResultSet rs=stmt.executeQuery(query);
			int count = 0;
			while(rs.next())
			{
				count++;
			}
			if(count >= 1) 
			{
				json.put("message", "User with this email already exists.");
				json.put("success", false);
			}
			else
			{
				String query1 = "insert into user(name,email,password,address,phone,type,answer) values('"+name+"','"+email+"','"+password+"','"+address+"','"+phone+"','"+type+"','"+answer+"')";
				stmt.executeUpdate(query1);
				json.put("message", "User registered successfully.Please Log In.");
				json.put("success", true);
			}	
			con.close();  
			}catch(Exception e){e.printStackTrace();}  
		
		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
	}
	
	
}
