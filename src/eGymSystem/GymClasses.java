package eGymSystem;

import java.util.Date;

public class GymClasses {
	
	private String className;
	private Date classDate;
	private Trainer classTrainer;
	
	public GymClasses(String className, Date classDate, Trainer classTrainer) {
		this.className = className;
		this.classDate = classDate;
		this.classTrainer = classTrainer;
	}
	
	public String getClassName() {
		return className;
	}
	
	public Date classDate() {
		return classDate;
	}
	
	public Trainer getClassTrainer() {
		return classTrainer;
	}
	
	@Override
	public String toString() {
		return "Class Name: " + className + "\nClass Date: " + classDate + "\nTrainer: " + classTrainer;
	}

}
