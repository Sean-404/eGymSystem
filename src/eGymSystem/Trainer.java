package eGymSystem;

import Enums.EmploymentType;

public class Trainer extends User {
	
	private EmploymentType employmentType;
	
	public Trainer(int userID, String username, String password,EmploymentType employmentType) {
		super(userID, username, password);
		this.employmentType = employmentType;
	}
	
	public EmploymentType getEmploymentType() {
		return employmentType;
	}

}
