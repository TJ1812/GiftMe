package beans;

public class RegistryBean 
{
	private String registryId;
	private String userId;
	private String name;
	private boolean showPublic;
	public String getRegistryId() {
		return registryId;
	}
	public void setRegistryId(String registryId) {
		this.registryId = registryId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}
	public boolean getShowPublic() {
		return showPublic;
	}
	public void setShowPublic(boolean showPublic) {
		this.showPublic = showPublic;
	}
}
