package src.menu.orderscreen;

import src.utility.*;
import src.menu.*;
import src.playerstuff.*;
import java.util.Scanner;

public class AlgoFoodOrderScreen {
    private Person person;
    private Motorcycle motorcycle;
    private Runnable orderRunnable;
    private Thread orderThread;
    private volatile boolean isPressed;
    private Scanner sc;

    public AlgoFoodOrderScreen(Person person) {
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
        while (true) {
            isPressed = false;
            //finding customer animation
            int findDuration = Tool.getRandomIntegerWithRange(10, 32);
            for (int i = 1; i <= findDuration; i++) {
                if (isPressed) {
                    System.out.print("Finding job cancelled\n");
                    System.out.print("Press <enter> to Continue...");
                    Tool.waitForEnterKeyPressed(() -> {});
                    return;
                }
                Tool.clearScreen();
                person.print();
                System.out.print("\n>>>>>>>>>>>>>>>[A l g o  -  F o o d]<<<<<<<<<<<<<<<\n");
                System.out.println("\nFinding job "+sprites[i%4]);
                System.out.println("\nPress <enter> to cancel finding customer");
                Tool.sleep(125);
            }

            //show customer status
            String name = "Andy";
            int money = Tool.getRandomIntegerWithRange(20, 50)*1000;
            int energy = Tool.getRandomIntegerWithRange(5, 8);
            int fuel = Tool.getRandomIntegerWithRange(25, 55);
            for (int i = 8; i >= 1; i--) {
                if (isPressed) {
                    doJob(money, energy, fuel);
                    return;
                }
                Tool.clearScreen();
                person.print();
                System.out.print("\n>>>>>>>>>>>>>>>[A l g o  -  F o o d]<<<<<<<<<<<<<<<\n");
                System.out.print("\nJob found!!\n");
                System.out.printf("\n%-10s | %-11s | %-6s | %-4s\n", "Name", "Job Fare", "Energy", "Fuel");
                System.out.println("-----------------------------------------");
                System.out.printf("%-10s | +Rp. %,-6d | %6s | %4s\n", name, money, "-"+energy, "-"+fuel);
                System.out.printf("\nPress <enter> to ACCEPT (%d)", i);
                Tool.sleep(1000);
            }
        }
    }

    private boolean isJobDoable(int energy, int fuel) {
        return person.getEnergy() >= energy && motorcycle.getFuel() >= fuel;
    }

    private void doJob(int money, int energy, int fuel) {
        Tool.showProgressBar(15, 250,
                    () -> {
                        Tool.clearScreen();
                        person.print();
                        System.out.print("\n>>>>>>>>>>>>>>>[A l g o  -  F o o d]<<<<<<<<<<<<<<<\n");
                        System.out.print("\nDelivering Food on Progress:\n");
                    });
        person.setMoney(person.getMoney()+money);
        person.setEnergy(person.getEnergy()-energy);
        motorcycle.setFuel(motorcycle.getFuel()-fuel);

        Tool.clearScreen();
        person.print();
        System.out.print("\n>>>>>>>>>>>>>>>[A l g o  -  F o o d]<<<<<<<<<<<<<<<\n");
        System.out.print("\nDelivering Food on Progress:\n");
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
