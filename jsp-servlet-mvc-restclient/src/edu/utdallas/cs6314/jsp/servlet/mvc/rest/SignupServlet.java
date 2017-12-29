package edu.utdallas.cs6314.jsp.servlet.mvc.rest;
import beans.SignupBean;

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

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
    public SignupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out=response.getWriter();
		SignupBean signupBean = new SignupBean();
		Gson gson = new Gson();
		String name = null;
		String email = null;
		String password = null;
		String address = null;
		String phone = null;
		String type = null;
		String answer = null;
		try {
	           StringBuilder sb = new StringBuilder();
	           String s;
	           while ((s = request.getReader().readLine()) != null) {
	               sb.append(s);
	           }
	           System.out.println(sb);
	          signupBean = (SignupBean) gson.fromJson(sb.toString(), SignupBean.class);
	  		  name = signupBean.getName();
	          email = signupBean.getEmail();
	  		  password = signupBean.getPassword();
	  		  address = signupBean.getAddress();
	  		  phone = signupBean.getPhone();
	  		  type = signupBean.getType();
	  		  answer = signupBean.getAnswer();
		  }
		  catch(Exception e)
		  {e.printStackTrace();}
		  System.out.println(email+" sa "+password);
		try {
			Client client = Client.create();
			WebResource webResource = client.resource("http://localhost:8080/jaxrs-jersey-rest/signupservices/adduser");
			MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
			formData.add("name", name);
			formData.add("email", email);
			formData.add("password", password);
			formData.add("address", address);
			formData.add("phone", phone);
			formData.add("type", type);
			formData.add("answer", answer);
			ClientResponse restResponse = webResource
			    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
			    .post(ClientResponse.class, formData);
			out.println(restResponse.getEntity(String.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
