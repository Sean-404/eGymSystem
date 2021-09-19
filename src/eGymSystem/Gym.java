package eGymSystem;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import Enums.EmploymentType;
import Enums.Membership;

public final class Gym implements Serializable {
	
	private static Scanner s;
	private static final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	private String gymName;
	private boolean userAuthenticate;
	public User userAccount;
	public GymClasses gymClass;
	private List<User> arrayUsers = Collections.synchronizedList(new ArrayList<User>());
	private List<GymClasses> arrayClasses = Collections.synchronizedList(new ArrayList<GymClasses>());
	
	public Gym(String gymName) {
		userAuthenticate = false;
		this.gymName = gymName;
	}
	
	public void logOn() throws NumberFormatException, ParseException {
		int userID = 0;
		s = new Scanner(System.in);
		System.out.println("Enter your User ID: ");
		String userIDString = s.nextLine();
		try {
			userID = Integer.valueOf(userIDString);
		} catch(NumberFormatException e) {
			System.out.println("User ID must be an integer!");
			return;
		}
		System.out.println("Enter your username:");
		String inputUsername = s.nextLine();
		System.out.println("Enter your password:");
		String inputPassword = s.nextLine();
		
		userAuthenticate = authenticateUser(userID, inputUsername, inputPassword);
		
		if(userAuthenticate) {
			System.out.println("Login credentials authenticated.");
		} else {
			System.out.println("Login credentials incorrect.");
		}
	}
	
	public void addCustomer(int userID, String username, String password, Membership membership, Date expirationDate) {
		arrayUsers.add(new Customer(userID, username, password, membership, expirationDate));
	}
	
	public void addTrainer(int userID, String username, String password, EmploymentType employment) {
		arrayUsers.add(new Trainer(userID, username, password, employment));
	}
	
	public void addManager(int userID, String username, String password) {
		arrayUsers.add(new Manager(userID, username, password));
	}
	
	public void addGymClass(String className, Date classDate, Trainer classTrainer) {
		arrayClasses.add(new GymClasses(className, classDate, classTrainer));
	}
	
	public void addNewGymClass() throws ParseException {
		if(userAccount instanceof Trainer) {
			System.out.println("Specify Gym class name: ");
			String className = s.nextLine();
			System.out.println("Specify Gym class date (dd-mm-yyyy): ");
			String dateString = s.nextLine();
			Date date = format.parse(dateString);
			System.out.println("Specify Gym trainer ID: ");
			String trainerName = s.nextLine();
			int trainerID = Integer.valueOf(trainerName);
			Trainer trainer = (Trainer) getUser(trainerID);
			addGymClass(className, date, trainer);
			System.out.println("Gym class added!");
		} else {
			System.out.println("This section is for trainers only!");
		}
	}
	
    public void addNewCustomer() throws ParseException {
    	Membership membership = null;
    	if (userAccount instanceof Manager) {
    		System.out.println("Specify Customer's User ID: ");
    		String IDString = s.nextLine();
    		int customerID = Integer.valueOf(IDString);
			for(int i = 0; i < arrayUsers.size(); i++) {
				if(arrayUsers.get(i).getUserID() == (customerID)) {
					System.out.println("User ID already exists!");
					return;
				}
			}
    		System.out.println("Specify Customer's Username: ");
			String customerUsername = s.nextLine();
			System.out.println("Specify Customer's Password: ");
			String customerPassword = s.nextLine();
			System.out.println("Specify Customer's Membership type (Monthly, FixedTerm, Student, OffPeak): ");
			String customerMembership = s.nextLine();
			try {
				membership = Membership.valueOf(customerMembership);
			} catch(IllegalArgumentException e) {
				System.out.println("Membership input invalid");
			}
			System.out.println("Specify Customer's Membership length (months): ");
			int membershipLength = s.nextInt();
			LocalDate futureDate = LocalDate.now().plusMonths(membershipLength);
			Date date = java.sql.Date.valueOf(futureDate);
			String dateString = format.format(date);
			date = format.parse(dateString);
			
			addCustomer(customerID, customerUsername, customerPassword, membership, date);
			System.out.println("Customer added!");
		} else {
			System.out.println("This section is for managers only!");
		}
    }
    
    public void addNewTrainer() {
    	EmploymentType employmentType = null;
    	if(userAccount instanceof Manager) {
    		System.out.println("Specify Trainer's User ID: ");
    		String IDString = s.nextLine();
    		int trainerID = Integer.valueOf(IDString);
			for(int i = 0; i < arrayUsers.size(); i++) {
				if(arrayUsers.get(i).getUserID() == (trainerID)) {
					System.out.println("User ID already exists!");
					return;
				}
			}
    		System.out.println("Specify Trainer's Username: ");
			String trainerUsername = s.nextLine();
			System.out.println("Specify Trainer's Password: ");
			String trainerPassword = s.nextLine();
			System.out.println("Specify Trainer's Employment Type (PartTime, FullTime, Temporary): ");
			String trainerEmployment = s.nextLine();
			try {
				employmentType = EmploymentType.valueOf(trainerEmployment);
			} catch(IllegalArgumentException e) {
				System.out.println("Employment Type input invalid");
			}
			addTrainer(trainerID, trainerUsername, trainerPassword, employmentType);
			System.out.println("Trainer added!");
    	} else {
    		System.out.println("This section is for managers only!");
    	}
    }
    
 	public void removeUser() throws ParseException {
 		if(userAccount instanceof Manager) {
 			viewCustomers();
 			viewTrainers();
 			System.out.println("Select Member to Remove by User ID: ");
 			String userIDString = s.nextLine();
 			int userIDInput = Integer.valueOf(userIDString);
 			User selectedUser = getUser(userIDInput);
 			if(selectedUser instanceof Customer) {
 				removeCustomer(userIDInput);
 			}
 			else if(selectedUser instanceof Trainer) {
 				removeTrainer(userIDInput);
 			} else {
 				System.out.println("Customer/Trainer does not exist.");
 			}
 		} else {
 			System.out.println("This section is for managers only!");
 		}
	}
    
    public void removeCustomer(int userID) throws ParseException {
    	arrayUsers.removeIf(e -> e.getUserID() == (userID));
    	System.out.println("Customer removed!");
 	}
    
    public void removeTrainer(int userID) throws ParseException {
    	arrayUsers.removeIf(e -> e.getUserID() == (userID));
    	System.out.println("Trainer removed!");
 	}
    
    public void removeGymClass() throws ParseException {
    	if(userAccount instanceof Trainer) {
        	System.out.println("Specify Gym class name: ");
        	String className = s.nextLine();
        	System.out.println("Specify Gym class date (dd-mm-yyyy): ");
        	String classDateString = s.nextLine();
        	Date classDate = format.parse(classDateString);
        	System.out.println("Specify Gym class trainer ID: ");
        	String trainerName = s.nextLine();
        	int trainerID = Integer.valueOf(trainerName);
        	Trainer trainer = (Trainer) getUser(trainerID);
        	getRemoveGymClass(className, classDate, trainer);
    	} else {
    		System.out.println("This section is for trainers only!");
    	}
    }
    
    public void getRemoveGymClass(String className, Date classDate, Trainer trainer) throws ParseException {
    	for (int i = arrayClasses.size() - 1; i >=0; --i) {
 		    GymClasses gymClass = arrayClasses.get(i);
 		    if (gymClass.className.equals(className) && 
 		    		(gymClass.classDate.equals(classDate) && 
 		    		gymClass.classTrainer.equals(trainer))) {
 		      arrayClasses.remove(i);
 		      System.out.println("Gym Class removed!");
 		      return;
 		    } else {   	
 		    	System.out.println("Gym Class does not exist. Return? (Y/N): ");
 		    	String input = s.nextLine();
 		    	if(input.equals("Y") || input.equals("y")) {
 		    		return;
 		    	} else {
 		    		removeGymClass();
 		    	}
 		    }
 		}
    }
	
    public User getUser(int userID) {
        for (User userAccount : arrayUsers) {
            if ((userAccount.getUserID()) == (userID)) {
                return userAccount;
            }
        }

        return null;
    }
    
    public void viewMembership() throws ParseException {
    	if (userAccount instanceof Customer) {
    		System.out.println("Your Membership");
    		System.out.println("Membership Type: " + ((Customer) userAccount).getMembershipType());
    		Date expirationDate = ((Customer) userAccount).getExpirationDate();
    		String expirationDateString = format.format(expirationDate);
    		System.out.println("Expiration Date: " + expirationDateString);
    	} else {
    		System.out.println("This section is for customers only!");
    	}
    }
    
    public void viewCustomers() throws ParseException {
    	if (userAccount instanceof Manager) {
    		System.out.println("List of Customers:");
    		for(int i = 0; i < arrayUsers.size(); i++) {  
        		if(arrayUsers.get(i) instanceof Customer) {
        			System.out.println("User ID: "+ arrayUsers.get(i).getUserID() + " | " +
        			"Name: " + arrayUsers.get(i).getUserName() + " | " +
        			"Membership Type: " + ((Customer) arrayUsers.get(i)).getMembershipType() + " | " +
        			"Expiration Date: " + format.format(((Customer) arrayUsers.get(i)).getExpirationDate()));
        		}
        	}
    	} else {
    		System.out.println("This section is for managers only!");
    	}
    }
    
    public void viewTrainers() {
    	System.out.println("List of Personal Trainers:");
    	for(int i = 0; i < arrayUsers.size(); i++) {  
    		if(arrayUsers.get(i) instanceof Trainer) {
    			System.out.println("User ID: "+ arrayUsers.get(i).getUserID() + " | " +
            	"Name: " + arrayUsers.get(i).getUserName() + " | " +
            	"Employment Type: " + ((Trainer) arrayUsers.get(i)).getEmploymentType() + "\n");
    		}
    	}
    }
    
    public void viewClasses() throws ParseException {
    	if(arrayClasses.isEmpty()) {
    		System.out.println("No classes found!");
    	} else {
        	System.out.println("Gym Classes: ");
        	for(int i = 0; i < arrayClasses.size(); i++) {
            	System.out.println("Class Name: "+ arrayClasses.get(i).getClassName() + " | " +
            	"Class Date: " + format.format(arrayClasses.get(i).getClassDate()) + " | " +
            	"Class Trainer ID: " + arrayClasses.get(i).getClassTrainer().getUserID() + " | " +
            	"Class Trainer Name: " + arrayClasses.get(i).getClassTrainer().getUserName());
        	}
    	}
    }
	
    public boolean authenticateUser(int userID, String inputUsername, String inputPassword) {
        userAccount = getUser(userID);

        if (userAccount != null) {
            return userAccount.checkPassword(inputPassword);
        }
        else {
            return false;
        }
    }
    
	public boolean checkIfManager() {
		if(userAccount instanceof Manager) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean checkIfCustomer() {
		if(userAccount instanceof Customer) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean checkIfTrainer() {
		if(userAccount instanceof Trainer) {
			return true;
		}
		else {
			return false;
		}
	}
    
	public boolean getAuthenticate() {
		return userAuthenticate;
	}
	
	@Override
	public String toString() {
		return "Gym: " + gymName + "\n";
	}
	
	public String getGymName() {
		return gymName;
	}
	
}
