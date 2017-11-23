package src.menu.gamemenu;

import src.utility.*;
import src.menu.*;
import src.playerstuff.Person;
import java.util.Scanner;

public class PersonalActivitiesMenu {

    private Person person;
	private String[] menuItems;
	private Validator validator;
	private ValidatedMenu menu;
	private Scanner sc;

    public PersonalActivitiesMenu(Person person) {
        this.person = person;
        this.menuItems = new String[] {
            "Study the Science of Algojek (-Rp.  60,000 | -10 Energy | +[10 to 15] Intelligence    )",
            "Go to Celebrity Gym          (-Rp.  40,000 | -15 Energy | +[10 to 15] Muscle Strength )",
            "Go to Barber Shop            (-Rp.  50,000 |  -5 Energy |  +[0 to 25] Attractiveness  )",
            "Go Shopping at Petisah       (-Rp. 100,000 | -10 Energy | +[55 to 75] Attractiveness  )",
            "Back"
        };
        this.validator = (idx) -> {
            if (idx == 0) {
                if (person.getIntelligence() == 100) return 0;
                else if (person.getMoney() >= 60_000 && person.getEnergy() >= 10) return 1;
                return -1;
            } else if (idx == 1) {
                if (person.getMuscleStrength() == 100) return 0;
                else if (person.getMoney() >= 40_000 && person.getEnergy() >= 15) return 1;
                return -1;
            } else if (idx == 2) {
                if (person.getAttractiveness() == 100) return 0;
                else if (person.getMoney() >= 50_000 && person.getEnergy() >= 5) return 1;
                return -1;
            } else if (idx == 3) {
                if (person.getAttractiveness() == 100) return 0;
                else if (person.getMoney() >= 100_000 && person.getEnergy() >= 10) return 1;
                return -1;
            }
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
    					System.out.println("\n"+Tool.rep('-',17)+"Personal Activities"+Tool.rep('-',17));
    					menu.print(" - Not enough Money/Energy", " - Still Full");
    					System.out.print("\nChoice(1-5): ");
                    });

            if (choice == 5) {
                break;
            } else if (validator.getValidationCode(choice-1) == 1) {
                if (choice == 1) {
                    person.setMoney(person.getMoney()-60_000);
					person.setEnergy(person.getEnergy()-10);
					Tool.showProgressBar(barWidth, delay,
						() -> {
							Tool.clearScreen();
							person.print();
							System.out.print("\n-----Status-----\n");
							System.out.printf("\nMoney(-Rp. %,d) -> Rp. %,d\n", 60_000, person.getMoney());
                            System.out.printf("Energy(-%d) -> %d\n", 10, person.getEnergy());
							System.out.print("\nStudying the Science of Algojek:\n");
						});
                    int intelligenceIncrease = Tool.getRandomIntegerWithRange(10, 15);
					person.setIntelligence(person.getIntelligence()+intelligenceIncrease);

					Tool.clearScreen();
					person.print();
					System.out.print("\n-----Status-----\n");
					System.out.printf("\nMoney(-Rp. %,d) -> Rp. %,d\n", 60_000, person.getMoney());
					System.out.printf("Energy(-%d) -> %d\n", 10, person.getEnergy());
                    System.out.print("\nStudying the Science of Algojek:\n");
					System.out.printf("|%s>| %d%%\n", Tool.rep("==", barWidth-1), 100);
					System.out.println("\nIntelligence increased by "+intelligenceIncrease+" points");
                } else if (choice == 2) {
                    person.setMoney(person.getMoney()-40_000);
                    person.setEnergy(person.getEnergy()-15);
                    Tool.showProgressBar(barWidth, delay,
                        () -> {
                            Tool.clearScreen();
                            person.print();
                            System.out.print("\n-----Status-----\n");
                            System.out.printf("\nMoney(-Rp. %,d) -> Rp. %,d\n", 40_000, person.getMoney());
                            System.out.printf("Energy(-%d) -> %d\n", 15, person.getEnergy());
                            System.out.print("\nWorkout in progress:\n");
                        });
                    int muscleStrengthIncrease = Tool.getRandomIntegerWithRange(10, 15);
                    person.setMuscleStrength(person.getMuscleStrength()+muscleStrengthIncrease);

                    Tool.clearScreen();
                    person.print();
                    System.out.print("\n-----Status-----\n");
                    System.out.printf("\nMoney(-Rp. %,d) -> Rp. %,d\n", 40_000, person.getMoney());
                    System.out.printf("Energy(-%d) -> %d\n", 15, person.getEnergy());
                    System.out.print("\nWorkout in progress:\n");
                    System.out.printf("|%s>| %d%%\n", Tool.rep("==", barWidth-1), 100);
                    System.out.println("\nMuscle Strength increased by "+muscleStrengthIncrease+" points");
                } else if (choice == 3) {
                    person.setMoney(person.getMoney()-50_000);
                    person.setEnergy(person.getEnergy()-5);
                    Tool.showProgressBar(barWidth, delay,
                        () -> {
                            Tool.clearScreen();
                            person.print();
                            System.out.print("\n-----Status-----\n");
                            System.out.printf("\nMoney(-Rp. %,d) -> Rp. %,d\n", 50_000, person.getMoney());
                            System.out.printf("Energy(-%d) -> %d\n", 5, person.getEnergy());
                            System.out.print("\nHaircut styling in progress:\n");
                        });
                    int attractivenessIncrease = Tool.getRandomIntegerWithRange(0, 25);
                    person.setAttractiveness(person.getAttractiveness()+attractivenessIncrease);

                    Tool.clearScreen();
                    person.print();
                    System.out.print("\n-----Status-----\n");
                    System.out.printf("\nMoney(-Rp. %,d) -> Rp. %,d\n", 50_000, person.getMoney());
                    System.out.printf("Energy(-%d) -> %d\n", 5, person.getEnergy());
                    System.out.print("\nHaircut styling in progress:\n");
                    System.out.printf("|%s>| %d%%\n", Tool.rep("==", barWidth-1), 100);
                    System.out.println("\nAttractiveness increased by "+attractivenessIncrease+" points");
                } else if (choice == 4) {
                    person.setMoney(person.getMoney()-100_000);
                    person.setEnergy(person.getEnergy()-10);
                    Tool.showProgressBar(barWidth, delay,
                        () -> {
                            Tool.clearScreen();
                            person.print();
                            System.out.print("\n-----Status-----\n");
                            System.out.printf("\nMoney(-Rp. %,d) -> Rp. %,d\n", 100_000, person.getMoney());
                            System.out.printf("Energy(-%d) -> %d\n", 10, person.getEnergy());
                            System.out.print("\nShopping in progress:\n");
                        });
                    int attractivenessIncrease = Tool.getRandomIntegerWithRange(55, 75);
                    person.setAttractiveness(person.getAttractiveness()+attractivenessIncrease);

                    Tool.clearScreen();
                    person.print();
                    System.out.print("\n-----Status-----\n");
                    System.out.printf("\nMoney(-Rp. %,d) -> Rp. %,d\n", 100_000, person.getMoney());
                    System.out.printf("Energy(-%d) -> %d\n", 10, person.getEnergy());
                    System.out.print("\nShopping in progress:\n");
                    System.out.printf("|%s>| %d%%\n", Tool.rep("==", barWidth-1), 100);
                    System.out.println("\nAttractiveness increased by "+attractivenessIncrease+" points");
                }
                System.out.print("\nPress <enter> to Continue...");
				Tool.waitForEnterKeyPressed(() -> {});
            }
        } while (true);
    }

}
