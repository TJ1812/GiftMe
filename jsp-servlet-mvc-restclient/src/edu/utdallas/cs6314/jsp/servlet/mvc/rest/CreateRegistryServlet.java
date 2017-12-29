package edu.utdallas.cs6314.jsp.servlet.mvc.rest;

import beans.RegistryBean;
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

@WebServlet("/CreateRegistryServlet")
public class CreateRegistryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CreateRegistryServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		RegistryBean registryBean = new RegistryBean();
		Gson gson = new Gson();

		String userId = null;
		Boolean showPublic = true;
		String name = null;
		String s = request.getReader().readLine();
		JSONObject keyJson = new JSONObject(s);
		keyJson.remove("itemId");
		keyJson.remove("sharedUserId");
		keyJson.remove("success");
		keyJson.remove("message");
		keyJson.remove("registryId");
			s = keyJson.toString();
			try {
				StringBuilder sb = new StringBuilder();
				sb.append(s);
				System.out.println(s);
				registryBean = (RegistryBean) gson.fromJson(sb.toString(), RegistryBean.class);
				userId = registryBean.getUserId();
				showPublic = registryBean.getShowPublic();
				name = registryBean.getName();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Client client = Client.create();
				WebResource webResource = client
						.resource("http://localhost:8080/jaxrs-jersey-rest/createregistryservices/createregistry");
				MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
				formData.add("userId", userId);
				formData.add("showPublic", showPublic.toString());
				formData.add("name", name);
				ClientResponse restResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
						.post(ClientResponse.class, formData);
				out.println(restResponse.getEntity(String.class));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
