package src.menu.orderscreen;

import src.utility.*;
import src.menu.*;
import src.playerstuff.*;
import java.util.*;

public class AlgoRideOrderScreen {
    private String customerDetails = "";
    private Person person;
    private Motorcycle motorcycle;
    private Thread enterKeyThread;
    private volatile boolean isPressed = false; //is enter key pressed?
    private Scanner sc;
    private List<String> nameList;
    private List<String> streetList;

    public AlgoRideOrderScreen(Person person) {
        this.nameList = Tool.getStringListFromTextFile("res\\names.txt");
        this.streetList = Tool.getStringListFromTextFile("res\\streets.txt");
        this.sc = new Scanner(System.in);
        this.person = person;
        this.motorcycle = person.getMotorcycle();
        this.enterKeyThread= new Thread(() -> {
            Tool.waitForEnterKeyPressed(() -> {isPressed = true;});
        });
    }

    private void orderMethod() { //finding customer method
        String loadingSprite = Tool.getStringFromTextFile("res\\algojobs\\ride.txt", 33);
        //search for customer infinitely
        while (true) {
            //finding customer animation
            String tab = Tool.rep(' ', 45);
            int duration = Tool.getRandomIntegerWithRange(40, 80);
            for (int i = 0; i < duration; i++) {
                Tool.clearScreen();
                person.print();
                System.out.printf("\n%s<Algojek App Screen>\n", Tool.rep(' ', 43));
                System.out.printf(loadingSprite);
                System.out.printf("\n%sFinding customer\n", tab);

                int tmp = i%22;
                if (tmp <= 10)
                    System.out.printf("%s[%s>>>%s]", tab, Tool.rep(' ',tmp), Tool.rep(' ',11-tmp));
                else {
                    tmp -= 11;
                    System.out.printf("%s[%s<<<%s]", tab, Tool.rep(' ',11-tmp), Tool.rep(' ',tmp));
                }

                System.out.printf("\n\n%sPress <enter> to cancel finding customer", Tool.rep(' ',32));
                Tool.sleep(100);
                if (isPressed) {
                    Tool.clearScreen();
                    person.print();
                    System.out.printf("\n%s<Algojek App Screen>\n", Tool.rep(' ', 43));
                    System.out.printf(loadingSprite);
                    System.out.printf("\n%sFinding customer cancelled\n", Tool.rep(' ', 40));
                    System.out.printf("\n%sPress <enter> to continue..", Tool.rep(' ',40));
                    Tool.waitForEnterKeyPressed(() -> {});
                    return;
                }
            }

            //customer state laoding
            String customer = nameList.get((int)(nameList.size()*Math.random()));
            String gender = customer.substring(0, 1);
            String name = customer.substring(2);
            int originIdx = (int)(streetList.size()*Math.random());
            String origin = streetList.get(originIdx);
            int destIdx = ( originIdx+(int)(Math.random()*streetList.size()) ) % streetList.size();
            String dest = streetList.get(destIdx);
            double rate = Math.random();
            String distance = String.format("%.1f", (8*rate));
            int money = ((int)(31*rate) + 5)*1000;
            int energy = ((int)(8*rate) + 3);
            int fuel = ((int)(31*rate) + 10);

            //show customer, ask for picking or not
            for (int i = 8; i >= 1; i--) {
                Tool.clearScreen();
                person.print();
                System.out.printf("\n%s<Algojek App Screen>\n", Tool.rep(' ', 43));
                System.out.printf("\n%s Customer found!\n", tab);

                customerDetails = String.format("\n%s%-14s | %-11s | %-6s | %-4s | %s \n", Tool.rep(' ',12),
                                        "Name", "Job Fare", "Energy", "Fuel", "Trip Path") +
                                    Tool.rep(' ',12)+Tool.rep('-', 85) + "\n" +
                                    String.format("%s%-14s | +Rp. %,-6d | %6s | %4s | %s\n", Tool.rep(' ',12),
                                        name+" ("+gender+")", money, "-"+energy, "-"+fuel+"%",
                                        origin+" -> "+dest+" ("+distance+"km)");
                System.out.print(customerDetails);
                if (!isJobDoable(energy, fuel)) {
                    System.out.printf("\n%s                    ** WARNING!! **", Tool.rep(' ',25));
                    System.out.printf("\n%sNot enough energy/fuel or Motorcycle is not healthy enough", Tool.rep(' ',25));
                    System.out.printf("\n%s                    Job becomes risky", Tool.rep(' ',25));
                    System.out.printf("\n%s   You may faint or experience motorcycle breakdown\n", Tool.rep(' ',25));
                }
                System.out.printf("\n%sPress <enter> to ACCEPT (%d)", Tool.rep(' ',40), i);
                Tool.sleep(1000);
                if (isPressed) {
                    doJob(money, energy, fuel, name, gender, distance, origin, dest);
                    return;
                }
            }
        }
    }

    private boolean isJobDoable(int energy, int fuel) {
        return person.getEnergy() >= energy &&
                motorcycle.getFuel() >= fuel &&
                motorcycle.isMotorcycleHealthy();
    }

    private void doJob(int money, int energy, int fuel, String name, String gender, String distance, String origin, String dest) {
        String progressBar = showJobAnimation(isJobDoable(energy, fuel) ? 34 : Tool.getRandomIntegerWithRange(10, 20));
        String tab = Tool.rep(' ',20);

        if (person.getEnergy() < energy) {  // not enough energy
            double customerRating = Tool.getRandomIntegerWithRange(1, 2);
            double currentRating = ((person.getRating()*person.getTotalTrip())+customerRating)/(person.getTotalTrip()+1);
            person.setRating(currentRating);
            person.setEnergy(0);

            printStatus(progressBar);
            System.out.print("\n"+tab+"Oh no!!\n");
            System.out.print(tab+"You fainted because you have too little energy for the job!\n");
            System.out.print(tab+"Your customer is so pissed off\n");
            System.out.printf(tab+"- Energy -> 0\n");
            System.out.printf(tab+"- Driver Rating from Customer: %.1f\n", customerRating);
            System.out.printf(tab+"- Current Driver Rating: %.1f\n", person.getRating());
        } else if (motorcycle.getFuel() < fuel) { // not enough fuel
            double customerRating = Tool.getRandomIntegerWithRange(1, 2);
            double currentRating = ((person.getRating()*person.getTotalTrip())+customerRating)/(person.getTotalTrip()+1);
            person.setRating(currentRating);
            motorcycle.setFuel(0);

            printStatus(progressBar);
            System.out.print("\n"+tab+"Oh no!!\n");
            System.out.print(tab+"Your motorcycle can't go on anymore because it ran out of fuel!\n");
            System.out.print(tab+"Your customer is so pissed off\n");
            System.out.printf(tab+"- Fuel -> 0\n");
            System.out.printf(tab+"- Driver Rating from Customer: %.1f\n", customerRating);
            System.out.printf(tab+"- Current Driver Rating: %.1f\n", person.getRating());
        } else if (!motorcycle.isMotorcycleHealthy()) { //motorcycle poor health
            double customerRating = Tool.getRandomIntegerWithRange(1, 2);
            double currentRating = ((person.getRating()*person.getTotalTrip())+customerRating)/(person.getTotalTrip()+1);
            person.setRating(currentRating);
            motorcycle.setAllStateToZero();

            printStatus(progressBar);
            System.out.print("\n"+tab+"Oh no!!\n");
            System.out.print(tab+"Your motorcycle broke down because it's engine/oil/suspension/battery is not healthy!\n");
            System.out.print(tab+"Your customer is so pissed off\n");
            System.out.printf(tab+"You got low Driver Rating and a broken motocycle (all state becomes 0)\n");
            System.out.printf(tab+"You should fix it at the Motorcycle Services Menu\n");
            System.out.printf(tab+"- Driver Rating from Customer: %.1f\n", customerRating);
            System.out.printf(tab+"- Current Driver Rating: %.1f\n", person.getRating());
        } else { //normal job flow
            //set some value
            int tip = 0, bonus = 0, minFuel = 0, minEnergy = 0;
            if (person.getAttractiveness()*Math.random() >= 35.0)
                tip = (int)(person.getAttractiveness()*money/300.0);
            if (person.getIntelligence()*Math.random() >= 35.0)
                minFuel = (int)(person.getIntelligence()*fuel/300.0);
            if (person.getMuscleStrength()*Math.random() >= 35.0)
                minEnergy = (int)(person.getMuscleStrength()*energy/300.0);

            motorcycle.decreaseStateALittle();
            person.setMoney(person.getMoney()+money+bonus+tip);
            person.setEnergy(person.getEnergy()-energy+minEnergy);
            motorcycle.setFuel(motorcycle.getFuel()-fuel+minFuel);
            int customerRating = Tool.getRandomIntegerWithRange(3, 5);
            double currentRating = ((person.getRating()*person.getTotalTrip())+customerRating)/(person.getTotalTrip()+1);
            person.setRating(currentRating);
            person.setTripOfTheDay(person.getTripOfTheDay()+1);
            person.setTotalTrip(person.getTotalTrip()+1);

            if (person.getTripOfTheDay() == 5)
                bonus = 200_000;

            String tab1 = Tool.rep(' ', 20);
            printStatus(progressBar);
            System.out.println("\n"+tab1+"Job done..");
            System.out.println(tab1+"- Motorcycle State decreased a little");
            System.out.printf(tab1+"- Money  %-13s -> Rp. %,d %s %s\n",
                String.format("(+Rp. %,d)", money+bonus+tip),
                person.getMoney(),
                bonus==0?"":String.format("(You get Rp. %,d from 5-trip Bonus!)", bonus),
                tip==0?"":String.format("\n%s    (Your customer gives you Rp. %,d as a Tip, because you of your Attractiveness!)", tab1, tip));
            System.out.printf(tab1+"- Energy %-13s -> %d %s\n",
                String.format("(-%d)", energy-minEnergy),
                person.getEnergy(),
                minEnergy==0?"":String.format("(Because of your Muscle Strength, your have used %d less Energy!)", minEnergy));
            System.out.printf(tab1+"- Fuel   %-13s -> %d%% %s\n",
                String.format("(-%d%%)", fuel-minFuel),
                motorcycle.getFuel(),
                minFuel==0?"":String.format("(You have used %d%% less Fuel, because of your Intelligence in choosing short path)", minFuel));
            System.out.printf(tab1+"- Trip of the Day (+1) -> %d\n", person.getTotalTrip());
            System.out.printf(tab1+"- Driver Rating from Customer : %d (%s )\n", customerRating, Tool.rep(" *", customerRating)+Tool.rep(" -", 5-customerRating));
        }
        System.out.print("\n"+Tool.rep(' ',40)+"Press <enter> to continue..");
        Tool.waitForEnterKeyPressed(() -> {});
    }

    private String showJobAnimation(int stop) {
        List<String> lst = Tool.getStringListFromTextFile("res\\algojobs\\jobOnProgress.txt");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 35; i++) {
            Tool.clearScreen();
            person.print();
            System.out.printf("\n%s<Algojek App Screen>\n", Tool.rep(' ', 43));
            System.out.printf("\n%sJob on progress..\n", Tool.rep(' ', 45));
            System.out.print(customerDetails);
            for (int j = 0; j < lst.size(); j++) {
                if (j == lst.size()-1) {
                    String tmp = String.format("%s%s%s%s\n", Tool.rep(' ',20), Tool.rep('_',i), lst.get(j), Tool.rep('_',35-i));
                    System.out.print(tmp);
                    if (i == stop) sb.append(tmp);
                } else {
                    String tmp = String.format("%s%s%s\n", Tool.rep(' ',20), Tool.rep(' ',i), lst.get(j));
                    System.out.print(tmp);
                    if (i == stop) sb.append(tmp);
                }
            }
            if (i == stop) break;
            Tool.sleep(50);
        }
        return sb.toString();
    }

    private void printStatus(String progressBar) {
        Tool.clearScreen();
        person.print();
        System.out.printf("\n%s<Algojek App Screen>\n", Tool.rep(' ', 43));
        System.out.printf("\n%sJob on progress..\n", Tool.rep(' ', 45));
        System.out.print(customerDetails);
        System.out.print(progressBar);
    }

    public void prompt() {  //main prompt
        enterKeyThread.start();
        orderMethod();
    }
}
