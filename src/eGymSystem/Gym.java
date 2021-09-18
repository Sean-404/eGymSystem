package eGymSystem;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public final class Gym implements Serializable {
	
	private static Scanner s;
	private static final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	private String gymName;
	private boolean userAuthenticate;
	public User userAccount;
	private List<User> arrayUsers = Collections.synchronizedList(new ArrayList<User>());
	private List<GymClasses> arrayClasses = Collections.synchronizedList(new ArrayList<GymClasses>());
	
	public Gym(String gymName) {
		userAuthenticate = false;
		this.gymName = gymName;
	}
	
	public void logOn() throws NumberFormatException, ParseException {
		s = new Scanner(System.in);
		System.out.println("Enter your username:");
		String inputUsername = s.nextLine();
		System.out.println("Enter your password:");
		String inputPassword = s.nextLine();
		
		userAuthenticate = authenticateUser(inputUsername, inputPassword);
		
		if(userAuthenticate) {
			System.out.println("Login credentials authenticated.");
		} else {
			System.out.println("Login credentials incorrect.");
		}
		
	}
	
	public void addCustomer(String username, String password, Membership membership, Date expirationDate) {
		arrayUsers.add(new Customer(username, password, membership,expirationDate));
	}
	
	public void addTrainer(String username, String password, EmploymentType employment) {
		arrayUsers.add(new Trainer(username, password, employment));
	}
	
	public void addManager(String username, String password) {
		arrayUsers.add(new Manager(username, password));
	}
	
	public void addGymClass(String className, Date classDate, Trainer classTrainer) {
		arrayClasses.add(new GymClasses(className, classDate, classTrainer));
	}
	
    public User getUser(String userName) {
        for (User userAccount : arrayUsers) {
            if ((userAccount.getUserName()).equals(userName)) {
                return userAccount;
            }
        }

        return null;
    }
    
    public void viewClasses() {
    	System.out.println(arrayClasses.toString());
    }
    
    public void viewTrainers() {
    	System.out.println("List of Personal Trainers:");
    	for(int i = 0; i < arrayUsers.size(); i++) {  
    		if(arrayUsers.get(i) instanceof Trainer) {
    			System.out.print(arrayUsers.get(i).getUserName() + "\n");
    		}
    	}
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
	
    public boolean authenticateUser(String inputUsername, String inputPassword) {
        userAccount = getUser(inputUsername);

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
