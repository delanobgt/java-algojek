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
    //-------------------------------
    private Motorcycle motorcycle;

    public Person(String name) {
        this.name = "Algojek - "+name;
        //-------------------------------
        this.day = 1;
        this.energy = 30; this.maxEnergy = 30;
        //-------------------------------
        this.money = 100_000;
        this.rating = 0;
        this.tripOfTheDay = 0;
        this.totalTrip = 0;
        //-------------------------------
        this.intelligence = 35;
        this.muscleStrength = 35;
        this.attractiveness = 35;
        //-------------------------------
        this.motorcycle = new Motorcycle();
    }

    public void print() {
        char filledChar = '=';
        int barLength;
        int barLength2;
        int leftPad = (104-(name.length()+10))/2;
        System.out.printf("%s/%s\\\n", Tool.rep(' ',leftPad), Tool.rep('-',name.length()+8));
        System.out.printf("  Day : %-3d%s| <> %s <> |\n", day, Tool.rep(' ', leftPad-11), name);
        System.out.printf("/---------------------------------------------------------------------------------------------------\\\n");
        barLength = (int)Math.ceil(10.0*energy/maxEnergy);
        System.out.printf("| Money : Rp. %,-11d                                               Energy [%-10s] %3d/%-3d |\n",
                            money,
                            Tool.rep(filledChar,barLength), energy, maxEnergy);
        System.out.printf(":--------------------------------------+-------------------------+----------------------------------:\n");
        System.out.printf("|              [Personal]              |        [Career]         |           [Motorcycle]           |\n");
        System.out.printf("|                                      |                         |                                  |\n");
        barLength = (int)Math.ceil(10*intelligence/100.0);
        barLength2 = (int)Math.ceil(10*motorcycle.getFuel()/100.0);
        System.out.printf("| Intelligence    [%-10s] %3d/100 | Rating: %.1f %s | Fuel           [%-10s] %3d%% |\n",
                            Tool.rep(filledChar,barLength), intelligence,
                            rating, rating==0? "(- - - - -)" : "(*"+Tool.rep(" *", (int)rating-1)+Tool.rep(" -", 5-(int)rating)+")",
                            Tool.rep(filledChar,barLength2), motorcycle.getFuel());
        barLength = (int)Math.ceil(10*muscleStrength/100.0);
        barLength2 = (int)Math.ceil(10*motorcycle.getOilQuality()/100.0);
        System.out.printf("| Muscle Strength [%-10s] %3d/100 | Today Trip: %2d/5        | Oil Quality    [%-10s] %3d%% |\n",
                            Tool.rep(filledChar,barLength), muscleStrength,
                            tripOfTheDay,
                            Tool.rep(filledChar,barLength2), motorcycle.getOilQuality());
        barLength = (int)Math.ceil(10*attractiveness/100.0);
        barLength2 = (int)Math.ceil(10*motorcycle.getEngineHealth()/100.0);
        System.out.printf("| Attractiveness  [%-10s] %3d/100 | Total Trip: %-3d         | Engine Health  [%-10s] %3d%% |\n",
                            Tool.rep(filledChar,barLength), attractiveness,
                            totalTrip,
                            Tool.rep(filledChar,barLength2), motorcycle.getEngineHealth());
        barLength = (int)Math.ceil(10*motorcycle.getBatteryHealth()/100.0);
        System.out.printf("|                                      |                         | Battery Health [%-10s] %3d%% |\n",
                            Tool.rep(filledChar,barLength), motorcycle.getBatteryHealth());
        System.out.printf("\\--------------------------------------+-------------------------+----------------------------------/\n");
    }

    public void printOld() {
        int gap = 5;
        System.out.printf("%-42s", Tool.rep('*', name.length()+4));                         System.out.printf("%s %s\n", Tool.rep(' ',gap), Tool.rep('*',14));
        System.out.printf("%-42s", "* "+name+" *");                                         System.out.printf("%s * %s *\n", Tool.rep(' ', gap), "Motorcycle");
        System.out.print(Tool.rep('*', 42));                                                System.out.printf("%s %s\n", Tool.rep(' ',gap), Tool.rep('*',30));
        System.out.printf("* %-18s : %-17d *", "Day", day);                                 System.out.printf("%s * %-17s : %3d %%  *\n", Tool.rep(' ',gap), "Fuel", motorcycle.getFuel());
        System.out.printf("* %-18s : %-17s *", "Energy", energy+"/"+maxEnergy);             System.out.printf("%s * %-17s : %3d %%  *\n", Tool.rep(' ',gap), "Oil Quality", motorcycle.getOilQuality());
        System.out.printf("*%s*", Tool.rep('-', 40));                                       System.out.printf("%s * %-17s : %3d %%  *\n", Tool.rep(' ',gap), "Engine Health", motorcycle.getEngineHealth());
        System.out.printf("* %-18s   %-17s *", "(Career)", "");
        System.out.printf("* %-18s : Rp. %,-13d *", "- Money", money);                      System.out.printf("%s * %-17s : %3d %%  *\n", Tool.rep(' ',gap), "Battery Health", motorcycle.getBatteryHealth());
        System.out.printf("* %-18s : %-17.1f *", "- Driver Rating", rating);                       System.out.printf("%s %s\n", Tool.rep(' ',gap), Tool.rep('*',30));
        System.out.printf("* %-18s : %-17s *\n", "- Trip of the Day", tripOfTheDay+"/"+5);
        System.out.printf("* %-18s : %-17d *\n", "- Total Trip", totalTrip);
        System.out.printf("*%s*\n", Tool.rep('-', 40));
        System.out.printf("* %-18s   %-17s *\n", "(Personal)", "");
        System.out.printf("* %-18s : %-17s *\n", "- Intelligence", intelligence+"/"+"100");
        System.out.printf("* %-18s : %-17s *\n", "- Muscle Strength", muscleStrength+"/"+"100");
        System.out.printf("* %-18s : %-17s *\n", "- Attractiveness", attractiveness+"/"+"100");
        System.out.println(Tool.rep('*', 42));
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getDay() {return day;}
    public void setDay(int day) {this.day = day;}
    public int getEnergy() {return energy;}
    public void setEnergy(int energy) {this.energy = energy;}
    public int getMaxEnergy() {return maxEnergy;}
    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = Math.max(0, maxEnergy);
        this.maxEnergy = Math.min(100, this.maxEnergy);
    }
    public int getMoney() {return money;}
    public void setMoney(int money) {this.money = money;}
    public double getRating() {return rating;}
    public void setRating(double rating) {this.rating = rating;}
    public int getTripOfTheDay() {return tripOfTheDay;}
    public void setTripOfTheDay(int tripOfTheDay) {this.tripOfTheDay = tripOfTheDay;}
    public int getTotalTrip() {return totalTrip;}
    public void setTotalTrip(int totalTrip) {this.totalTrip = totalTrip;}
    public int getIntelligence() {return intelligence;}
    public void setIntelligence(int intelligence) {
        this.intelligence = Math.min(100, intelligence);
        this.intelligence = Math.max(0, this.intelligence);
    }
    public int getMuscleStrength() {return muscleStrength;}
    public void setMuscleStrength(int muscleStrength) {
        this.muscleStrength = Math.min(100, muscleStrength);
        this.muscleStrength = Math.max(0, this.muscleStrength);
    }
    public int getAttractiveness() {return attractiveness;}
    public void setAttractiveness(int attractiveness) {
        this.attractiveness = Math.min(100, attractiveness);
        this.attractiveness = Math.max(0, this.attractiveness);
    }
    public Motorcycle getMotorcycle() {return motorcycle;}
    public void setMotorcycle(Motorcycle motorcycle) {this.motorcycle = motorcycle;}
}
