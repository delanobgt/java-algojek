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
			"All-in-One Service (-Rp. 100,000 |-17 Energy | All Motorcycle State 100% )",
			"Refill the Fuel    (-Rp.  20,000 | -3 Energy | Fuel 100%                 )",
			"Engine Service     (-Rp.  50,000 | -8 Energy | Engine Health 100%        )",
			"Oil Service        (-Rp.  35,000 | -5 Energy | Oil Quality 100%          )",
			"Suspension Service (-Rp.  25,000 | -5 Energy | Suspension Health 100%    )",
			"Battery Service    (-Rp.  15,000 | -4 Energy | Battery Health 100%       )",
			"Back"
		};
		// Code: -1:not enough money, 0:still 100%, 1:(available)
		this.validator = (idx) -> {
			if (idx == 0)
				if (motorcycle.getFuel() == 100 && motorcycle.getOilQuality() == 100 &&
					motorcycle.getEngineHealth() == 100 && motorcycle.getSuspensionHealth() == 100 &&
					motorcycle.getBatteryHealth() == 100) return 0;
				else if (person.getMoney() >= 100_000 && person.getEnergy() >= 17) return 1;
				else return -1;
			else if (idx == 1)
				if (motorcycle.getFuel() == 100) return 0;
				else if (person.getMoney() >= 20_000 && person.getEnergy() >= 3) return 1;
				else return -1;
			else if (idx == 2)
				if (motorcycle.getEngineHealth() == 100) return 0;
				else if (person.getMoney() >= 50_000 && person.getEnergy() >= 8) return 1;
				else return -1;
			else if (idx == 3)
				if (motorcycle.getOilQuality() == 100) return 0;
				else if (person.getMoney() >= 35_000 && person.getEnergy() >= 5) return 1;
				else return -1;
			else if (idx == 4)
				if (motorcycle.getSuspensionHealth() == 100) return 0;
				else if (person.getMoney() >= 25_000 && person.getEnergy() >= 5) return 1;
				else return -1;
			else if (idx == 5)
				if (motorcycle.getBatteryHealth() == 100) return 0;
				else if (person.getMoney() >= 15_000 && person.getEnergy() >= 4) return 1;
				else return -1;
			return 1;
		};
		this.menu = new ValidatedMenu(menuItems, validator);
		this.sc = new Scanner(System.in);
	}

	public void prompt() {
		int barWidth = 12;
		int delay = 125;
		do {
			int choice = Tool.getIntegerInputWithRange(1, menuItems.length,
				() -> {
					Tool.clearScreen();
					person.print();
					System.out.println("\n"+Tool.rep('-',17)+"Motorcycle Services"+Tool.rep('-',17));
					menu.print(" - Not enough Money/Energy", " - Still 100%");
					System.out.print("\nChoice(1-7): ");
				});

			if (choice == 7) {
				break;
			} else if (validator.getValidationCode(choice-1) == 1) {
				if (choice == 1) {
					person.setMoney(person.getMoney()-100_000);
					person.setEnergy(person.getEnergy()-17);
					Tool.showProgressBar(barWidth, delay,
						() -> {
							Tool.clearScreen();
							person.print();
							System.out.print("\n-----Status-----\n");
							System.out.printf("\nMoney(-Rp. %,d) -> Rp. %,d\n", 100_000, person.getMoney());
							System.out.printf("Energy(-%d) -> %d\n", 17, person.getEnergy());
							System.out.print("\nAll-in-One Service In Progress:\n");
						});
					motorcycle.setFuel(100);
					motorcycle.setOilQuality(100);
					motorcycle.setEngineHealth(100);
					motorcycle.setSuspensionHealth(100);
					motorcycle.setBatteryHealth(100);

					Tool.clearScreen();
					person.print();
					System.out.print("\n-----Status-----\n");
					System.out.printf("\nMoney(-Rp. %,d) -> Rp. %,d\n", 100_000, person.getMoney());
					System.out.printf("Energy(-%d) -> %d\n", 17, person.getEnergy());
					System.out.print("\nAll-in-One Service In Progress:\n");
					System.out.printf("|%s>| %d%%\n", Tool.rep("==", barWidth-1), 100);
					System.out.println("\nFuel restored to 100%");
					System.out.println("Oil Quality restored to 100%");
					System.out.println("Engine Health restored to 100%");
					System.out.println("Suspension Health restored to 100%");
					System.out.println("Battery Health restored to 100%");
				} else if (choice == 2) {
					person.setMoney(person.getMoney()-20_000);
					person.setEnergy(person.getEnergy()-3);
					Tool.showProgressBar(barWidth, delay,
						() -> {
							Tool.clearScreen();
							person.print();
							System.out.print("\n-----Status-----\n");
							System.out.printf("\nMoney(-Rp. %,d) -> Rp. %,d\n", 20_000, person.getMoney());
							System.out.printf("Energy(-%d) -> %d\n", 3, person.getEnergy());
							System.out.print("\nFuel Refill In Progress:\n");
						});
					motorcycle.setFuel(100);

					Tool.clearScreen();
					person.print();
					System.out.print("\n-----Status-----\n");
					System.out.printf("\nMoney(-Rp. %,d) -> Rp. %,d\n", 20_000, person.getMoney());
					System.out.printf("Energy(-%d) -> %d\n", 3, person.getEnergy());
					System.out.print("\nFuel Refill In Progress:\n");
					System.out.printf("|%s>| %d%%\n", Tool.rep("==", barWidth-1), 100);
					System.out.println("\nFuel restored to 100%");
				} else if (choice == 3) {
					person.setMoney(person.getMoney()-35_000);
					person.setEnergy(person.getEnergy()-8);
					Tool.showProgressBar(barWidth, delay,
						() -> {
							Tool.clearScreen();
							person.print();
							System.out.print("\n-----Status-----\n");
							System.out.printf("\nMoney(-Rp. %,d) -> Rp. %,d\n", 35_000, person.getMoney());
							System.out.printf("Energy(-%d) -> %d\n", 8, person.getEnergy());
							System.out.print("\nEngine Service In Progress:\n");
						});
					motorcycle.setEngineHealth(100);

					Tool.clearScreen();
					person.print();
					System.out.print("\n-----Status-----\n");
					System.out.printf("\nMoney(-Rp. %,d) -> Rp. %,d\n", 35_000, person.getMoney());
					System.out.printf("Energy(-%d) -> %d\n", 8, person.getEnergy());
					System.out.print("\nEngine Service In Progress:\n");
					System.out.printf("|%s>| %d%%\n", Tool.rep("==", barWidth-1), 100);
					System.out.println("\nEngine Health restored to 100%");
				} else if (choice == 4) {
					person.setMoney(person.getMoney()-50_000);
					person.setEnergy(person.getEnergy()-5);
					Tool.showProgressBar(barWidth, delay,
						() -> {
							Tool.clearScreen();
							person.print();
							System.out.print("\n-----Status-----\n");
							System.out.printf("\nMoney(-Rp. %,d) -> Rp. %,d\n", 50_000, person.getMoney());
							System.out.printf("Energy(-%d) -> %d\n", 5, person.getEnergy());
							System.out.print("\nOil Exchange In Progress:\n");
						});
					motorcycle.setOilQuality(100);

					Tool.clearScreen();
					person.print();
					System.out.print("\n-----Status-----\n");
					System.out.printf("\nMoney(-Rp. %,d) -> Rp. %,d\n", 50_000, person.getMoney());
					System.out.printf("Energy(-%d) -> %d\n", 5, person.getEnergy());
					System.out.print("\nOil Exchange In Progress:\n");
					System.out.printf("|%s>| %d%%\n", Tool.rep("==", barWidth-1), 100);
					System.out.println("\nOil Quality restored to 100%");
				} else if (choice == 5) {
					person.setMoney(person.getMoney()-25_000);
					person.setEnergy(person.getEnergy()-5);
					Tool.showProgressBar(barWidth, delay,
						() -> {
							Tool.clearScreen();
							person.print();
							System.out.print("\n-----Status-----\n");
							System.out.printf("\nMoney(-Rp. %,d) -> Rp. %,d\n", 25_000, person.getMoney());
							System.out.printf("Energy(-%d) -> %d\n", 5, person.getEnergy());
							System.out.print("\nSuspension Recalibrate In Progress:\n");
						});
					motorcycle.setSuspensionHealth(100);

					Tool.clearScreen();
					person.print();
					System.out.print("\n-----Status-----\n");
					System.out.printf("\nMoney(-Rp. %,d) -> Rp. %,d\n", 25_000, person.getMoney());
					System.out.printf("Energy(-%d) -> %d\n", 5, person.getEnergy());
					System.out.print("\nSuspension Recalibrate In Progress:\n");
					System.out.printf("|%s>| %d%%\n", Tool.rep("==", barWidth-1), 100);
					System.out.println("\nSuspension Health restored to 100%");
				} else if (choice == 6) {
					person.setMoney(person.getMoney()-15_000);
					person.setEnergy(person.getEnergy()-4);
					Tool.showProgressBar(barWidth, delay,
						() -> {
							Tool.clearScreen();
							person.print();
							System.out.print("\n-----Status-----\n");
							System.out.printf("\nMoney(-Rp. %,d) -> Rp. %,d\n", 15_000, person.getMoney());
							System.out.printf("Energy(-%d) -> %d\n", 4, person.getEnergy());
							System.out.print("\nBattery Refill In Progress:\n");
						});
					motorcycle.setBatteryHealth(100);

					Tool.clearScreen();
					person.print();
					System.out.print("\n-----Status-----\n");
					System.out.printf("\nMoney(-Rp. %,d) -> Rp. %,d\n", 15_000, person.getMoney());
					System.out.printf("Energy(-%d) -> %d\n", 4, person.getEnergy());
					System.out.print("\nBattery Refill In Progress:\n");
					System.out.printf("|%s>| %d%%\n", Tool.rep("==", barWidth-1), 100);
					System.out.println("\nBattery Health restored to 100%");
				}
				System.out.print("\nPress <enter> to Continue...");
				Tool.waitForEnterKeyPressed(() -> {});
			}
		} while (true);
	}

}
