package Sys;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import Enums.EmploymentType;
import Enums.Membership;
import eGymSystem.Gym;

public class GymSystem {
	
	//this is the filepath for the serialisation file, it may need to be changed for other machines
	private static final String PATH = "C:\\Users\\seanm\\eclipse-workspace\\eGymSystem\\src\\eGymSystem";
	
	private static final Scanner S = new Scanner(System.in);
	
	private static final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	
	private List<Gym> arrayGym = Collections.synchronizedList(new ArrayList<Gym>());
	
	private Gym gym;
	private int gymInt;
	
	public GymSystem() throws ParseException {
		
		arrayGym.add(new Gym("Liverpool"));
		arrayGym.add(new Gym("Manchester"));
		arrayGym.add(new Gym("Leeds"));
		arrayGym.get(0).addManager(1234, "Sean","_Sean");
		arrayGym.get(1).addManager(1111, "James","_James");
		arrayGym.get(2).addManager(0000, "Ben","_Ben");
		arrayGym.get(0).addCustomer(2222, "Thanos","_Thanos", Membership.Student, format.parse("01-01-2023"));
		arrayGym.get(0).addTrainer(3333, "Bob", "_Bob", EmploymentType.FullTime);
		
		deSerialize();
	}
	
	private void logOn() throws Exception {
		System.out.println(arrayGym.toString());
		System.out.println("Choose a Gym Location (e.g. Liverpool = 1, Manchester = 2 etc.): ");
		gymInt = Integer.valueOf(S.nextLine());
		gymInt -= 1;
		gym = arrayGym.get(gymInt);
		gym.logOn();
		if(gym.getAuthenticate()) {
			mainMenu();
		}
		else {
			System.out.println("Access Denied.");
		}
	}
	
	public void entryMenu() throws Exception {
		String choice = "";

		do {
			System.out.println("-- ENTRY MENU --");
			System.out.println("1 - Login");
			System.out.println("2 - Quit");
			System.out.print("Pick : ");

			choice = S.nextLine().toUpperCase();

			switch (choice) {
				case "1" : {
					logOn();
					break;
				}
			}

		} while (!choice.equals("2"));
		
		serialize();
		System.out.println("Goodbye!");
		S.close();
	}
	
	public void mainMenu() throws Exception {
		String choice = "";
		do {
			System.out.println("\nWelcome to the Gym System!");
			System.out.println("1 - View gym classes");
			System.out.println("2 - View list of personal trainers");
			System.out.println("3 - View membership type and expiration date (Customers only)");
			System.out.println("4 - View list of customers (Managers only)");
			System.out.println("5 - Add a new customer (Managers only)");
			System.out.println("6 - Add a new trainer (Managers only)");
			System.out.println("7 - Remove a customer/trainer (Managers only)");
			System.out.println("8 - Add a new gym class (Trainers only)");
			System.out.println("9 - Remove a gym class (Trainers only)");
			System.out.println("10 - Log out");
			System.out.print("Pick : ");

			choice = S.nextLine();
		
			switch (choice) {
				case "1" : {
					gym.viewClasses();
					break;
				}
				case "2":  {
					gym.viewTrainers();
					break;
				}
				case "3": {
					gym.viewMembership();
					break;
				}
				case "4": {
					gym.viewCustomers();
					break;
				}
				case "5": {
					gym.addNewCustomer();
					break;
				}
				case "6": {
					gym.addNewTrainer();
					break;
				}
				case "7": {
					gym.removeUser();
					break;
				}
				case "8": {
					gym.addNewGymClass();
					break;
				}
				case "9": {
					gym.removeGymClass();
					break;
				}
			}
		} while (!choice.equals("10"));
	}
	
    public Gym getGym(String selectedGym) {
        for (Gym gym : arrayGym) {
            if ((gym.getGymName()).equals(selectedGym)) {
                return gym;
            }
        }
        return null;
    }
    
	private void deSerialize() {
		ObjectInputStream ois;

		try {
			ois = new ObjectInputStream(new FileInputStream(PATH + "depots.ser"));

			arrayGym = (List<Gym>)ois.readObject();

			ois.close();
		}
		catch (Exception e) {
			//System.out.println(e.getMessage());
		}
	}
	
	private void serialize() {
		ObjectOutputStream oos;

		try {
			oos = new ObjectOutputStream(new FileOutputStream(PATH + "depots.ser"));
			oos.writeObject(arrayGym);
			oos.close();
		}
		catch (Exception e) {
			//System.out.println(e.getMessage());
		}
	}

}
