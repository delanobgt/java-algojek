package src.playerstuff;

import java.util.*;
import java.io.Serializable;

public class Motorcycle implements Serializable {
    private int fuel;
    private int oilQuality;
    private int engineHealth;
    private int suspensionHealth;
    private int batteryHealth;

    public Motorcycle() {
        this.fuel = 90;
        this.oilQuality = 70;
        this.engineHealth = 80;
        this.suspensionHealth = 10;
        this.batteryHealth = 10;
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
