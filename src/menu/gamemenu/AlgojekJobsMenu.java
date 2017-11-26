package src.menu.gamemenu;

import src.utility.*;
import src.menu.*;
import src.menu.orderscreen.*;
import src.playerstuff.Person;
import java.util.Scanner;

public class AlgojekJobsMenu {

	private Person person;
	private String[] menuItems;
	private Validator validator;
	private ValidatedMenu menu;
	private Scanner sc;
	private String[][] phoneSprites;

	public AlgojekJobsMenu(Person person) {
		this.person = person;
        this.menuItems = new String[] {
			"Algo-Ride",
			"Algo-Send",
			"Algo-Food",
			"Back"
        };
        this.validator = (idx) -> {
            if (idx == 0) {
                if (person.getEnergy() >= 3 && person.getMotorcycle().getFuel() >= 10) return 1;
                return -1;
            } else if (idx == 1) {
				if (person.getEnergy() >= 5 && person.getMotorcycle().getFuel() >= 25) return 1;
				return -1;
            } else if (idx == 2) {
				if (person.getEnergy() >= 3 && person.getMotorcycle().getFuel() >= 30) return 1;
				return -1;
            }
            return 1;
        };
        this.menu = new ValidatedMenu(menuItems, validator, 7);
		this.phoneSprites = new String[][] {
			new String[] {Tool.getStringFromTextFile("res\\phone\\phone_0.txt", 1)},
			new String[] {Tool.getStringFromTextFile("res\\phone\\phone_10.txt", 1),
							Tool.getStringFromTextFile("res\\phone\\phone_11.txt", 1)},
			new String[] {Tool.getStringFromTextFile("res\\phone\\phone_20.txt", 1),
							Tool.getStringFromTextFile("res\\phone\\phone_21.txt", 1)},
			new String[] {Tool.getStringFromTextFile("res\\phone\\phone_30.txt", 1),
							Tool.getStringFromTextFile("res\\phone\\phone_31.txt", 1)}
		};
        this.sc = new Scanner(System.in);
	}

    public void prompt() {
		do {
			int choice = Tool.getIntegerInputWithRange(1, menuItems.length,
	                    () -> {
	                        Tool.clearScreen();
	                        person.print();
	                        System.out.printf("\n%s<Your Smartphone>\n", Tool.rep(' ', 7));
	                        System.out.println(phoneSprites[0][0]);
	                        System.out.printf("%sPlease choose a job!\n", Tool.rep(' ',7));
	                        menu.print(" - Not enough Energy/Fuel", "");
	                        System.out.printf("\n%sChoice(1-4): ", Tool.rep(' ',7));
	                    });

			if (choice == 4) {
				break;
			} else if (validator.getValidationCode(choice-1) == 1) {
				if (choice == 1) { 			// Algo-Ride
					printWholeWithPhone(1, 1, choice); Tool.sleep(250);
					printWholeWithPhone(1, 0, choice); Tool.sleep(250);
					printWholeWithPhone(1, 1, choice); Tool.sleep(250);
					printWholeWithPhone(1, 0, choice); Tool.sleep(250);
					AlgoRideOrderScreen algoRideOrderScreen = new AlgoRideOrderScreen(person);
					algoRideOrderScreen.prompt();
				} else if (choice == 2) {	// Algo-Send
					printWholeWithPhone(2, 1, choice); Tool.sleep(250);
					printWholeWithPhone(2, 0, choice); Tool.sleep(250);
					printWholeWithPhone(2, 1, choice); Tool.sleep(250);
					printWholeWithPhone(2, 0, choice); Tool.sleep(250);
					AlgoSendOrderScreen algoSendOrderScreen = new AlgoSendOrderScreen(person);
					algoSendOrderScreen.prompt();
				} else if (choice == 3) {	// Algo-Food
					printWholeWithPhone(3, 1, choice); Tool.sleep(250);
					printWholeWithPhone(3, 0, choice); Tool.sleep(250);
					printWholeWithPhone(3, 1, choice); Tool.sleep(250);
					printWholeWithPhone(3, 0, choice); Tool.sleep(250);
					AlgoFoodOrderScreen algoFoodOrderScreen = new AlgoFoodOrderScreen(person);
					algoFoodOrderScreen.prompt();
				}
			}
		} while (true);
    }

	private void printWholeWithPhone(int r, int c, int choice) {
		Tool.clearScreen();
		person.print();
		System.out.printf("\n%s<Your Smartphone>\n", Tool.rep(' ', 7));
		System.out.println(phoneSprites[r][c]);
		System.out.printf("%sPlease choose a job!\n", Tool.rep(' ',7));
		menu.print(" - Not enough Energy/Fuel", "");
		System.out.printf("\n%sChoice(1-4): %d", Tool.rep(' ',7), choice);
	}
}
