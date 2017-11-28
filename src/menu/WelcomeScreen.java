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
            "New Game   ",
            "Load Game  ",
            "How to Play",
            "Credits    ",
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
                String[][] result = SaveHandler.getSaveData();
                String[] saveNames = result[0];
                String[] saveNamesAndDates = result[1];
                saveNamesAndDates[5] = "Back";
                Menu saveMenu = new Menu(saveNamesAndDates, 18);

                int saveChoice;
                do {
                    saveChoice = Tool.getIntegerInputWithRange(1, saveNamesAndDates.length,
                        () -> {
                            Tool.clearScreen();
                            displayTitle();
                            System.out.println("\n"+Tool.rep(' ',18)+Tool.rep('-',16)+"Save List"+Tool.rep('-',16));
                            saveMenu.print();
                            System.out.print( "\n"+Tool.rep(' ', 30)+String.format("Choice(1-%d): ", saveNamesAndDates.length) );
                        });
                    if (saveChoice == saveNamesAndDates.length) break;
                    if (!saveNames[saveChoice-1].equals(SaveHandler.SAVE_SLOT_NOT_USED)) return saveNames[saveChoice-1];
                } while (true);
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
            userName = getUserNameWithLengthRange(1, 10);
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

    private static String getUserNameWithLengthRange(int left, int right) {
        String input = "";
        do {
            Scanner sc = new Scanner(System.in);
            boolean isValid = true;

            Tool.clearScreen();
            WelcomeScreen.displayTitle();
            if (SaveHandler.checkDuplicate(input)) System.out.println("\n"+Tool.rep(' ', 15)+"Name already exist! Please use another name");
            else System.out.println("\n"+Tool.rep(' ', 15)+"Only A-Z letters (lowercase/uppercase) are allowed");
            System.out.print("\n"+Tool.rep(' ', 15)+"Enter your name(1-10 alphabets): ");

            try {input = sc.nextLine();} catch (Exception ex) {isValid = false;}
            if (isValid &&
                input.matches(String.format("[a-zA-Z]{%d,%d}", left, right)) &&
                !SaveHandler.checkDuplicate(input)) return input;
        } while (true);
    }

}
