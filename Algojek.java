import src.utility.*;
import src.menu.*;
import src.menu.gamemenu.*;
import src.menu.orderscreen.*;
import src.playerstuff.Person;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

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

        List<QuoteBox> quoteBoxes = new ArrayList<>();
        File quoteDir = new File("res\\quotes");
        for (File file : quoteDir.listFiles())
            quoteBoxes.add(new QuoteBox("res\\quotes\\"+file.getName()));

        do {
			Tool.clearScreen();
            WelcomeScreen.showAnimatedTitle();
            person = WelcomeScreen.prompt();
            do {
                QuoteBox quoteBox = quoteBoxes.get(Tool.getRandomIntegerWithRange(0, quoteBoxes.size()-1));
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
