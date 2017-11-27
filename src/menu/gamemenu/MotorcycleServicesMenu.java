package src.menu.gamemenu;

import src.utility.*;
import src.menu.*;
import src.playerstuff.*;
import java.util.*;

public class MotorcycleServicesMenu {

	private Person person;
	private Motorcycle motorcycle;
	private String[] menuItems;
	private Validator validator;
	private ValidatedMenu menu;
	private Scanner sc;

	public MotorcycleServicesMenu(Person person) {
		this.person = person;
		this.motorcycle = person.getMotorcycle();
		this.menuItems = new String[] {
			"All-in-One Service",
			"Refill the Fuel   ",
			"Oil Service       ",
			"Engine Service    ",
			"Battery Service   ",
			"Back"
		};
		// Code: -1:not enough money, 0:still 100%, 1:(available)
		this.validator = (idx) -> {
			if (idx == 0)
				if (motorcycle.getFuel() == 100 &&
					motorcycle.getOilQuality() == 100 &&
					motorcycle.getEngineHealth() == 100 &&
					motorcycle.getBatteryHealth() == 100) return 0;
				else if (person.getMoney() >= 100_000 && person.getEnergy() >= 17) return 1;
				else return -1;
			else if (idx == 1)
				if (motorcycle.getFuel() == 100) return 0;
				else if (person.getMoney() >= 20_000 && person.getEnergy() >= 3) return 1;
				else return -1;
			else if (idx == 2)
				if (motorcycle.getOilQuality() == 100) return 0;
				else if (person.getMoney() >= 35_000 && person.getEnergy() >= 5) return 1;
				else return -1;
			else if (idx == 3)
				if (motorcycle.getEngineHealth() == 100) return 0;
				else if (person.getMoney() >= 50_000 && person.getEnergy() >= 8) return 1;
				else return -1;
			else if (idx == 4)
				if (motorcycle.getBatteryHealth() == 100) return 0;
				else if (person.getMoney() >= 15_000 && person.getEnergy() >= 4) return 1;
				else return -1;
			return 1;
		};
		this.menu = new ValidatedMenu(menuItems, validator, 5);
		this.sc = new Scanner(System.in);
	}

	public void prompt() {
		int barWidth = 12;
		int delay = 125;
		String garageSprite = Tool.getStringFromTextFile("res\\sprites\\garage.txt");
		do {
			int choice = Tool.getIntegerInputWithRange(1, menuItems.length,
				() -> {
					Tool.clearScreen();
					person.print();
					System.out.printf("\n%s<Motorcycle Services>\n", Tool.rep(' ', 7));
					System.out.println(garageSprite);
					System.out.printf("%sWhat do you want to do?\n", Tool.rep(' ',5));
					menu.print(" - Not enough Money/Energy", " - Still 100%");
					System.out.printf("\n%sChoice(1-6): ", Tool.rep(' ',5));
				});

			if (choice == 6) {
				break;
			} else if (validator.getValidationCode(choice-1) == 1) {
				if (choice == 1) {		// All-in-One Service
					String sprite = Tool.getStringFromTextFile("res\\sprites\\motorcycle.txt", 38);
					person.setMoney(person.getMoney()-100_000);
					person.setEnergy(person.getEnergy()-17);
					for (int i = 1; i <= 15; i++) {
						Tool.clearScreen();
						person.print();
						System.out.printf("\n%s<Motorcycle Services>\n", Tool.rep(' ', 42));
                        System.out.printf("\n%sAll-in-One Service in progress..\n", Tool.rep(' ', 38));
                        System.out.println(sprite);
                        System.out.printf("%s[%-"+(2*15-1)+"s] %d%%\n", Tool.rep(' ',36), Tool.rep("==",i-1)+">", (int)(i*100.0/15) );
                        Tool.sleep(375);
					}
					motorcycle.setFuel(100);
					motorcycle.setOilQuality(100);
					motorcycle.setEngineHealth(100);
					motorcycle.setBatteryHealth(100);

					Tool.clearScreen();
					person.print();
					System.out.printf("\n%s<Motorcycle Services>\n", Tool.rep(' ', 42));
					System.out.printf("\n%sAll-in-One Service done..\n", Tool.rep(' ', 41));
					System.out.println(sprite);
					System.out.printf("%s[%s] %d%%\n\n", Tool.rep(' ',36), Tool.rep("==",14)+">", 100);
					System.out.printf("%sMoney(-Rp. %,d) -> Rp. %,d\n", Tool.rep(' ',36), 100_000, person.getMoney());
					System.out.printf("%sEnergy(-%d) -> %d\n", Tool.rep(' ',36), 17, person.getEnergy());
					System.out.printf("%sFuel restored to 100%%\n", Tool.rep(' ',36));
					System.out.printf("%sOil Quality restored to 100%%\n", Tool.rep(' ',36));
					System.out.printf("%sEngine Health restored to 100%%\n", Tool.rep(' ',36));
					System.out.printf("%sBattery Health restored to 100%%\n", Tool.rep(' ',36));
				} else if (choice == 2) { 		// Refill fuel
					String sprite = Tool.getStringFromTextFile("res\\sprites\\fuel.txt", 38);
					person.setMoney(person.getMoney()-20_000);
					person.setEnergy(person.getEnergy()-3);
					for (int i = 1; i <= 15; i++) {
						Tool.clearScreen();
						person.print();
						System.out.printf("\n%s<Motorcycle Services>\n", Tool.rep(' ', 42));
                        System.out.printf("\n%sFuel Refill in progress..\n", Tool.rep(' ', 40));
                        System.out.println(sprite);
                        System.out.printf("%s[%-"+(2*15-1)+"s] %d%%\n", Tool.rep(' ',36), Tool.rep("==",i-1)+">", (int)(i*100.0/15) );
                        Tool.sleep(375);
					}
					motorcycle.setFuel(100);

					Tool.clearScreen();
					person.print();
					System.out.printf("\n%s<Motorcycle Services>\n", Tool.rep(' ', 42));
					System.out.printf("\n%sFuel Refill done..\n", Tool.rep(' ', 44));
					System.out.println(sprite);
					System.out.printf("%s[%s] %d%%\n\n", Tool.rep(' ',36), Tool.rep("==",14)+">", 100);
					System.out.printf("%sMoney(-Rp. %,d) -> Rp. %,d\n", Tool.rep(' ',36), 20_000, person.getMoney());
					System.out.printf("%sEnergy(-%d) -> %d\n", Tool.rep(' ',36), 3, person.getEnergy());
					System.out.printf("%sFuel restored to 100%%\n", Tool.rep(' ',36));
				} else if (choice == 3) {		// Oil Replacement
					String sprite = Tool.getStringFromTextFile("res\\sprites\\oil.txt", 45);
					person.setMoney(person.getMoney()-50_000);
					person.setEnergy(person.getEnergy()-5);
					for (int i = 1; i <= 15; i++) {
						Tool.clearScreen();
						person.print();
						System.out.printf("\n%s<Motorcycle Services>\n", Tool.rep(' ', 42));
                        System.out.printf("\n%sOil Replacement in progress..\n", Tool.rep(' ', 38));
                        System.out.println(sprite);
                        System.out.printf("%s[%-"+(2*15-1)+"s] %d%%\n", Tool.rep(' ',36), Tool.rep("==",i-1)+">", (int)(i*100.0/15) );
                        Tool.sleep(375);
					}
					motorcycle.setOilQuality(100);

					Tool.clearScreen();
					person.print();
					System.out.printf("\n%s<Motorcycle Services>\n", Tool.rep(' ', 42));
					System.out.printf("\n%sOil Replacement done..\n", Tool.rep(' ', 42));
					System.out.println(sprite);
					System.out.printf("%s[%s] %d%%\n\n", Tool.rep(' ',36), Tool.rep("==",14)+">", 100);
					System.out.printf("%sMoney(-Rp. %,d) -> Rp. %,d\n", Tool.rep(' ',36), 50_000, person.getMoney());
					System.out.printf("%sEnergy(-%d) -> %d\n", Tool.rep(' ',36), 5, person.getEnergy());
					System.out.printf("%sOil Quality restored to 100%%\n", Tool.rep(' ',36));
				} else if (choice == 4) {		// Engine Service
					String sprite = Tool.getStringFromTextFile("res\\sprites\\motorcycle.txt", 38);
					person.setMoney(person.getMoney()-35_000);
					person.setEnergy(person.getEnergy()-8);
					for (int i = 1; i <= 15; i++) {
						Tool.clearScreen();
						person.print();
						System.out.printf("\n%s<Motorcycle Services>\n", Tool.rep(' ', 42));
                        System.out.printf("\n%sEngine Service in progress..\n", Tool.rep(' ', 38));
                        System.out.println(sprite);
                        System.out.printf("%s[%-"+(2*15-1)+"s] %d%%\n", Tool.rep(' ',36), Tool.rep("==",i-1)+">", (int)(i*100.0/15) );
                        Tool.sleep(375);
					}
					motorcycle.setEngineHealth(100);

					Tool.clearScreen();
					person.print();
					System.out.printf("\n%s<Motorcycle Services>\n", Tool.rep(' ', 42));
					System.out.printf("\n%sEngine Service done..\n", Tool.rep(' ', 42));
					System.out.println(sprite);
					System.out.printf("%s[%s] %d%%\n\n", Tool.rep(' ',36), Tool.rep("==",14)+">", 100);
					System.out.printf("%sMoney(-Rp. %,d) -> Rp. %,d\n", Tool.rep(' ',36), 35_000, person.getMoney());
					System.out.printf("%sEnergy(-%d) -> %d\n", Tool.rep(' ',36), 8, person.getEnergy());
					System.out.printf("%sEngine Health restored to 100%%\n", Tool.rep(' ',36));
				} else if (choice == 5) {		// Battery Service
					String sprite = Tool.getStringFromTextFile("res\\sprites\\battery.txt", 43);
					person.setMoney(person.getMoney()-15_000);
					person.setEnergy(person.getEnergy()-4);
					for (int i = 1; i <= 15; i++) {
						Tool.clearScreen();
						person.print();
						System.out.printf("\n%s<Motorcycle Services>\n", Tool.rep(' ', 42));
                        System.out.printf("\n%sBattery Service in progress..\n", Tool.rep(' ', 38));
                        System.out.println(sprite);
                        System.out.printf("%s[%-"+(2*15-1)+"s] %d%%\n", Tool.rep(' ',36), Tool.rep("==",i-1)+">", (int)(i*100.0/15) );
                        Tool.sleep(375);
					}
					motorcycle.setBatteryHealth(100);

					Tool.clearScreen();
					person.print();
					System.out.printf("\n%s<Motorcycle Services>\n", Tool.rep(' ', 42));
					System.out.printf("\n%sBattery Service done..\n", Tool.rep(' ', 42));
					System.out.println(sprite);
					System.out.printf("%s[%s] %d%%\n\n", Tool.rep(' ',36), Tool.rep("==",14)+">", 100);
					System.out.printf("%sMoney(-Rp. %,d) -> Rp. %,d\n", Tool.rep(' ',36), 15_000, person.getMoney());
					System.out.printf("%sEnergy(-%d) -> %d\n", Tool.rep(' ',36), 4, person.getEnergy());
					System.out.printf("%sBattery Health restored to 100%%\n", Tool.rep(' ',36));
				}
				System.out.printf("\n%sPress <enter> to Continue..", Tool.rep(' ',39));
				Tool.waitForEnterKeyPressed(() -> {});
			}
		} while (true);
	}

}
