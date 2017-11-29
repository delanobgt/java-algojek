package src.menu.gamemenu;

import src.utility.*;
import src.menu.*;
import src.menu.orderscreen.*;
import src.playerstuff.Person;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;

public class QuitMenu {

    private Scanner sc = new Scanner(System.in);
    private Person person;
    private String[] menuItems = {
        "Save & Quit",
        "Quit without saving",
        "Back"
    };
    private Menu menu;

    public QuitMenu(Person person) {
        this.person = person;
        this.menu = new Menu(menuItems, 40);
    }

    public boolean prompt() {
        do {
            int choice = Tool.getIntegerInputWithRange(0, menuItems.length-1,
                () -> {
                    Tool.clearScreen();
                    person.print();
                    System.out.printf("\n%s<Quit Options>\n\n", Tool.rep(' ',45));
                    System.out.printf("%sHow do you want to quit?\n", Tool.rep(' ',40));
                    menu.print();
                    System.out.printf("\n%sChoice(0-2): ", Tool.rep(' ',40));
                });
            if (choice == 1) {          // Save & Quit
                do {
                    String[][] result = SaveHandler.getSaveData();
                    String[] saveNames = result[0];
                    String[] saveNamesAndDates = result[1];
                    saveNamesAndDates[4] = "Back";
                    Menu saveMenu = new Menu(saveNamesAndDates, 31);

                    int saveChoice;
                    saveChoice = Tool.getIntegerInputWithRange(0, saveNamesAndDates.length-1,
                        () -> {
                            Tool.clearScreen();
                            person.print();
                            System.out.println("\n"+Tool.rep(' ',41)+"Which \"Save Slot\" to use?\n");
                            System.out.print("\n"+Tool.rep(' ',46)+"<Save Slots>");
                            System.out.println("\n"+Tool.rep(' ',43)+"(maximum 4 slots)\n");
                            saveMenu.print();
                            System.out.print( "\n"+Tool.rep(' ', 31)+String.format("Choice(0-%d): ", saveNamesAndDates.length-1) );
                        });
                    if (saveChoice == 0) break; //use pressed back button
                    if (saveNames[saveChoice-1].equals(SaveHandler.SAVE_SLOT_NOT_USED)) { //slot not used
                        String resp = getYesOrNoSaveLoadInput(saveMenu, saveNamesAndDates, saveChoice, "NEW_SAVE");
                        if (resp.equalsIgnoreCase("n")) continue;
                        SaveHandler.save(person, String.format("save%d_%s.algojek", saveChoice-1, person.getName()));
                    } else {        //slot used, ask for overwrite
                        String resp = getYesOrNoSaveLoadInput(saveMenu, saveNamesAndDates, saveChoice, "OVERWRITE_SAVE");
                        if (resp.equalsIgnoreCase("n")) continue;
                        try {
                            File file = new File("saved\\"+saveNames[saveChoice-1]);
                            if (!file.delete())
                                file.renameTo(new File("saved\\deleted_"+saveNames[saveChoice-1]));
                            SaveHandler.save(person, String.format("save%d_%s.algojek", saveChoice-1, person.getName()));
                        } catch (Exception ex) {System.out.println(ex);}
                    }

                    Tool.clearScreen();
                    person.print();
                    System.out.println("\n"+Tool.rep(' ',46)+"Progress saved!\n");
                    System.out.print("\n"+Tool.rep(' ',46)+"<Save Slots>");
                    System.out.println("\n"+Tool.rep(' ',43)+"(maximum 4 slots)\n");
                    Menu tmpMenu = new Menu(Arrays.copyOfRange(SaveHandler.getSaveData()[1],0,4), 31);
                    tmpMenu.setHasBackButton(false);
                    tmpMenu.print();
                    System.out.printf("\n%sPress <enter> to Continue..", Tool.rep(' ',39));
                    Tool.waitForEnterKeyPressed(() -> {});
                    return false;
                } while (true);
            } else if (choice == 2) {   // Quit without saving
                return false;
            } else if (choice == 0) {   // Back
                return true;
            }
        } while(true);
    }

    private String getYesOrNoSaveLoadInput(Menu saveMenu, String[] saveNamesAndDates, int saveChoice, String flag) {
        do {
            Tool.clearScreen();
            person.print();
            System.out.println("\n"+Tool.rep(' ',41)+"Which \"Save Slot\" to use?\n");
            System.out.print("\n"+Tool.rep(' ',46)+"<Save Slots>");
            System.out.println("\n"+Tool.rep(' ',43)+"(maximum 4 slots)\n");
            saveMenu.print();
            System.out.print("\n"+Tool.rep(' ', 31)+String.format("Choice(0-%d): %d\n\n", saveNamesAndDates.length-1, saveChoice));
            if (flag.equals("NEW_SAVE"))
                System.out.print(Tool.rep(' ',31)+"Your progress will be saved to slot "+saveChoice+"\n");
            else if (flag.equals("OVERWRITE_SAVE"))
                System.out.print(Tool.rep(' ',31)+String.format("Slot %d will be overwritten",saveChoice)+"\n");
            System.out.print(Tool.rep(' ',31)+"Are you sure? (y/n): ");

            Scanner sc = new Scanner(System.in);
            boolean isValid = true;
            String input = null;
            try {input = sc.nextLine();} catch (Exception ex) {isValid = false;}
            if (isValid && input.length() == 1 && "YyNn".indexOf(input) != -1) return input;
        } while (true);
    }
}
