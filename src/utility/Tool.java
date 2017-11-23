package src.utility;

import src.utility.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;

public class Tool {
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
                System.out.print(rep('\n', 100));
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

    public static void printTextFileContent(String filePath) {
        List<String> lst = getStringListFromTextFile(filePath);
        for (String s : lst) System.out.println(s);
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception ex) {}
    }

	public static int sum(int[] data) {
		int total = 0;
		for (int i = 0; i < data.length; i++) total += data[i];
		return total;
	}

    public static void showProgressBar(int length, int delay, Routine routine) {
        for (int i = 1; i <= length; i++) {
            routine.doRoutine();
            System.out.printf("|%-"+(2*length-1)+"s| %d%%\n", Tool.rep("==",i-1)+">", (int)(i*100.0/length) );
            sleep(delay);
        }
    }

    public static String showProgressBar(int length, int delay, int stop, Routine routine) {
        String progressBar = "";
        for (int i = 1; i <= length; i++) {
            routine.doRoutine();
            progressBar = String.format("|%-"+(2*length-1)+"s| %d%%\n", Tool.rep("==",i-1)+">", (int)(i*100.0/length) );
            System.out.print(progressBar);
            sleep(delay);
            if (i == stop) break;
        }
        return progressBar;
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
