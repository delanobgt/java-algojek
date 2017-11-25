package src.menu.orderscreen;

import src.utility.*;
import src.menu.*;
import src.playerstuff.*;
import java.util.*;

public class AlgoFoodOrderScreen {
    private String title = "\n>>>>>>>>>>>>>>>>>>>>[ A L G O  -  F O O D ]<<<<<<<<<<<<<<<<<<<<\n";
    private String customerDetails = "";
    private Person person;
    private Motorcycle motorcycle;
    private Runnable orderRunnable;
    private Thread orderThread;
    private volatile boolean isPressed; //is enter key pressed?
    private Scanner sc;
    private List<String> nameList;
    private List<String> streetList;
    private List<String> foodList;

    public AlgoFoodOrderScreen(Person person) {
        this.nameList = Tool.getStringListFromTextFile("res\\names.txt");
        this.streetList = Tool.getStringListFromTextFile("res\\streets.txt");
        this.foodList= Tool.getStringListFromTextFile("res\\foods.txt");
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
                Tool.clearScreen();
                person.print();
                System.out.print(title);
                System.out.println("\nFinding Algo-Food Job "+sprites[i%4]);
                System.out.println("\nPress <enter> to cancel Finding Job");
                Tool.sleep(250);
                if (isPressed) {
                    Tool.clearScreen();
                    person.print();
                    System.out.print(title);
                    System.out.print("\nFinding Job cancelled\n\n");
                    System.out.print("Press <enter> to Continue...");
                    Tool.waitForEnterKeyPressed(() -> {});
                    return;
                }
            }

            //job state laoding
            String customer = nameList.get((int)(nameList.size()*Math.random()));
            String gender = customer.substring(0, 1);
            String name = customer.substring(2);
            int foodIdx = (int)(Math.random()*foodList.size());
            String food = foodList.get(foodIdx);
            int originIdx = (int)(streetList.size()*Math.random());
            String origin = streetList.get(originIdx);

            double rate = Math.random();
            String distance = String.format("%.1f", (8*rate));
            int money = ((int)(31*rate) + 20)*1000;
            int energy = ((int)(4*rate) + 5);
            int fuel = ((int)(31*rate) + 25);

            //show job, ask for picking or not
            for (int i = 8; i >= 1; i--) {
                Tool.clearScreen();
                person.print();
                System.out.print(title);
                System.out.print("\nAlgo-Food Job found!!\n");
                customerDetails = String.format("\n%-14s | %-10s | %-11s | %-6s | %-4s | %s \n",
                                        "Customer Name", "Food Name", "Job Fare", "Energy", "Fuel", "Home Address") +
                                Tool.rep('-', 90) + "\n" +
                                String.format("%-14s | %-10s | +Rp. %,-6d | %6s | %4s | %s\n",
                                        name+" ("+gender+")", food, money, "-"+energy, "-"+fuel+"%",
                                        origin+" ("+distance+"km)");
                System.out.print(customerDetails);
                if (!isJobDoable(energy, fuel)) {
                    System.out.print("\n **WARNING!!**");
                    System.out.print("\n Not enough energy/fuel or Motorcycle is not healthy enough");
                    System.out.print("\n Job becomes risky");
                    System.out.print("\n You may faint or experience motorcycle breakdown\n");
                }
                System.out.printf("\nPress <enter> to ACCEPT (%d)", i);
                Tool.sleep(1000);
                if (isPressed) {
                    doJob(money, energy, fuel, name, gender, distance, origin);
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

    private void doJob(int money, int energy, int fuel, String name, String gender, String distance, String origin) {
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
            System.out.print("\n  Oh no!!\n");
            System.out.print("  You fainted because you have too little energy for the job!\n\n");
            System.out.print("  Your customer is so pissed off\n");
            System.out.printf("- Energy -> 0\n");
            System.out.printf("- Driver Rating from Customer: %.1f\n", customerRating);
            System.out.printf("- Current Driver Rating: %.1f\n", person.getRating());
        } else if (motorcycle.getFuel() < fuel) { // not enough fuel
            double customerRating = Tool.getRandomIntegerWithRange(1, 2);
            double currentRating = ((person.getRating()*person.getTotalTrip())+customerRating)/(person.getTotalTrip()+1);
            person.setRating(currentRating);
            motorcycle.setFuel(0);

            printStatus(progressBar);
            System.out.print("\n  Oh no!!\n");
            System.out.print("  Your motorcycle can't go on anymore because it ran out of fuel!\n");
            System.out.print("  Your customer is so pissed off\n");
            System.out.printf("- Fuel -> 0\n");
            System.out.printf("- Driver Rating from Customer: %.1f\n", customerRating);
            System.out.printf("- Current Driver Rating: %.1f\n", person.getRating());
        } else if (!motorcycle.isMotorcycleHealthy()) { //motorcycle poor health
            double customerRating = Tool.getRandomIntegerWithRange(1, 2);
            double currentRating = ((person.getRating()*person.getTotalTrip())+customerRating)/(person.getTotalTrip()+1);
            person.setRating(currentRating);
            motorcycle.setAllStateToZero();

            printStatus(progressBar);
            System.out.print("\n  Oh no!!\n");
            System.out.print("  Your motorcycle broke down because it's engine/oil/suspension/battery is not healthy!\n");
            System.out.print("  Your customer is so pissed off\n");
            System.out.printf("  You got low Driver Rating and a broken motocycle (all state becomes 0)\n");
            System.out.printf("  You should fix it at the Motorcycle Services Menu\n\n");
            System.out.printf("- Driver Rating from Customer: %.1f\n", customerRating);
            System.out.printf("- Current Driver Rating: %.1f\n", person.getRating());
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

            printStatus(progressBar);
            System.out.println("\nJob done..\n");
            System.out.println("- Motorcycle State decreased a little");
            System.out.printf("- Money  %-13s -> Rp. %,d %s%s\n",
                String.format("(+Rp. %,d)", money+bonus+tip),
                person.getMoney(),
                bonus==0?"":String.format("\n  (For completing 5 trip, you get Rp. %,d as a Bonus!)", bonus),
                tip==0?"":String.format("\n  (Because of your Attractiveness, your customer gives you Rp. %,d as a Tip!)", tip));
            System.out.printf("- Energy %-13s -> %d %s\n",
                String.format("(-%d)", energy-minEnergy),
                person.getEnergy(),
                minEnergy==0?"":String.format("\n  (Because of your Muscle Strength, your have used %d less Energy!)", minEnergy));
            System.out.printf("- Fuel   %-13s -> %d%% %s\n",
                String.format("(-%d%%)", fuel-minFuel),
                motorcycle.getFuel(),
                minFuel==0?"":String.format("\n  (Because of your Intelligence in searching for shortest path, your have used %d%% less Fuel!)", minFuel));
            System.out.printf("- Trip of the Day (+1) -> %d", person.getTotalTrip());
            System.out.printf("\n- Driver Rating from Customer : %d {%s }\n", customerRating, Tool.rep(" *", customerRating)+Tool.rep(" -", 5-customerRating));
            System.out.printf("- Current Driver Rating       : %.1f\n", person.getRating());
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
