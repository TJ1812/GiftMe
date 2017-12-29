package beans;

public class LoginBean {

	private String email;
	private String password;
    /**
     * Default constructor. 
     */
    public LoginBean() {
    	email = "";
    	password = "";
    }
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
