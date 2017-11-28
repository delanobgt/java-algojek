package src.menu.gamemenu;

import src.utility.*;
import src.menu.*;
import src.menu.orderscreen.*;
import src.playerstuff.Person;
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
        this.menu = new Menu(menuItems);
    }

    public boolean prompt() {
        do {
            int choice = Tool.getIntegerInputWithRange(1, menuItems.length,
                            () -> {
                                Tool.clearScreen();
                                person.print();
                                System.out.println("-------Quit Menu-------");
                                menu.print();
                                System.out.print("\nChoice(1-3): ");
                            });
            if (choice == 1) {          // Save & Quit
                do {
                    String[][] result = SaveHandler.getSaveData();
                    String[] saveNames = result[0];
                    String[] saveNamesAndDates = result[1];
                    saveNamesAndDates[5] = "Back";
                    Menu saveMenu = new Menu(saveNamesAndDates, 18);

                    int saveChoice;
                    saveChoice = Tool.getIntegerInputWithRange(1, saveNamesAndDates.length,
                        () -> {
                            Tool.clearScreen();
                            person.print();
                            System.out.println("\n"+Tool.rep(' ',18)+"Which Save Slot to use?");
                            System.out.println("\n"+Tool.rep(' ',18)+Tool.rep('-',16)+"Save List"+Tool.rep('-',16));
                            saveMenu.print();
                            System.out.print( "\n"+Tool.rep(' ', 30)+String.format("Choice(1-%d): ", saveNamesAndDates.length) );
                        });
                    if (saveChoice == saveNamesAndDates.length) break; //use pressed back button
                    if (saveNames[saveChoice-1].equals(SaveHandler.SAVE_SLOT_NOT_USED)) { //slot not used
                        System.out.print("Are you sure you want to save here? (y/n): ");
                        String resp;
                        do {resp = sc.nextLine();} while (resp.length() != 1 && "YyNn".indexOf(resp) == -1);
                        if (resp.equalsIgnoreCase("n")) continue;
                        SaveHandler.save(person, String.format("save%d_%s.algojek", saveChoice-1, person.getName()));
                    } else { //slot used, ask for overwrite
                        System.out.print("Do you want to overwrite? (y/n): ");
                        String resp;
                        do {resp = sc.nextLine();} while (resp.length() != 1 && "YyNn".indexOf(resp) == -1);
                        if (resp.equalsIgnoreCase("n")) continue;
                        try {
                            File file = new File( String.format("saved\\save%d_%s.algojek", saveChoice-1, saveNames[saveChoice-1]) );
                            file.delete();
                            SaveHandler.save(person, String.format("save%d_%s.algojek", saveChoice-1, person.getName()));
                        } catch (Exception ex) {System.out.println(ex);}
                    }
                    Tool.clearScreen();
                    person.print();
                    new Menu(SaveHandler.getSaveData()[1], 18).print();
                    System.out.println("\n"+Tool.rep(' ',18)+Tool.rep('-',16)+"Save List"+Tool.rep('-',16));
                    System.out.printf("\n%sPress <enter> to Continue..", Tool.rep(' ',39));
                    Tool.waitForEnterKeyPressed(() -> {});
                    return false;
                } while (true);
            } else if (choice == 2) {   // Quit without saving
                return false;
            } else if (choice == 3) {   // Back
                return true;
            }
        } while(true);
    }

}
