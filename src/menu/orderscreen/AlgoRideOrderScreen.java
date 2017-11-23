package src.menu.orderscreen;

import src.utility.*;
import src.menu.*;
import src.playerstuff.*;
import java.util.*;

public class AlgoRideOrderScreen {
    private String title = "\n>>>>>>>>>>>>>>>>>>>>[ A l g o  -  R i d e ]<<<<<<<<<<<<<<<<<<<<\n";
    private String customerDetails = "";
    private Person person;
    private Motorcycle motorcycle;
    private Runnable orderRunnable;
    private Thread orderThread;
    private volatile boolean isPressed; //is enter key pressed?
    private Scanner sc;
    private List<String> nameList;
    private List<String> streetList;

    public AlgoRideOrderScreen(Person person) {
        this.nameList = Tool.getStringListFromTextFile("res\\names.txt");
        this.streetList = Tool.getStringListFromTextFile("res\\streets.txt");
        this.sc = new Scanner(System.in);
        this.person = person;
        this.motorcycle = person.getMotorcycle();
        this.orderRunnable = () -> {
            orderMethod();
        };
        this.orderThread = new Thread(orderRunnable);
    }

    private void orderMethod() { //finding customer method
        char[] sprites = {'|', '/', '-', '\\'};
        isPressed = false;
        //search for customer infinitely
        while (true) {
            //finding customer animation
            int findDuration = Tool.getRandomIntegerWithRange(10, 24);
            for (int i = 1; i <= findDuration; i++) {
                if (isPressed) {
                    Tool.clearScreen();
                    person.print();
                    System.out.print(title);
                    System.out.print("\nFinding Customer cancelled\n\n");
                    System.out.print("Press <enter> to Continue...");
                    Tool.waitForEnterKeyPressed(() -> {});
                    return;
                }
                Tool.clearScreen();
                person.print();
                System.out.print(title);
                System.out.println("\nFinding customer "+sprites[i%4]);
                System.out.println("\nPress <enter> to cancel finding customer");
                Tool.sleep(250);
            }

            //customer state laoding
            String customer = nameList.get((int)(nameList.size()*Math.random()));
            String gender = customer.substring(0, 1);
            String name = customer.substring(2);
            int originIdx = (int)(streetList.size()*Math.random()); String origin = streetList.get(originIdx);
            int destIdx = (originIdx*47)%streetList.size(); String dest = streetList.get(destIdx);
            double rate = Math.random();
            String distance = String.format("%.1f", (8*rate));
            int money = ((int)(31*rate) + 5)*1000;
            int energy = ((int)(8*rate) + 3);
            int fuel = ((int)(31*rate) + 10);

            //show customer, ask for picking or not
            for (int i = 8; i >= 0; i--) {
                if (isPressed) {
                    doJob(money, energy, fuel, name, gender, distance, origin, dest);
                    return;
                }
                Tool.clearScreen();
                person.print();
                System.out.print(title);
                System.out.print("\nCustomer found!!\n");
                customerDetails = String.format("\n%-14s | %-11s | %-6s | %-4s | %s \n",
                                        "Name", "Job Fare", "Energy", "Fuel", "Trip Path") +
                                    Tool.rep('-', 88) + "\n" +
                                    String.format("%-14s | +Rp. %,-6d | %6s | %4s | %s\n",
                                        name+" ("+gender+")", money, "-"+energy, "-"+fuel+"%",
                                        origin+" -> "+dest+" ("+distance+"km)");
                System.out.print(customerDetails);
                if (!isJobDoable(energy, fuel)) {
                    System.out.print("\n **WARNING!!**");
                    System.out.print("\n Not enough energy/fuel");
                    System.out.print("\n Job becomes risky");
                    System.out.print("\n You may faint or experience motorcycle breakdown\n");
                }
                System.out.printf("\nPress <enter> to ACCEPT (%d)", i);
                Tool.sleep(1000);
            }
        }
    }

    private boolean isJobDoable(int energy, int fuel) {
        return person.getEnergy() >= energy && motorcycle.getFuel() >= fuel;
    }

    private void doJob(int money, int energy, int fuel, String name, String gender, String distance, String origin, String dest) {
        String progressBar = Tool.showProgressBar(15, 250, isJobDoable(energy, fuel)?15:Tool.getRandomIntegerWithRange(6, 10),
                                () -> {
                                    Tool.clearScreen();
                                    person.print();
                                    System.out.print(title);
                                    System.out.print("\nJob accepted..\n");
                                    System.out.print(customerDetails);
                                    System.out.print("\nJob on Progress:\n");
                                });
        if (person.getEnergy() < energy) {  // not enough energy
            double customerRating = Tool.getRandomIntegerWithRange(1, 2);
            double currentRating = ((person.getRating()*person.getTotalTrip())+customerRating)/(person.getTotalTrip()+1);
            person.setRating(currentRating);
            person.setEnergy(0);

            printStatus(progressBar);
            System.out.print("\nOh no!!\n");
            System.out.print("You fainted because you have too little energy for the job!\n\n");
            System.out.printf("Energy -> 0\n");
            System.out.printf("Driver Rating from Customer: %.1f\n", customerRating);
            System.out.printf("Current Driver Rating: %.1f\n", person.getRating());
        } else if (motorcycle.getFuel() < fuel) { // not enough fuel
            double customerRating = Tool.getRandomIntegerWithRange(1, 2);
            double currentRating = ((person.getRating()*person.getTotalTrip())+customerRating)/(person.getTotalTrip()+1);
            person.setRating(currentRating);
            motorcycle.setFuel(0);

            printStatus(progressBar);
            System.out.print("\nOh no!!\n");
            System.out.print("Your motorcycle broke down because it ran out of fuel!\n");
            System.out.printf("Fuel -> 0\n");
            System.out.printf("Driver Rating from Customer: %.1f\n", customerRating);
            System.out.printf("Current Driver Rating: %.1f\n", person.getRating());
        } else { //normal job flow
            //set some value
            int bonus = 0, minFuel = 0, minEnergy = 0;
            if (person.getAttractiveness()*Math.random() >= 35.0)
                bonus = (int)(person.getAttractiveness()*money/400.0);
            if (person.getIntelligence()*Math.random() >= 35.0)
                minFuel = (int)(person.getIntelligence()*fuel/400.0);
            if (person.getMuscleStrength()*Math.random() >= 35.0)
                minEnergy = (int)(person.getMuscleStrength()*energy/400.0);

            person.setMoney(person.getMoney()+money+bonus);
            person.setEnergy(person.getEnergy()-energy+minEnergy);
            motorcycle.setFuel(motorcycle.getFuel()-fuel+minFuel);
            double customerRating = Tool.getRandomIntegerWithRange(3, 5);
            double currentRating = ((person.getRating()*person.getTotalTrip())+customerRating)/(person.getTotalTrip()+1);
            person.setRating(currentRating);

            printStatus(progressBar);
            System.out.println("\nJob done..\n");
            System.out.printf("  Money  %-13s -> Rp. %,d %s\n",
                String.format("(+Rp. %,d)", money+bonus),
                person.getMoney(),
                bonus==0?"":String.format("\n  (Because of your Attractiveness, your customer gives you Rp. %,d as a Bonus!)", bonus));
            System.out.printf("\n  Energy %-13s -> %d %s\n",
                String.format("(-%d)", energy-minEnergy),
                person.getEnergy(),
                minEnergy==0?"":String.format("\n  (Because of your Muscle Strength, your have used %d less Energy!)", minEnergy));
            System.out.printf("\n  Fuel   %-13s -> %d%% %s\n",
                String.format("(-%d%%)", fuel-minFuel),
                motorcycle.getFuel(),
                minFuel==0?"":String.format("\n  (Because of your Intelligence in searching for shortest path, your have used %d%% less Fuel!)", minFuel));
            System.out.printf("\n  Driver Rating from Customer : %.1f\n", customerRating);
            System.out.printf("  Current Driver Rating       : %.1f\n", person.getRating());
        }
        System.out.print("\nPress <enter> to continue..");
        Tool.waitForEnterKeyPressed(() -> {});
    }

    private void printStatus(String progressBar) {
        Tool.clearScreen();
        person.print();
        System.out.print(title);
        System.out.print("\nJob accepted..\n");
        System.out.print(customerDetails);
        System.out.print("\nJob on Progress:\n");
        System.out.print(progressBar);
    }

    public void prompt() {  //main prompt
        orderThread.start();
        Tool.waitForEnterKeyPressed(() -> {isPressed = true;});
    }
}
