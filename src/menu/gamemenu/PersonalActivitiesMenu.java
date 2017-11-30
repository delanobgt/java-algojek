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
            "Study the Science of Algojek",
            "Go to Celebrity Gym         ",
            "Go to Barber Shop           ",
            "Go Shopping at Petisah      ",
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
        this.menu = new ValidatedMenu(menuItems, validator, 18);
        this.sc = new Scanner(System.in);
    }

    public void prompt() {
        String peopleSprite = Tool.getStringFromTextFile("res\\sprites\\people.txt", 20);
        do {
            int choice = Tool.getIntegerInputWithRange(0, menuItems.length-1,
                    () -> {
                        Tool.clearScreen();
                        person.print();
                        System.out.printf("\n%s<Your personal life>\n", Tool.rep(' ', 18));
                        System.out.println(peopleSprite);
                        System.out.printf("%sWhat do you want to do?\n", Tool.rep(' ',18));
                        menu.print(" - Not enough Money/Energy", " - Still Full");
                        System.out.printf("\n%sChoice(0-4): ", Tool.rep(' ',18));
                    });

            if (choice == 0) {
                break;
            } else if (validator.getValidationCode(choice-1) == 1) {
                if (choice == 1) {
                    String sprite = Tool.getStringFromTextFile("res\\sprites\\school.txt", 30);
                    person.setMoney(person.getMoney()-60_000);
					person.setEnergy(person.getEnergy()-10);
					for (int i = 1; i <= 15; i++) {
							Tool.clearScreen();
							person.print();
							System.out.printf("\n%s<Your personal life>\n", Tool.rep(' ', 43));
                            System.out.printf("\n%sLearning the Science of Algojek..\n", Tool.rep(' ', 38));
                            System.out.println(sprite);
                            System.out.printf("%s[%-"+(2*15-1)+"s] %d%%\n", Tool.rep(' ',36), Tool.rep("==",i-1)+">", (int)(i*100.0/15) );
                            Tool.sleep(375);
					}
                    int intelligenceIncrease = Tool.getRandomIntegerWithRange(5, 15);
					person.setIntelligence(person.getIntelligence()+intelligenceIncrease);

					Tool.clearScreen();
					person.print();
                    System.out.printf("\n%s<Your personal life>\n", Tool.rep(' ', 43));
                    System.out.printf("\n%sLearning done\n", Tool.rep(' ', 47));
                    System.out.println(sprite);
                    System.out.printf("%s[%s] %d%%\n\n", Tool.rep(' ',36), Tool.rep("==",14)+">", 100);
					System.out.printf("%sMoney(-Rp. %,d) -> Rp. %,d\n", Tool.rep(' ',36), 60_000, person.getMoney());
					System.out.printf("%sEnergy(-%d) -> %d\n", Tool.rep(' ',36), 10, person.getEnergy());
					System.out.printf("%sIntelligence increased by %d points\n", Tool.rep(' ',36), intelligenceIncrease);
                } else if (choice == 2) {
                    String sprite = Tool.getStringFromTextFile("res\\sprites\\workout.txt", 17);
                    person.setMoney(person.getMoney()-40_000);
                    person.setEnergy(person.getEnergy()-15);

                    for (int i = 1; i <= 15; i++) {
							Tool.clearScreen();
							person.print();
							System.out.printf("\n%s<Your personal life>\n", Tool.rep(' ', 43));
                            System.out.printf("\n%sWorking out..\n", Tool.rep(' ', 47));
                            System.out.println(sprite);
                            System.out.printf("%s[%-"+(2*15-1)+"s] %d%%\n", Tool.rep(' ',36), Tool.rep("==",i-1)+">", (int)(i*100.0/15) );
                            Tool.sleep(375);
					}
                    int muscleStrengthIncrease = Tool.getRandomIntegerWithRange(10, 15);
                    person.setMuscleStrength(person.getMuscleStrength()+muscleStrengthIncrease);

                    int maxEnergyIncrease = -1;
                    if (Math.random() <= 0.45)  {
                        maxEnergyIncrease = Tool.getRandomIntegerWithRange(1, 10);
                        person.setMaxEnergy(person.getMaxEnergy()+maxEnergyIncrease);
                    }

                    Tool.clearScreen();
                    person.print();
                    System.out.printf("\n%s<Your personal life>\n", Tool.rep(' ', 43));
                    System.out.printf("\n%sWorkout done\n", Tool.rep(' ', 47));
                    System.out.println(sprite);
                    System.out.printf("%s[%s] %d%%\n\n", Tool.rep(' ',36), Tool.rep("==",14)+">", 100);
                    System.out.printf("%sMoney(-Rp. %,d) -> Rp. %,d\n", Tool.rep(' ',36), 40_000, person.getMoney());
                    System.out.printf("%sEnergy(-%d) -> %d\n", Tool.rep(' ',36), 15, person.getEnergy());
                    System.out.printf("%sMuscle Strength increased by %d points\n", Tool.rep(' ',36), muscleStrengthIncrease);
                    if (maxEnergyIncrease != -1)
                        System.out.printf("%sCongratz, you successfully extended Max Energy by %d points\n", Tool.rep(' ',36), maxEnergyIncrease);
                } else if (choice == 3) {
                    String sprite = Tool.getStringFromTextFile("res\\sprites\\barber.txt", 25);
                    person.setMoney(person.getMoney()-50_000);
                    person.setEnergy(person.getEnergy()-5);
                    for (int i = 1; i <= 15; i++) {
							Tool.clearScreen();
							person.print();
							System.out.printf("\n%s<Your personal life>\n", Tool.rep(' ', 43));
                            System.out.printf("\n%sStyling your hair..\n", Tool.rep(' ', 44));
                            System.out.println(sprite);
                            System.out.printf("%s[%-"+(2*15-1)+"s] %d%%\n", Tool.rep(' ',36), Tool.rep("==",i-1)+">", (int)(i*100.0/15) );
                            Tool.sleep(375);
					}
                    int attractivenessIncrease = Tool.getRandomIntegerWithRange(0, 25);
                    person.setAttractiveness(person.getAttractiveness()+attractivenessIncrease);

                    Tool.clearScreen();
                    person.print();
                    System.out.printf("\n%s<Your personal life>\n", Tool.rep(' ', 43));
                    System.out.printf("\n%sHairstyling done\n", Tool.rep(' ', 45));
                    System.out.println(sprite);
                    System.out.printf("%s[%s] %d%%\n\n", Tool.rep(' ',36), Tool.rep("==",14)+">", 100);
                    System.out.printf("%sMoney(-Rp. %,d) -> Rp. %,d\n", Tool.rep(' ',36), 50_000, person.getMoney());
                    System.out.printf("%sEnergy(-%d) -> %d\n", Tool.rep(' ',36), 5, person.getEnergy());
                    System.out.printf("%sAttractiveness increased by %d points\n", Tool.rep(' ',36), attractivenessIncrease);
                } else if (choice == 4) {
                    String sprite = Tool.getStringFromTextFile("res\\sprites\\shopping.txt", 40);
                    person.setMoney(person.getMoney()-100_000);
                    person.setEnergy(person.getEnergy()-10);
                    for (int i = 1; i <= 15; i++) {
							Tool.clearScreen();
							person.print();
							System.out.printf("\n%s<Your personal life>\n", Tool.rep(' ', 43));
                            System.out.printf("\n%sShopping at Petisah..\n", Tool.rep(' ', 43));
                            System.out.println(sprite);
                            System.out.printf("%s[%-"+(2*15-1)+"s] %d%%\n", Tool.rep(' ',36), Tool.rep("==",i-1)+">", (int)(i*100.0/15) );
                            Tool.sleep(375);
					}
                    int attractivenessIncrease = Tool.getRandomIntegerWithRange(55, 75);
                    person.setAttractiveness(person.getAttractiveness()+attractivenessIncrease);

                    Tool.clearScreen();
                    person.print();
                    System.out.printf("\n%s<Your personal life>\n", Tool.rep(' ', 43));
                    System.out.printf("\n%sShopping done\n", Tool.rep(' ', 47));
                    System.out.println(sprite);
                    System.out.printf("%s[%s] %d%%\n\n", Tool.rep(' ',36), Tool.rep("==",14)+">", 100);
                    System.out.printf("%sMoney(-Rp. %,d) -> Rp. %,d\n", Tool.rep(' ',36), 100_000, person.getMoney());
                    System.out.printf("%sEnergy(-%d) -> %d\n", Tool.rep(' ',36), 10, person.getEnergy());
                    System.out.printf("%sAttractiveness increased by %d points\n", Tool.rep(' ',36), attractivenessIncrease);
                }
                System.out.printf("\n%sPress <enter> to Continue..", Tool.rep(' ',39));
				Tool.waitForEnterKeyPressed(() -> {});
            }
        } while (true);
    }

}
