package src.menu.orderscreen;

import src.utility.*;
import src.menu.*;
import src.playerstuff.*;
import java.util.*;

public class AlgoRideOrderScreen {
    private Person person;
    private Motorcycle motorcycle;
    private Runnable orderRunnable;
    private Thread orderThread;
    private volatile boolean isPressed;
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
        while (true) {
            //finding customer animation
            int findDuration = Tool.getRandomIntegerWithRange(10, 24);
            for (int i = 1; i <= findDuration; i++) {
                if (isPressed) {
                    System.out.print("Finding Customer cancelled\n");
                    System.out.print("Press <enter> to Continue...");
                    Tool.waitForEnterKeyPressed(() -> {});
                    return;
                }
                Tool.clearScreen();
                person.print();
                System.out.print("\n>>>>>>>>>>>>>>>>>>>>[ A l g o  -  R i d e ]<<<<<<<<<<<<<<<<<<<<\n");
                System.out.println("\nFinding customer "+sprites[i%4]);
                System.out.println("\nPress <enter> to cancel finding customer");
                Tool.sleep(250);
            }

            //show customer status
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
            for (int i = 8; i >= 1; i--) {
                if (isPressed) {
                    doJob(money, energy, fuel, name, gender, distance, origin, dest);
                    return;
                }
                Tool.clearScreen();
                person.print();
                System.out.print("\n>>>>>>>>>>>>>>>>>>>>[ A l g o  -  R i d e ]<<<<<<<<<<<<<<<<<<<<\n");
                System.out.print("\nCustomer found!!\n");
                System.out.printf("\n%-14s | %-11s | %-6s | %-4s | %s \n",
                                    "Name", "Job Fare", "Energy", "Fuel", "Trip Path");
                System.out.println(Tool.rep('-', 88));
                System.out.printf("%-14s | +Rp. %,-6d | %6s | %4s | %s\n",
                                    name+" ("+gender+")", money, "-"+energy, "-"+fuel+"%",
                                    origin+" -> "+dest+" ("+distance+"km)");
                if (!isJobDoable(energy, fuel)) {
                    System.out.print("\n**Job not do-able, not enough energy/fuel**");
                    break;
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
        Tool.showProgressBar(15, 250,
                    () -> {
                        Tool.clearScreen();
                        person.print();
                        System.out.print("\n>>>>>>>>>>>>>>>>>>>>[ A l g o  -  R i d e ]<<<<<<<<<<<<<<<<<<<<\n");
                        System.out.print("\nJob on Progress:\n");
                    });
        person.setMoney(person.getMoney()+money);
        person.setEnergy(person.getEnergy()-energy);
        motorcycle.setFuel(motorcycle.getFuel()-fuel);

        Tool.clearScreen();
        person.print();
        System.out.print("\n>>>>>>>>>>>>>>>>>>>>[ A l g o  -  R i d e ]<<<<<<<<<<<<<<<<<<<<\n");
        System.out.print("\nJob on Progress:\n");
        System.out.printf("|%s>| %d%%\n", Tool.rep("==", 15-1), 100);

        System.out.println("\nJob done..");
        System.out.printf("\nMoney(+Rp. %,d) -> Rp. %,d\n", money, person.getMoney());
        System.out.printf("Energy(-%d) -> %d\n", energy, person.getEnergy());
        System.out.printf("Fuel(-%d) -> %d\n", fuel, motorcycle.getFuel());
        System.out.print("\nPress <enter> to continue..");
        Tool.waitForEnterKeyPressed(() -> {});
    }

    public void prompt() {  //main prompt
        orderThread.start();
        Tool.waitForEnterKeyPressed(() -> {isPressed = true;});
    }
}
