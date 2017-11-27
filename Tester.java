import src.utility.*;
import src.menu.*;
import src.menu.orderscreen.*;
import src.menu.gamemenu.*;
import src.playerstuff.Person;
import java.util.*;
import java.io.*;

public class Tester {

    public static void main(String[] args) {
		Person person = new Person("ASTAGA WOI");
        // AlgojekJobsMenu algojekJobsMenu = new AlgojekJobsMenu(person);
        // algojekJobsMenu.prompt();

        // AlgoRideOrderScreen m = new AlgoRideOrderScreen(person);
        // m.prompt();
        // AlgoSendOrderScreen m = new AlgoSendOrderScreen(person);
        // m.prompt();
        // AlgoFoodOrderScreen m = new AlgoFoodOrderScreen(person);
        // m.prompt();

        PersonalActivitiesMenu m = new PersonalActivitiesMenu(person);
        m.prompt();

        //writeToFile();
    }

    public static void writeToFile() {
        List<String> lst = Tool.getStringListFromTextFile("items.txt");

        try {
            FileWriter writer = new FileWriter("items.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (String s : lst) {
                String tmp = s.trim();
                if (1 <= tmp.length() && tmp.length() <= 10) {
                    bufferedWriter.write(tmp);
                    bufferedWriter.newLine();
                }
            }

            // for (int i = 0; i < lst.size(); i++) {
            //     String tmp = lst.get(i).trim();
            //     if (1 <= tmp.length() && tmp.length() <= 10) {
            //         if (i <= 49) bufferedWriter.write("M-"+tmp);
            //         else bufferedWriter.write("F-"+tmp);
            //         bufferedWriter.newLine();
            //     }
            // }

            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
