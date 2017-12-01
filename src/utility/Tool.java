package src.utility;

import src.menu.WelcomeScreen;
import src.utility.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;

public class Tool {
    private static String wholeScreenNewLine = rep('\n', 100);

    public static <T> String rep(T ch, int n) {
        StringBuilder sb = new StringBuilder();
        while (n-- > 0) sb.append(ch);
        return sb.toString();
    }

    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception ex1) {
            try {
                Runtime.getRuntime().exec("cls");
            } catch (Exception ex2) {
                System.out.print(wholeScreenNewLine);
            }
        }
    }

    public static int getIntegerInputWithRange(int left, int right, Routine routine) {
        do {
            Scanner sc = new Scanner(System.in);
            boolean isValid = true;
            routine.doRoutine();
            String inputString = null;
            try {inputString = sc.nextLine();} catch (Exception ex) {isValid = false;}
            if (isValid && inputString.matches("\\d{1,9}")) {
                int inputInt = Integer.parseInt(inputString);
                if (left <= inputInt && inputInt <= right) return inputInt;
            }
        } while (true);
    }

    public static String getStringInputWithLengthRange(int left, int right, Routine routine) {
        do {
            Scanner sc = new Scanner(System.in);
            boolean isValid = true;
            routine.doRoutine();
            String input = null;
            try {input = sc.nextLine();} catch (Exception ex) {isValid = false;}
            if ( isValid && input.matches(String.format("[a-zA-Z]{%d,%d}", left, right)) ) return input;
        } while (true);
    }

    public static List<String> getStringListFromTextFile(String filePath) {
        List<String> lst = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String s;
            while ((s = br.readLine()) != null) lst.add(s);
        } catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }

    public static String getStringFromTextFile(String filePath) {
        List<String> lst = getStringListFromTextFile(filePath);
        StringBuilder sb = new StringBuilder();
        for (String s : lst) sb.append(s).append('\n');
        return sb.toString();
    }

    public static String getStringFromTextFile(String filePath, int tabSize) {
        List<String> lst = getStringListFromTextFile(filePath);
        StringBuilder sb = new StringBuilder();
        for (String s : lst) sb.append(Tool.rep(' ',tabSize)).append(s).append('\n');
        return sb.toString();
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception ex) {}
    }
    
    public static int getRandomIntegerWithRange(int a, int b) {
        return ( (int)(Math.random()*(b-a+1)) ) + a;
    }

    public static void waitForEnterKeyPressed(Routine routine) {
        Scanner sc = new Scanner(System.in);
        try {
            sc.nextLine();
            routine.doRoutine();
        } catch (Exception ex) {
            routine.doRoutine();
        }
    }
}
