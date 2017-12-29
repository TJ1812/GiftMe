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

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import beans.LoginBean;

/**
 * Servlet implementation class SessionControllerServlet
 */
@WebServlet("/SessionControllerServlet")
public class SessionControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SessionControllerServlet() {
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
		LoginBean lb = new LoginBean();
		Gson gson = new Gson();
		String email = null;
		String password = null;
		String answer = null;
		String s = request.getReader().readLine();
		System.out.println(s);
		JSONObject keyJson = new JSONObject(s);

		if (!keyJson.has("password")) {
			try {
				answer = keyJson.getString("answer");
				email = keyJson.getString("email");
				Client client = Client.create();
				WebResource webResource = client
						.resource("http://localhost:8080/jaxrs-jersey-rest/forgetpassservices/forgetpass");
				MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
				formData.add("email", email);
				formData.add("answer", answer);
				ClientResponse restResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
						.post(ClientResponse.class, formData);
				String resp = restResponse.getEntity(String.class);
				JSONObject respJson = new JSONObject(resp);
				if (respJson.has("userId")) {
					response.addHeader("Access-Control-Allow-Origin", "*");
					response.addHeader("Access-Control-Allow-Methods", "POST");
					response.addHeader("Access-Control-Allow-Headers", "application/json");
					response.addHeader("Access-Control-Max-Age", "86400");
					out.println(respJson);

				} else {
					JSONObject json = new JSONObject();
					json.put("message", "Failed to log in");
					json.put("success", false);
					json.put("userId", "");
					json.put("name", "");
					out.println(json);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			keyJson.remove("answer");
			s = keyJson.toString();
			try {
				StringBuilder sb = new StringBuilder();
				sb.append(s);
				lb = (LoginBean) gson.fromJson(sb.toString(), LoginBean.class);
				email = lb.getEmail();
				password = lb.getPassword();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Client client = Client.create();
				WebResource webResource = client
						.resource("http://localhost:8080/jaxrs-jersey-rest/loginservices/checkuservalidity");
				MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
				formData.add("username", email);
				formData.add("password", password);
				ClientResponse restResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
						.post(ClientResponse.class, formData);
				String resp = restResponse.getEntity(String.class);
				JSONObject respJson = new JSONObject(resp);
				if (respJson.has("userId")) {
					response.addHeader("Access-Control-Allow-Origin", "*");
					response.addHeader("Access-Control-Allow-Methods", "POST");
					response.addHeader("Access-Control-Allow-Headers", "application/json");
					response.addHeader("Access-Control-Max-Age", "86400");
					out.println(respJson);
				} else {
					JSONObject json = new JSONObject();
					json.put("message", "Failed to log in");
					json.put("success", false);
					json.put("userId", "");
					json.put("name", "");
					out.println(json);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}