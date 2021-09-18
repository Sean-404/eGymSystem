package eGymSystem;

import java.text.ParseException;
import java.util.Date;

public class Customer extends User {
	
	private Membership membershipType;
	private Date expirationDate;
	
	public Customer(String username, String password,Membership membershipType,Date expirationDate) {
		super(username, password);
		this.membershipType = membershipType;
		this.expirationDate = expirationDate;
	}
	
	public Membership getMembershipType() {
		return membershipType;
	}
	
	public Date getExpirationDate() throws ParseException {
		return expirationDate;
	}


}
