package src.playerstuff;

import src.utility.*;
import java.util.*;
import java.io.Serializable;

public class Motorcycle implements Serializable {
    private int fuel;
    private int oilQuality;
    private int engineHealth;
    private int suspensionHealth;
    private int batteryHealth;

    public Motorcycle() {
        this.fuel = 70;
        this.oilQuality = 80;
        this.engineHealth = 80;
        this.suspensionHealth = 50;
        this.batteryHealth = 90;
    }

    public boolean isMotorcycleHealthy() {
        return oilQuality >= 10 &&
                engineHealth >= 10 &&
                batteryHealth >= 10;
    }

    public void setAllStateToZero() {
        fuel = 0;
        oilQuality = 0;
        engineHealth = 0;
        batteryHealth = 0;
    }

    public void decreaseStateALittle() {
        oilQuality -= Tool.getRandomIntegerWithRange(1, 2);
        if (Math.random() >= 0.5) engineHealth -= Tool.getRandomIntegerWithRange(1, 2);
        if (Math.random() >= 0.5) batteryHealth -= Tool.getRandomIntegerWithRange(1, 2);
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public int getEngineHealth() {
        return engineHealth;
    }

    public void setEngineHealth(int engineHealth) {
        this.engineHealth = engineHealth;
    }

    public int getOilQuality() {
        return oilQuality;
    }

    public void setOilQuality(int oilQuality) {
        this.oilQuality = oilQuality;
    }

    public int getSuspensionHealth() {
        return suspensionHealth;
    }

    public void setSuspensionHealth(int suspensionHealth) {
        this.suspensionHealth = suspensionHealth;
    }

    public int getBatteryHealth() {
        return batteryHealth;
    }

    public void setBatteryHealth(int batteryHealth) {
        this.batteryHealth = batteryHealth;
    }
}
