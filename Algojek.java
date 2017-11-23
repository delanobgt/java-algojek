import src.utility.*;
import src.menu.*;
import src.menu.gamemenu.*;
import src.playerstuff.Person;
import java.util.Scanner;

public class Algojek {
    private static Person person;
    private static String[] mainJobs;
    private static Menu mainJobMenu;

    static {
        mainJobs = new String[] {
            "Sleep (Restore Full Energy)",
            "Algojek Jobs",
            "Personal Activities",
            "Motorcycle Services",
            "Check Achievements",
            "Quit.."
        };
        mainJobMenu = new Menu(mainJobs);
    }

    public static void main(String[] args) {

        do {
			Tool.clearScreen();
            WelcomeScreen.showAnimatedTitle();
            String userName = WelcomeScreen.prompt();
            person = WelcomeScreen.getPersonByName(userName);

            int mainChoice = Tool.getIntegerInputWithRange(1, mainJobs.length,
                    () -> {
                        Tool.clearScreen();
                        person.print();
                        System.out.println("\n----Main Activities----");
                        mainJobMenu.print();
                        System.out.print(String.format("\nChoice(1-%d): ", mainJobs.length));
                    });

            if (mainChoice == 1) {  // Sleep

            } else if (mainChoice == 2) {   // Algojek Jobs

            } else if (mainChoice == 3) {   // Personal Activities

            } else if (mainChoice == 4) {   // Motorcycle Services

            } else if (mainChoice == 5) {   // Check Achievements

            } else if (mainChoice == 6) {   // Quit..

            }
        } while (true);
    }

}
