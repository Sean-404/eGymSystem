package eGymSystem;

public class Customer extends User {
	
	private Membership membershipType;
	
	public Customer(String username, String password,Membership membershipType) {
		super(username, password);
		this.membershipType = membershipType;	
	}
	
	public Membership getMembershipType() {
		return membershipType;
	}


}
