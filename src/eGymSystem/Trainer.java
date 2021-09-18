package eGymSystem;

public class Trainer extends User {
	
	private EmploymentType employmentType;
	
	public Trainer(String username, String password,EmploymentType employmentType) {
		super(username, password);
		this.employmentType = employmentType;
	}
	
	public EmploymentType getEmploymentType() {
		return employmentType;
	}

}
