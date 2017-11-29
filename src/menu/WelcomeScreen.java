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
        mainMenu = new Menu(mainMenuItems, 42);
    }

    public static Person prompt() {
        do {
            int mainChoice = Tool.getIntegerInputWithRange(0, mainMenuItems.length-1,
                            () -> {
                                Tool.clearScreen();
                                displayTitle();
                                System.out.printf("\n%s<Main Menu>\n\n", Tool.rep(' ', 43));
                                mainMenu.print();
                                System.out.print("\n"+Tool.rep(' ', 42)+"Choice(0-4): ");
                            });

            if (mainChoice == 1) { // New Game
                String userName = getUserNameWithLengthRange(1, 10);
                if (userName.equals("0")) continue;
                return new Person(userName);
            } else if (mainChoice == 2) { // Load Game
                String[][] result = SaveHandler.getSaveData();
                String[] saveNames = result[0];
                String[] saveNamesAndDates = result[1];
                saveNamesAndDates[4] = "Back";
                Menu saveMenu = new Menu(saveNamesAndDates, 31);

                int saveChoice;
                do {
                    saveChoice = Tool.getIntegerInputWithRange(0, saveNamesAndDates.length-1,
                        () -> {
                            Tool.clearScreen();
                            displayTitle();
                            System.out.print("\n"+Tool.rep(' ',46)+"<Save Slots>");
                            System.out.println("\n"+Tool.rep(' ',43)+"(maximum 4 slots)\n");
                            saveMenu.print();
                            System.out.print( "\n"+Tool.rep(' ', 31)+String.format("Choice(0-%d): ", saveNamesAndDates.length-1) );
                        });
                    if (saveChoice == 0) break;
                    if (!saveNames[saveChoice-1].equals(SaveHandler.SAVE_SLOT_NOT_USED))
                        return (Person)SaveHandler.load(saveNames[saveChoice-1]);
                } while (true);
            } else if (mainChoice == 3) { // How to Play
                //TODO
            } else if (mainChoice == 4) { // Credits
                Tool.clearScreen();
                displayTitle();
                displayCredits();
                System.out.print("\n"+Tool.rep(' ',23)+"Press <enter> to go back");
                (new Scanner(System.in)).nextLine();
            } else if (mainChoice == 0) { // Quit
                System.exit(0);
            }
        } while (true);
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
            System.out.println("\n\n"+Tool.rep(' ', 10)+"** Only A-Z letters (lowercase/uppercase, without spaces) are allowed **");
            System.out.println("\n"+Tool.rep(' ', 10)+"** Type in \"0\" without quotes to go back **");
            if (SaveHandler.checkDuplicate(input)) System.out.println("\n"+Tool.rep(' ', 10)+"** Name already exist! Please use another name! **");
            System.out.print("\n\n"+Tool.rep(' ', 10)+"Enter your name(1-10 alphabets):");
            System.out.print("\n\n"+Tool.rep(' ', 10)+"Algojek - ");

            try {input = sc.nextLine();} catch (Exception ex) {isValid = false;}
            if (isValid && input.equals("0")) return input;
            if (isValid &&
                input.matches(String.format("[a-zA-Z]{%d,%d}", left, right)) &&
                !SaveHandler.checkDuplicate(input)) return input;
        } while (true);
    }

}
