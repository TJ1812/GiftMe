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

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@WebServlet("/DeleteItemServlet")
public class DeleteItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteItemServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String itemId = request.getReader().readLine();
		System.out.println(itemId);
		try {
			Client client = Client.create();
			WebResource webResource = client
					.resource("http://localhost:8080/jaxrs-jersey-rest/deleteitemservices/deleteitem");
			MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
			formData.add("itemId", itemId);
			ClientResponse restResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
					.post(ClientResponse.class, formData);
			out.println(restResponse.getEntity(String.class));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
