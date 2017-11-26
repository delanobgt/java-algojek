package src.menu.gamemenu;

import src.utility.*;
import src.menu.*;
import src.playerstuff.Person;
import java.util.Scanner;

public class SleepMenu {
    private Person person;
    private Menu menu;
    private String[] menuItems;
    private String[] bedSprites;
    private Scanner sc;

    public SleepMenu(Person person) {
        this.person = person;
        this.menuItems = new String[] {
            "Sleep",
            "Back"
        };
        this.menu = new Menu(menuItems, 40);
        this.bedSprites = new String[] {
            Tool.getStringFromTextFile("res\\bed\\bed_0.txt",35),
            Tool.getStringFromTextFile("res\\bed\\bed_1.txt",35),
            Tool.getStringFromTextFile("res\\bed\\bed_2.txt",35),
            Tool.getStringFromTextFile("res\\bed\\bed_3.txt",35)
        };
        this.sc = new Scanner(System.in);
    }

    public void prompt() {
        int choice = Tool.getIntegerInputWithRange(1, 2,
                    () -> {
                        Tool.clearScreen();
                        person.print();
                        System.out.printf("\n%s<Bedroom>\n", Tool.rep(' ', 15));
                        System.out.println(bedSprites[0]);
                        System.out.printf("%sWhat do you want to do?\n", Tool.rep(' ',40));
                        menu.print();
                        System.out.printf("\n%sChoice(1-2): ", Tool.rep(' ',40));
                    });
        if (choice == 2) return;

        String gap1 = Tool.rep(' ', 15);
        String gap2 = Tool.rep(' ',40);
        for (int i = 0; i < 12; i++) {
            Tool.clearScreen();
            person.print();
            System.out.printf("\n%s<Bedroom>\n", gap1);
            System.out.println(bedSprites[i%bedSprites.length]);
            System.out.printf("%sSleeping%s\n", gap2, Tool.rep('.',i%4));
            Tool.sleep(400);
        }
        person.setDay(person.getDay()+1);
        person.setEnergy(person.getMaxEnergy());
        person.setTripOfTheDay(0);

        int intelligenceDegrade = -1;
        int muscleStrengthDegrade = -1;
        int attractivenessDegrade = -1;
        double chance = Math.random();
        if (chance <= 0.2) // intelligence degradation
            if (person.getIntelligence() >= 10) {
                intelligenceDegrade = Tool.getRandomIntegerWithRange(1, 2);
                person.setIntelligence(person.getIntelligence()-intelligenceDegrade);
            }
        chance = Math.random();
        if (chance <= 0.2) // muscleStrength degradation
            if (person.getMuscleStrength() >= 10) {
                muscleStrengthDegrade = Tool.getRandomIntegerWithRange(1, 2);
                person.setMuscleStrength(person.getMuscleStrength()-muscleStrengthDegrade);
            }
        chance = Math.random();
        if (chance <= 0.2) // attractiveness degradation
            if (person.getAttractiveness() >= 10) {
                attractivenessDegrade = Tool.getRandomIntegerWithRange(1, 2);
                person.setAttractiveness(person.getAttractiveness()-attractivenessDegrade);
            }

        Tool.clearScreen();
        person.print();
        System.out.printf("\n%s<Bedroom>\n", Tool.rep(' ', 15));
        System.out.println(bedSprites[0]);
        System.out.printf("%sWake up!\n", Tool.rep(' ',40));
		System.out.printf("\n%sIt's already Day-%d\n", Tool.rep(' ', 40), person.getDay());
		System.out.printf("%sEnergy fully restored to %d\n", Tool.rep(' ', 40), person.getEnergy());
        if (intelligenceDegrade != -1) {
            System.out.printf("\n%sYou lost %d Intelligence because " +
                                "\n%syou hit your head to the wall while sleeping\n",
                                Tool.rep(' ',40), intelligenceDegrade, Tool.rep(' ',40));
        }
        if (muscleStrengthDegrade != -1) {
            System.out.printf("\n%sYou lost %d Muscle Strength sbecause " +
                                "\n%sof Muscle Degradation\n",
                                Tool.rep(' ',40),muscleStrengthDegrade,Tool.rep(' ',40));
        }
        if (attractivenessDegrade != -1) {
            System.out.printf("\n%sYou lost %d Attractiveness because " +
                                "\n%syou fell down and hit your face\n",
                                Tool.rep(' ',40),attractivenessDegrade,Tool.rep(' ',40));
        }
		System.out.printf("\n%sPress <enter> to continue...", Tool.rep(' ',40));
		Tool.waitForEnterKeyPressed(() -> {});
    }
}
