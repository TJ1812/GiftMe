package edu.utdallas.cs6314.jsp.servlet.mvc.rest;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * Servlet implementation class SessionControllerServlet
 */
@WebServlet("/UserProfileServlet")
public class UserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserProfileServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String key = null;
		String s = request.getReader().readLine();
		System.out.println(s);
		JSONObject keyJson = new JSONObject(s);
		key = keyJson.getString("userId");
		System.out.println("userId:"+key);
			try {
				Client client = Client.create();
				WebResource webResource = client
						.resource("http://localhost:8080/jaxrs-jersey-rest/userprofileservice/showprofile");
				MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
				formData.add("userId", key);
				ClientResponse restResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
						.post(ClientResponse.class, formData);
				String resp = restResponse.getEntity(String.class);
				out.println(resp);
	
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}