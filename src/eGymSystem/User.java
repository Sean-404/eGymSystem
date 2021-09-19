package eGymSystem;

public class User {
	
	protected int userID;
	protected String username; //These attributes are protected so that only this class and any sub-classes can use them
	protected String password;
	
	public User(int userID, String username, String password) {
		this.userID = userID;
		this.username = username;
		this.password = password;
	}
	
	public boolean checkPassword(String inputPassword) {
		if(password.equals(inputPassword)) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getUserID() {
		return userID;
	}
	
	public String getUserName() {
		return username;
	}

}