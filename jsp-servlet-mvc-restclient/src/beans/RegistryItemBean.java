package beans;

public class RegistryItemBean {
	private String registryId;
	private String itemId;
	private String assignedEmail;
	private String name;
	public String getRegistryId() {
		return registryId;
	}
	public void setRegistryId(String registryId) {
		this.registryId = registryId;
	}
	public String getAssignedEmail() {
		return this.assignedEmail;
	}
	public void setAssignedEmail(String assignedEmail) {
		this.assignedEmail = assignedEmail;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}
}
