package src.menu;

import src.utility.*;
import src.menu.*;
import src.playerstuff.Person;
import java.util.*;
import java.io.*;

public class WelcomeScreen {

    private static Menu mainMenu;
    private static String[] mainMenuItems;
    private static String miniTitle =
        "\n||----------------------------------------------------------------------------------------||";


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
                showProgressBar("NEW_GAME");
                return new Person(userName);
            } else if (mainChoice == 2) { // Load Game
                do {
                    String[][] result = SaveHandler.getSaveData();
                    String[] saveNames = result[0];
                    String[] saveNamesAndDates = result[1];
                    saveNamesAndDates[4] = "Back";
                    Menu saveMenu = new Menu(saveNamesAndDates, 31);
                    int saveChoice = Tool.getIntegerInputWithRange(0, saveNamesAndDates.length-1,
                        () -> {
                            Tool.clearScreen();
                            System.out.print(miniTitle);
                            System.out.print(Tool.rep('\n',7)+Tool.rep(' ',46)+"<Save Slots>");
                            System.out.println("\n"+Tool.rep(' ',43)+"(maximum 4 slots)\n");
                            saveMenu.print();
                            System.out.print( "\n"+Tool.rep(' ', 31)+String.format("Choice(0-%d): ", saveNamesAndDates.length-1) );
                        });
                    if (saveChoice == 0) break;
                    if (!saveNames[saveChoice-1].equals(SaveHandler.SAVE_SLOT_NOT_USED)) {
                        do {
                            final int tmp1 = saveChoice;
                            int loadChoice = Tool.getIntegerInputWithRange(0, 2,
                                () -> {
                                    Tool.clearScreen();
                                    System.out.print(miniTitle);
                                    System.out.print(Tool.rep('\n',7)+Tool.rep(' ',46)+"<Save Slots>");
                                    System.out.println("\n"+Tool.rep(' ',43)+"(maximum 4 slots)\n");
                                    saveMenu.print();
                                    System.out.println("\n"+Tool.rep(' ', 31)+String.format("Save Slot %d selected", tmp1));
                                    System.out.println(Tool.rep(' ',31)+"What do you want to do with the Save Slot?");
                                    Menu tmpMenu = new Menu(new String[] {
                                        "Load",
                                        "Delete",
                                        "Cancel"
                                    }, 31);
                                    tmpMenu.print();
                                    System.out.printf("\n%sChoice(0-2): ", Tool.rep(' ',31));
                                });
                            if (loadChoice == 0) { //cancel
                                break;
                            } else if (loadChoice == 1) { //load
                                showProgressBar("LOAD_GAME");
                                return (Person)SaveHandler.load(saveNames[saveChoice-1]);
                            } else if (loadChoice == 2) { //delete
                                String deleteChoice = getYesOrNoDeleteInput(saveMenu, saveNamesAndDates, saveChoice);
                                if (deleteChoice.equalsIgnoreCase("n")) continue;
                                try {
                                    File file = new File("saved\\"+saveNames[saveChoice-1]);
                                    if (!file.delete())
                                        file.renameTo(new File("saved\\deleted_"+saveNames[saveChoice-1]));
                                } catch (Exception ex) {System.out.println(ex);}
                                break;
                            }
                        } while (true);
                    }
                } while (true);
            } else if (mainChoice == 3) { // How to Play
                Tool.clearScreen();
                System.out.print(miniTitle);
                System.out.print(Tool.rep('\n', 3)+Tool.rep(' ',40)+"<How to Play>\n\n");
                displayHowToPlay();
                System.out.print("\n"+Tool.rep(' ',34)+"Press <enter> to go back");
                Tool.waitForEnterKeyPressed(() -> {});
            } else if (mainChoice == 4) { // Credits
                Tool.clearScreen();
                System.out.print(miniTitle);
                System.out.print(Tool.rep('\n', 7)+Tool.rep(' ',40)+"<Credits>\n\n");
                displayCredits();
                System.out.print("\n"+Tool.rep(' ',33)+"Press <enter> to go back");
                Tool.waitForEnterKeyPressed(() -> {});
            } else if (mainChoice == 0) { // Quit
                Tool.clearScreen();
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

    private static void displayHowToPlay() {
        String content = Tool.getStringFromTextFile("res\\howToPlay.txt", 2);
        System.out.print(content);
    }

    private static void displayCredits() {
        String creditsContent = Tool.getStringFromTextFile("res\\credits.txt", 2);
        System.out.print(creditsContent);
    }

    private static String getYesOrNoDeleteInput(Menu saveMenu, String[] saveNamesAndDates, int saveChoice) {
        final int tmp1 = saveChoice;
        do {
            Tool.clearScreen();
            System.out.print(miniTitle);
            System.out.print(Tool.rep('\n',7)+Tool.rep(' ',46)+"<Save Slots>");
            System.out.println("\n"+Tool.rep(' ',43)+"(maximum 4 slots)\n");
            saveMenu.print();
            System.out.println("\n"+Tool.rep(' ', 31)+String.format("Save Slot %d will be deleted", tmp1));
            System.out.print(Tool.rep(' ',31)+"Are you sure? (y/n): ");

            Scanner sc = new Scanner(System.in);
            boolean isValid = true;
            String input = null;
            try {input = sc.nextLine();} catch (Exception ex) {isValid = false;}
            if (isValid && input.length() == 1 && "YyNn".indexOf(input) != -1) return input;
        } while (true);
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


    private static void showProgressBar(String flag) {
        if (flag.equals("NEW_GAME")) {
            int tabSize = 24;
            String tab = Tool.rep(' ',tabSize);
            int length = 20;
            int delay = 350;
            for (int i = 0; i <= length; i++) {
                Tool.clearScreen();
                displayTitle();
                System.out.println('\n');
                if (i < 7)
                    System.out.printf("%sCreating your character%s\n", tab, Tool.rep('.', i%3));
                else if (i < 14)
                    System.out.printf("%sSigning you into Algojek Company%s\n", tab, Tool.rep('.', i%3));
                else if (i < 20)
                    System.out.printf("%sPurchasing your new motorcycle%s\n", tab, Tool.rep('.', i%3));
                else
                    System.out.printf("%sDone\n", tab);
                System.out.printf("\n\n%s[%-"+(2*length-1)+"s] %d%%\n", tab, Tool.rep("==",i-1)+">", (int)(i*100.0/length) );
                Tool.sleep(delay);
            }
        } else if (flag.equals("LOAD_GAME")) {
            int tabSize = 24;
            String tab = Tool.rep(' ',tabSize);
            int length = 20;
            int delay = 350;
            for (int i = 0; i <= length; i++) {
                Tool.clearScreen();
                System.out.println(miniTitle);
                System.out.println("\n\n\n\n\n");
                if (i < 7)
                    System.out.printf("%sLoading your progress%s\n", tab, Tool.rep('.', i%3));
                else if (i < 14)
                    System.out.printf("%sConfiguring character%s\n", tab, Tool.rep('.', i%3));
                else if (i < 20)
                    System.out.printf("%sPreparing UI%s\n", tab, Tool.rep('.', i%3));
                else
                    System.out.printf("%sDone\n", tab);
                System.out.printf("\n\n%s[%-"+(2*length-1)+"s] %d%%\n", tab, Tool.rep("==",i-1)+">", (int)(i*100.0/length) );
                Tool.sleep(delay);
            }
        }
    }
}
