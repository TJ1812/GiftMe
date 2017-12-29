package edu.utdallas.cs6314.jsp.servlet.mvc.rest;
import beans.ItemBean;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@WebServlet("/AddItemServlet")
public class AddItemServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
    public AddItemServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out=response.getWriter();
		ItemBean itemBean = new ItemBean();
		Gson gson = new Gson();
		String item_name = null;
		String description = null;
		String category = null;
		String price = null;
		String photo = null;
		String quantity = null;
		try {
	           StringBuilder sb = new StringBuilder();
	           String s;
	           while ((s = request.getReader().readLine()) != null) {
	               sb.append(s);
	           }
	          itemBean = (ItemBean) gson.fromJson(sb.toString(), ItemBean.class);
	  		  item_name = itemBean.getName();
	  		  description = itemBean.getDescription();
	  		  category = itemBean.getCategory();
	  		  price = itemBean.getPrice();
	  		  photo = itemBean.getPhoto();
	  		  quantity = itemBean.getQuantity();
		  }
		  catch(Exception e)
		  {e.printStackTrace();}
		  System.out.println(item_name+" sa "+description);
		try {
			Client client = Client.create();
			WebResource webResource = client.resource("http://localhost:8080/jaxrs-jersey-rest/additemservices/additem");
			MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
			formData.add("itemName", item_name);
			formData.add("description", description);
			formData.add("category", category);
			formData.add("price", price);
			formData.add("photo", photo);
			formData.add("quantity", quantity);
			ClientResponse restResponse = webResource
			    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
			    .post(ClientResponse.class, formData);
			out.println(restResponse.getEntity(String.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
