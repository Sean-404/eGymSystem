package eGymSystem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class GymSystem {
	
	//this is the filepath for the serialisation file, it may need to be changed for other machines
	private static final String PATH = "C:\\Users\\james\\eclipse-workspace\\eGymSystem\\src\\eGymSystem";
	
	private static final Scanner S = new Scanner(System.in);
	
	private List<Gym> arrayGym = Collections.synchronizedList(new ArrayList<Gym>());
	
	private Gym gym;
	private int gymInt;
	
	public GymSystem() throws ParseException {
		
		arrayGym.add(new Gym("Liverpool"));
		arrayGym.add(new Gym("Manchester"));
		arrayGym.add(new Gym("Leeds"));
		arrayGym.get(0).addManager("Sean","_Sean");
		arrayGym.get(1).addManager("James","_James");
		arrayGym.get(2).addManager("Ben","_Ben");
		
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
			System.out.println("Poggers");
			//mainMenu();
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
	
	/*public void mainMenu() throws Exception {
		String choice = "";
		do {
			System.out.println("\nWelcome to the Gym System!");
			System.out.println("1 - View work schedule");
			System.out.println("2 - View list of vehicles");
			System.out.println("3 - Setup work schedule");
			System.out.println("4 - Move vehicle");
			System.out.println("5 - Add vehicle");
			System.out.println("6 - Remove vehicle");
			System.out.println("7 - Add driver");
			System.out.println("8 - Log out");
			System.out.print("Pick : ");

			choice = S.nextLine();
		
			switch (choice) {
				case "1" : {
					depot.viewWorkSchedule();
					break;
				}
				case "2":  {
					depot.viewVehicles();
					break;
				}
				case "3" : {
					depot.setupWorkSchedule();
					break;
				}
				case "4" : {
					if(depot.checkIfManager()) {
						Vehicle selectedVehicle = depot.displayVehicleMenu();
						System.out.println(arrayDepot.toString());
						System.out.println("Select Depot to Move Vehicle (e.g. Liverpool = 1, Manchester = 2 etc.): ");
						int depotChoice = Integer.valueOf(S.nextLine());
						depotChoice-=1;
						Depot selectedDepot = arrayDepot.get(depotChoice);
						
						System.out.println("Specify Move Date & Time for Vehicle [dd-mm-yyyy HH:MM] :");
						String moveDate = S.nextLine();
						SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
						Date moveDate1 = format.parse(moveDate);
						System.out.println("Vehicle will be moved to " + selectedDepot + " at date: " + moveDate1);
				
						if(selectedVehicle instanceof Tanker) {
							String regNo = ((Tanker) selectedVehicle).getRegNo();
							arrayDepot.get(depotInt).getRemoveTanker(regNo);
							TankerCheck.addToBufferTanker(selectedVehicle, selectedDepot, moveDate1);
							depot.startTankerCheck();
						}
						else if(selectedVehicle instanceof Truck) {
							String regNo = ((Truck) selectedVehicle).getRegNo();
							arrayDepot.get(depotInt).getRemoveTruck(regNo);
							TruckCheck.addToBufferTruck(selectedVehicle, selectedDepot, moveDate1);
							depot.startTruckCheck();
						}
					}
					else {
						System.out.println("You need to be a manager to move a vehicle!");
					}
					break;
				}
				case "5" : {
					depot.addVehicle();
					System.out.println("Vehicle Added!");
					break;
				}
				case "6" : {
					depot.removeVehicle();
					System.out.println("Vehicle Removed!");
					break;
				}
				case "7" : {
					depot.addNewDriver();
					System.out.println("Driver Added!");
					break;
				}
				
			}
		} while (!choice.equals("8"));
	}
	*/
	
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
			System.out.println(e.getMessage());
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
			System.out.println(e.getMessage());
		}
	}

}
