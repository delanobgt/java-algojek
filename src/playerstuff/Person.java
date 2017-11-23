package src.playerstuff;

import src.utility.*;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class Person implements Serializable {
    private String name;
    //------------------------------
    private int day;
    private int energy, maxEnergy;
    //------------------------------
    private int money;
    private double rating;
    private int tripOfTheDay;
    private int totalTrip;
    //------------------------------
    private int intelligence;
    private int muscleStrength;
    private int attractiveness;
    private int girlsDated;
    //-------------------------------
    private Motorcycle motorcycle;
    private Achievements achievements;

    public Person(String name) {
        this.name = "Algojek - "+name;
        //-------------------------------
        this.day = 1;
        this.energy = 20; this.maxEnergy = 20;
        //-------------------------------
        this.money = 1_000_000;
        this.tripOfTheDay = 0;
        this.totalTrip = 0;
        //-------------------------------
        this.intelligence = 20;
        this.muscleStrength = 20;
        this.attractiveness = 60;
        this.girlsDated = 0;
        //-------------------------------
        this.motorcycle = new Motorcycle();
        this.achievements = new Achievements();
    }

    public void print() {
        int gap = 5;
        System.out.printf("%-42s", Tool.rep('*', name.length()+4));                         System.out.printf("%s %s\n", Tool.rep(' ',gap), Tool.rep('*',14));
        System.out.printf("%-42s", "* "+name+" *");                                         System.out.printf("%s * %s *\n", Tool.rep(' ', gap), "Motorcycle");
        System.out.print(Tool.rep('*', 42));                                                System.out.printf("%s %s\n", Tool.rep(' ',gap), Tool.rep('*',30));
        System.out.printf("* %-18s : %-17d *", "Day", day);                                 System.out.printf("%s * %-17s : %3d %%  *\n", Tool.rep(' ',gap), "Fuel", motorcycle.getFuel());
        System.out.printf("* %-18s : %-17s *", "Energy", energy+"/"+maxEnergy);             System.out.printf("%s * %-17s : %3d %%  *\n", Tool.rep(' ',gap), "Oil Quality", motorcycle.getOilQuality());
        System.out.printf("*%s*", Tool.rep('-', 40));                                       System.out.printf("%s * %-17s : %3d %%  *\n", Tool.rep(' ',gap), "Engine Health", motorcycle.getEngineHealth());
        System.out.printf("* %-18s   %-17s *", "(Career)", "");                             System.out.printf("%s * %-17s : %3d %%  *\n", Tool.rep(' ',gap), "Suspension Health", motorcycle.getSuspensionHealth());
        System.out.printf("* %-18s : Rp. %,-13d *", "- Money", money);                      System.out.printf("%s * %-17s : %3d %%  *\n", Tool.rep(' ',gap), "Battery Health", motorcycle.getBatteryHealth());
        System.out.printf("* %-18s : %-17.1f *", "- Driver Rating", rating);                       System.out.printf("%s %s\n", Tool.rep(' ',gap), Tool.rep('*',30));
        System.out.printf("* %-18s : %-17s *\n", "- Trip of the Day", tripOfTheDay+"/"+5);
        System.out.printf("* %-18s : %-17d *\n", "- Total Trip", totalTrip);
        System.out.printf("*%s*\n", Tool.rep('-', 40));
        System.out.printf("* %-18s   %-17s *\n", "(Personal)", "");
        System.out.printf("* %-18s : %-17s *\n", "- Intelligence", intelligence+"/"+"100");
        System.out.printf("* %-18s : %-17s *\n", "- Muscle Strength", muscleStrength+"/"+"100");
        System.out.printf("* %-18s : %-17s *\n", "- Attractiveness", attractiveness+"/"+"100");
        System.out.printf("* %-18s : %-17d *\n", "- Girl(s) Dated", girlsDated);
        System.out.println(Tool.rep('*', 42));
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getDay() {return day;}
    public void setDay(int day) {this.day = day;}
    public int getEnergy() {return energy;}
    public void setEnergy(int energy) {this.energy = energy;}
    public int getMaxEnergy() {return maxEnergy;}
    public void setMaxEnergy(int maxEnergy) {this.maxEnergy = maxEnergy;}
    public int getMoney() {return money;}
    public void setMoney(int money) {this.money = money;}
    public double getRating() {return rating;}
    public void setMoney(double rating) {this.rating = rating;}
    public int getTripOfTheDay() {return tripOfTheDay;}
    public void setTripOfTheDay(int tripOfTheDay) {this.tripOfTheDay = tripOfTheDay;}
    public int getTotalTrip() {return totalTrip;}
    public void setTotalTrip(int totalTrip) {this.totalTrip = totalTrip;}
    public int getIntelligence() {return intelligence;}
    public void setIntelligence(int intelligence) {
        this.intelligence = Math.min(100, intelligence);
        this.intelligence = Math.max(0, intelligence);
    }
    public int getMuscleStrength() {return muscleStrength;}
    public void setMuscleStrength(int muscleStrength) {
        this.muscleStrength = Math.min(100, muscleStrength);
        this.muscleStrength = Math.max(0, muscleStrength);
    }
    public int getAttractiveness() {return attractiveness;}
    public void setAttractiveness(int attractiveness) {
        this.attractiveness = Math.min(100, attractiveness);
        this.attractiveness = Math.max(0, attractiveness);
    }
    public int getGirlsDated() {return girlsDated;}
    public void setGirlsDated(int girlsDated) {this.girlsDated = girlsDated;}
    public Motorcycle getMotorcycle() {return motorcycle;}
    public void setMotorcycle(Motorcycle motorcycle) {this.motorcycle = motorcycle;}
    public Achievements getAchievements() {return achievements;}
    public void setAchievements(Achievements achievements) {this.achievements = achievements;}

}
