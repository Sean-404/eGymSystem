package eGymSystem;

import java.util.Date;

public class GymClasses {
	
	protected String className;
	protected Date classDate;
	protected Trainer classTrainer;
	
	public GymClasses(String className, Date classDate, Trainer classTrainer) {
		this.className = className;
		this.classDate = classDate;
		this.classTrainer = classTrainer;
	}
	
	public String getClassName() {
		return className;
	}
	
	public Date getClassDate() {
		return classDate;
	}
	
	public Trainer getClassTrainer() {
		return classTrainer;
	}

}
