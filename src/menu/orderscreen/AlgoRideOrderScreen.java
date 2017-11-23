package src.menu.orderscreen;

import src.utility.*;
import src.menu.*;
import src.playerstuff.*;
import java.util.*;

public class AlgoRideOrderScreen {
    private String title = "\n>>>>>>>>>>>>>>>>>>>>[ A l g o  -  R i d e ]<<<<<<<<<<<<<<<<<<<<\n";
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
            for (int i = 8; i >= 1; i--) {
                if (isPressed) {
                    doJob(money, energy, fuel, name, gender, distance, origin, dest);
                    return;
                }
                Tool.clearScreen();
                person.print();
                System.out.print(title);
                System.out.print("\nCustomer found!!\n");
                System.out.printf("\n%-14s | %-11s | %-6s | %-4s | %s \n",
                                    "Name", "Job Fare", "Energy", "Fuel", "Trip Path");
                System.out.println(Tool.rep('-', 88));
                System.out.printf("%-14s | +Rp. %,-6d | %6s | %4s | %s\n",
                                    name+" ("+gender+")", money, "-"+energy, "-"+fuel+"%",
                                    origin+" -> "+dest+" ("+distance+"km)");
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
                                    System.out.print("\nJob on Progress:\n");
                                });
        if (person.getEnergy() < energy) {  // not enough energy
            double customerRating = (int)(((Math.random()*2)+1.0)*10)/10.0;
            double currentRating = ((person.getRating()*person.getTotalTrip())+customerRating)/(person.getTotalTrip()+1);
            person.setRating(currentRating);
            person.setEnergy(0);

            printStatus(progressBar);
            System.out.print("\nOh no!!\n");
            System.out.print("You fainted because you have too little energy for the job!\n\n");
            System.out.printf("Energy -> 0\n");
            System.out.printf("Driver Rating from Customer: %.1f\n", customerRating);
            System.out.printf("Current Driver Rating: %.1f\n", person.getRating());
        } else if (motorcycle.getFuel() < fuel) { // not enough
            double customerRating = (int)(((Math.random()*2)+1.0)*10)/10.0;
            double currentRating = ((person.getRating()*person.getTotalTrip())+customerRating)/(person.getTotalTrip()+1);
            person.setRating(currentRating);
            motorcycle.setFuel(0);

            printStatus(progressBar);
            System.out.print("\nOh no!!\n");
            System.out.print("Your motorcycle broke down because it ran out of fuel!\n");
            System.out.printf("Fuel -> 0\n");
            System.out.printf("Driver Rating from Customer: %.1f\n", customerRating);
            System.out.printf("Current Driver Rating: %.1f\n", person.getRating());
        } else {
            //set some value
            person.setMoney(person.getMoney()+money);
            person.setEnergy(person.getEnergy()-energy);
            motorcycle.setFuel(motorcycle.getFuel()-fuel);
            double customerRating = (int)(((Math.random()*2)+1.0)*10)/10.0;
            double currentRating = ((person.getRating()*person.getTotalTrip())+customerRating)/(person.getTotalTrip()+1);
            person.setRating(currentRating);
            
            //display final status
            double chance = Math.random();
            if (chance <= 0.15 && gender.equals("F") && person.getAttractiveness() >= 60) { //date a girl
                person.setGirlsDated(person.getGirlsDated()+1);
                printStatus(progressBar);
                System.out.println("\nJob done..\n");
                System.out.printf("As a attractive driver, %s seems to like you!\n", name);
                System.out.printf("Both of you go on a date to Cemara Asri (Free of cost)\n\n");

                System.out.printf("Girls Dated(+1) -> %d\n", person.getGirlsDated());
                System.out.printf("Money(+Rp. %,d) -> Rp. %,d\n", money, person.getMoney());
                System.out.printf("Energy(-%d) -> %d\n", energy, person.getEnergy());
                System.out.printf("Fuel(-%d) -> %d\n", fuel, motorcycle.getFuel());
            } else if (chance >= 0.85 && person.getMoney() >= 70_000) { //begal
                if (person.getMuscleStrength() >= 50) {
                    printStatus(progressBar);
                    System.out.println("\nJob done..\n");
                    System.out.print("You got attacked by Medan Begal Club!\n");
                    System.out.print("Fortunately, you are strong enough to fight them ^.^\n");

                    System.out.printf("\nMoney(+Rp. %,d) -> Rp. %,d\n", money, person.getMoney());
                    System.out.printf("Energy(-%d) -> %d\n", energy, person.getEnergy());
                    System.out.printf("Fuel(-%d) -> %d\n", fuel, motorcycle.getFuel());
                } else {
                    person.setMoney(person.getMoney()-money);
                    printStatus(progressBar);
                    System.out.println("\nJob done..\n");
                    System.out.print("Oh my god!! You got robbed by Medan Begal Club\n");
                    System.out.print("You lost the fight because your Muscle Strength is less than 50\n");
                    System.out.print("As a result, you lost this job fare T.T\n");

                    System.out.printf("\nMoney(+Rp. %,d) -> Rp. %,d\n", 0, person.getMoney());
                    System.out.printf("Energy(-%d) -> %d\n", energy, person.getEnergy());
                    System.out.printf("Fuel(-%d) -> %d\n", fuel, motorcycle.getFuel());
                }
            } else {
                printStatus(progressBar);
                System.out.println("\nJob done..");
                System.out.printf("\nMoney(+Rp. %,d) -> Rp. %,d\n", money, person.getMoney());
                System.out.printf("Energy(-%d) -> %d\n", energy, person.getEnergy());
                System.out.printf("Fuel(-%d) -> %d\n", fuel, motorcycle.getFuel());
            }
        }
        System.out.print("\nPress <enter> to continue..");
        Tool.waitForEnterKeyPressed(() -> {});
    }

    private void printStatus(String progressBar) {
        Tool.clearScreen();
        person.print();
        System.out.print(title);
        System.out.print("\nJob on Progress:\n");
        System.out.print(progressBar);
    }

    public void prompt() {  //main prompt
        orderThread.start();
        Tool.waitForEnterKeyPressed(() -> {isPressed = true;});
    }
}
