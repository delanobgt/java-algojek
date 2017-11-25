package src.menu.gamemenu;

import src.utility.*;
import src.menu.*;
import src.menu.orderscreen.*;
import src.playerstuff.Person;
import java.util.Scanner;

public class QuitMenu {

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
        int choice = Tool.getIntegerInputWithRange(1, menuItems.length,
                        () -> {
                            Tool.clearScreen();
                            person.print();
                            System.out.println("-------Quit Menu-------");
                            menu.print();
                            System.out.print("\nChoice(1-3): ");
                        });
        if (choice == 1) {          // Save & Quit
            SaveHandler.save(person, person.getName());
            for (int i = 1; i <= 5; i++) {
                
            }
        } else if (choice == 2) {   // Quit without saving
            return false;
        } else if (choice == 3) {   // Back
            return true;
        }
        return false;
    }

}
