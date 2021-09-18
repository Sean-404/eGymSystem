package eGymSystem;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public final class Gym implements Serializable {
	
	private static Scanner s;
	private String gymName;
	private boolean userAuthenticate;
	public User userAccount;
	private List<User> arrayUsers = Collections.synchronizedList(new ArrayList<User>());
	
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
	
	public void addCustomer(String username, String password, Membership membership) {
		arrayUsers.add(new Customer(username, password, membership));
	}
	
	public void addTrainer(String username, String password, EmploymentType employment) {
		arrayUsers.add(new Trainer(username, password, employment));
	}
	
	public void addManager(String username, String password) {
		arrayUsers.add(new Manager(username, password));
	}
	
    public User getUser(String userName) {
        for (User userAccount : arrayUsers) {
            if ((userAccount.getUserName()).equals(userName)) {
                return userAccount;
            }
        }

        return null;
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
