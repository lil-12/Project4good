//Author: Carter Morgan
public class CustomerAccount extends Account {
	private String profile;
	
	//client account constructor
	public CustomerAccount(int id, String username,String password,String profile) {
		super(id, username, password);
		this.profile = profile;
	}
	
	//profile getter
	public String getProfile() {
		return profile;
	}
}
