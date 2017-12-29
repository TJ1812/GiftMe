package edu.utdallas.cs6314.jsp.servlet.mvc.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;

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

import net.spy.memcached.MemcachedClient;

/**
 * Servlet implementation class SessionControllerServlet
 */
@WebServlet("/ItemSearchServlet")
public class ItemSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static MemcachedClient mcc;

	/**
	 * @throws IOException
	 * @see HttpServlet#HttpServlet()
	 */
	public ItemSearchServlet() throws IOException {
		super();
		mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		try {
			Client client = Client.create();
			WebResource webResource = client
					.resource("http://localhost:8080/jaxrs-jersey-rest/itemsearchservice/searchitem");
			MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
			ClientResponse restResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
					.post(ClientResponse.class, formData);
			String resp = restResponse.getEntity(String.class);
			out.println(resp);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String key = request.getReader().readLine();

		if (mcc.get(key) != null) {
			System.out.println("Getting data from cache...");
			out.print(mcc.get(key));
		} else {

			try {
				Client client = Client.create();
				WebResource webResource = client
						.resource("http://localhost:8080/jaxrs-jersey-rest/itemsearchservice/searchitemfromid");
				MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
				formData.add("key", key);
				ClientResponse restResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
						.post(ClientResponse.class, formData);
				String resp = restResponse.getEntity(String.class);
				System.out.println("Putting data in cache...");
				mcc.set(new JSONObject(resp).get("itemId").toString(), 30, resp);
				out.println(resp);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}