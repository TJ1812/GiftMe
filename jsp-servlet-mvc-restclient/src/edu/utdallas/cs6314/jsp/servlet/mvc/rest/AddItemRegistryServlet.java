package edu.utdallas.cs6314.jsp.servlet.mvc.rest;

import beans.RegistryItemBean;
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

@WebServlet("/AddItemRegistryServlet")
public class AddItemRegistryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddItemRegistryServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		RegistryItemBean registryItemBean = new RegistryItemBean();
		Gson gson = new Gson();
		String registryId = null;
		String itemId = null;
		String s = request.getReader().readLine();
		JSONObject keyJson = new JSONObject(s);
		keyJson.remove("userId");
		keyJson.remove("name");
		keyJson.remove("showPublic");
		keyJson.remove("message");
		keyJson.remove("success");
		keyJson.remove("sharedUserId");
		s = keyJson.toString();
			try {
				StringBuilder sb = new StringBuilder();
				sb.append(s);
				registryItemBean = (RegistryItemBean) gson.fromJson(sb.toString(), RegistryItemBean.class);
				itemId = registryItemBean.getItemId();
				registryId = registryItemBean.getRegistryId();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(itemId + " sa " + registryId);
			try {
				Client client = Client.create();
				WebResource webResource = client
						.resource("http://localhost:8080/jaxrs-jersey-rest/additemregistryservices/additemregistry");
				MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
				formData.add("registryId", registryId);
				formData.add("itemId", itemId);
				ClientResponse restResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
						.post(ClientResponse.class, formData);
				out.println(restResponse.getEntity(String.class));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
}
