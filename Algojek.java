import src.utility.*;
import src.menu.*;
import src.menu.gamemenu.*;
import src.menu.orderscreen.*;
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
            do {
                int mainChoice = Tool.getIntegerInputWithRange(1, mainJobs.length,
                        () -> {
                            Tool.clearScreen();
                            person.print();
                            System.out.println("\n----Main Activities----");
                            mainJobMenu.print();
                            System.out.print(String.format("\nChoice(1-%d): ", mainJobs.length));
                        });

                if (mainChoice == 1) {  // Sleep
                    SleepMenu sleepMenu = new SleepMenu(person);
                    sleepMenu.prompt();
                } else if (mainChoice == 2) {   // Algojek Jobs
                    AlgojekJobsMenu algojekJobsMenu = new AlgojekJobsMenu(person);
                    algojekJobsMenu.prompt();
                } else if (mainChoice == 3) {   // Personal Activities
                    PersonalActivitiesMenu personalActivitiesMenu = new PersonalActivitiesMenu(person);
                    personalActivitiesMenu.prompt();
                } else if (mainChoice == 4) {   // Motorcycle Services
                    MotorcycleServicesMenu motorcycleServicesMenu = new MotorcycleServicesMenu(person);
                    motorcycleServicesMenu.prompt();
                } else if (mainChoice == 5) {   // Check Achievements
                    System.out.print("Not yet done..");
                    Tool.waitForEnterKeyPressed(() -> {});
                } else if (mainChoice == 6) {   // Quit..
                    QuitMenu quitMenu = new QuitMenu(person);
                    if (!quitMenu.prompt()) break;
                }
            } while (true);
        } while (true);
    }

}
