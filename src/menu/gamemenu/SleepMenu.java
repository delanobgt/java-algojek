package src.menu.gamemenu;

import src.utility.*;
import src.playerstuff.Person;
import java.util.Scanner;

public class SleepMenu {
    private Person person;
    private Scanner sc;

    public SleepMenu(Person person) {
        this.person = person;
        this.sc = new Scanner(System.in);
    }

    public void prompt() {
        int barWidth = 12;
        Tool.showProgressBar(barWidth, 125,
            () -> {
                Tool.clearScreen();
                person.print();
                System.out.print("\nSleeping Progress:\n");
            });

        person.setDay(person.getDay()+1);
        person.setEnergy(person.getMaxEnergy());

        int intelligenceDegrade = -1;
        int muscleStrengthDegrade = -1;
        int attractivenessDegrade = -1;

        double chance = Math.random();
        if (chance <= 0.35) // intelligence degradation
            if (person.getIntelligence() >= 10) {
                intelligenceDegrade = (int)Math.ceil(Math.random()*5);
                person.setIntelligence(person.getIntelligence()-intelligenceDegrade);
            }
        chance = Math.random();
        if (chance <= 0.35) // muscleStrength degradation
            if (person.getMuscleStrength() >= 10) {
                muscleStrengthDegrade = (int)Math.ceil(Math.random()*5);
                person.setMuscleStrength(person.getMuscleStrength()-muscleStrengthDegrade);
            }
        chance = Math.random();
        if (chance <= 0.35) // attractiveness degradation
            if (person.getAttractiveness() >= 10) {
                attractivenessDegrade = (int)Math.ceil(Math.random()*5);
                person.setAttractiveness(person.getAttractiveness()-attractivenessDegrade);
            }

        Tool.clearScreen();
        person.print();
        System.out.print("\nSleeping Progress:\n");
        System.out.printf("|%s>| %d%%\n", Tool.rep("==", barWidth-1), 100);
		System.out.println("\n-------Status-------");
		System.out.printf("- It's already Day-%d\n", person.getDay());
		System.out.printf("- Energy fully restored to %d\n", person.getEnergy());
        if (intelligenceDegrade != -1) {
            System.out.printf("- Intelligence(-%d) -> %d\n", intelligenceDegrade, person.getIntelligence());
            System.out.println("\tYou lost some of your Intelligence because\n" +
                                "\tyou hit your head to the wall while sleeping\n" +
                                "\tYou should learn harder!!");
        }
        if (muscleStrengthDegrade != -1) {
            System.out.printf("- Muscle Strength(-%d) -> %d\n", muscleStrengthDegrade, person.getMuscleStrength());
            System.out.println("\tYou lost some of your Muscle Strength\n" +
                                "\tbecause of Muscle Degradation\n"+
                                "\tYou should workout harder!!");
        }
        if (attractivenessDegrade != -1) {
            System.out.printf("- Attractiveness(-%d) -> %d\n", attractivenessDegrade, person.getAttractiveness());
            System.out.println("\tYou lost some of your Attractiveness\n" +
                                "\tbecause you fell down and hit your face\n"+
                                "\tYou should be more careful!!");
        }
		System.out.print("\nPress <enter> to Continue...");
		Tool.waitForEnterKeyPressed(() -> {});
    }
}
