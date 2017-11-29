import src.utility.*;
import src.menu.*;
import src.menu.gamemenu.*;
import src.menu.orderscreen.*;
import src.playerstuff.Person;
import java.util.Scanner;

public class Algojek {
    static Person person = null;

    public static void main(String[] args) {
        String[] mainJobs = new String[] {
            "Go to Bedroom",
            "Algojek Jobs",
            "Personal Activities",
            "Motorcycle Services",
            "Quit.."
        };
        Menu mainJobMenu = new Menu(mainJobs, 41);
        QuoteBox[] quoteBoxes = {
            new QuoteBox("res\\quotes\\1.txt"),
            new QuoteBox("res\\quotes\\2.txt"),
            new QuoteBox("res\\quotes\\3.txt"),
            new QuoteBox("res\\quotes\\4.txt"),
            new QuoteBox("res\\quotes\\5.txt"),
            new QuoteBox("res\\quotes\\6.txt"),
            new QuoteBox("res\\quotes\\7.txt"),
            new QuoteBox("res\\quotes\\8.txt"),
            new QuoteBox("res\\quotes\\9.txt"),
            new QuoteBox("res\\quotes\\10.txt")
        };

        do {
			Tool.clearScreen();
            WelcomeScreen.showAnimatedTitle();
            person = WelcomeScreen.prompt();
            do {
                QuoteBox quoteBox = quoteBoxes[Tool.getRandomIntegerWithRange(0, quoteBoxes.length-1)];
                int mainChoice = Tool.getIntegerInputWithRange(0, mainJobs.length-1,
                    () -> {
                        Tool.clearScreen();
                        person.print();
                        System.out.print("\n"+quoteBox);
                        System.out.printf("\n%s<Main Activities>", Tool.rep(' ', 44));
                        System.out.printf("\n\n%sWhat do you want to do?\n\n", Tool.rep(' ', 41));
                        mainJobMenu.print();
                        System.out.print(String.format("\n%sChoice(0-%d): ", Tool.rep(' ', 41), mainJobs.length-1));
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
                } else if (mainChoice == 0) {   // Quit..
                    QuitMenu quitMenu = new QuitMenu(person);
                    if (!quitMenu.prompt()) break;
                }
            } while (true);
        } while (true);
    }

}
