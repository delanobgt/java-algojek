package src.menu;

import src.utility.*;
import src.menu.*;
import src.playerstuff.Person;
import java.util.*;
import java.io.*;

public class WelcomeScreen {

    private static Menu mainMenu;
    private static String[] mainMenuItems;

    static {
        mainMenuItems = new String[] {
            "New Game",
            "Load Game",
            "How to Play",
            "Credits",
            "Quit"
        };
        mainMenu = new Menu(mainMenuItems, 32);
    }
    public static String prompt() {
        do {
            int mainChoice = Tool.getIntegerInputWithRange(1, mainMenuItems.length,
                            () -> {
                                Tool.clearScreen();
                                displayTitle();
                                System.out.println();
                                mainMenu.print();
                                System.out.print("\n"+Tool.rep(' ', 32)+"Choice(1-5): ");
                            });

            if (mainChoice == 1) { // New Game
                return null;
            } else if (mainChoice == 2) { // Load Game
                List<List<String>> lst = SaveHandler.getSaveList();
                List<String> saveNames = lst.get(0);
                List<String> saveNamesAndDates = lst.get(1);
                saveNamesAndDates.add("Back");
                Menu saveMenu = new Menu(saveNamesAndDates, 18);

                int saveChoice = Tool.getIntegerInputWithRange(1, saveNamesAndDates.size(),
                                () -> {
                                    Tool.clearScreen();
                                    displayTitle();
                                    System.out.println("\n"+Tool.rep(' ',18)+Tool.rep('-',16)+"Save List"+Tool.rep('-',16));
                                    if (saveNames.size() == 0) System.out.println(Tool.rep(' ', 31)+"*No save data*");
                                    saveMenu.print();
                                    System.out.print( "\n"+Tool.rep(' ', 30)+String.format("Choice(1-%d): ", saveNamesAndDates.size()) );
                                });
                if (saveChoice == saveNamesAndDates.size()) continue;
                return saveNames.get(saveChoice-1);
            } else if (mainChoice == 3) { // How to Play
                //TODO
            } else if (mainChoice == 4) { // Credits
                Tool.clearScreen();
                displayTitle();
                displayCredits();
                System.out.print("\n"+Tool.rep(' ',23)+"Press <enter> to go back");
                (new Scanner(System.in)).nextLine();
            } else if (mainChoice == 5) { // Quit
                System.exit(0);
            }
        } while (true);
    }

    public static Person getPersonByName(String userName) {
        if (userName == null) {
            userName = Tool.getStringInputWithLengthRange(1, 10,
                    () -> {
                        Tool.clearScreen();
                        displayTitle();
                        System.out.print("\n"+Tool.rep(' ', 15)+"Enter your name(1-10 alphabets): ");
                    });
            return new Person(userName);
        } else {
            return (Person)SaveHandler.load(userName);
        }
    }

    public static void showAnimatedTitle() {
        List<String> lst = Tool.getStringListFromTextFile("res\\title.txt");
        for (String s : lst) {
            Tool.sleep(100);
            System.out.println(s);
        }
    }

    private static void displayTitle() {
        List<String> lst = Tool.getStringListFromTextFile("res\\title.txt");
        for (String s : lst) System.out.println(s);
    }

    private static void displayCredits() {
        List<String> lst = Tool.getStringListFromTextFile("res\\credits.txt");
        System.out.println();
        for (String s : lst) System.out.println(Tool.rep(' ',23)+s);
    }

}
