package src.menu.gamemenu;

import src.utility.*;
import src.menu.*;
import src.playerstuff.Person;
import java.util.Scanner;

public class AlgojekJobsMenu {

	private Person person;
	private String[] menuItems;
	private Validator validator;
	private ValidatedMenu menu;
	private Scanner sc;

	public AlgojekJobsMenu(Person person) {
		this.person = person;
        this.menuItems = new String[] {
			"Algo-Ride       (+[Rp.  5,000 to Rp  35,000] | -[3 to 10] Energy | -[10% to 40%] Fuel)",
			"Algo-Send       (+[Rp. 20,000 to Rp  50,000] | -[5 to  8] Energy | -[25% to 55%] Fuel)",
			"Algo-Food       (+[Rp. 15,000 to Rp 100,000] | -[3 to 15] Energy | -[30% to 60%] Fuel)",
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
        this.menu = new ValidatedMenu(menuItems, validator);
        this.sc = new Scanner(System.in);
	}

    public void prompt() {
		do {
	        int choice = Tool.getIntegerInputWithRange(1, menuItems.length,
						() -> {
							Tool.clearScreen();
	    					person.print();
	    					System.out.println("\n"+Tool.rep('-',17)+"Algojek Jobs"+Tool.rep('-',17));
	    					menu.print(" - Not enough Energy/Fuel", "");
	    					System.out.print("\nChoice(1-4): ");
						});

			if (choice == 4) {
				break;
			} else if (validator.getValidationCode(choice-1) == 1) {
				if (choice == 1) {

				} else if (choice == 2) {

				} else if (choice == 3) {

				}
			}
			System.out.print("\nPress <enter> to Continue...");
			Tool.waitForEnterKeyPressed(() -> {});
		} while (true);
    }
}
