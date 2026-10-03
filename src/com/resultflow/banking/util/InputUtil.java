package com.resultflow.banking.util;
import java.util.Scanner;
public final class InputUtil {
    private static final Scanner SC = new Scanner(System.in);
    private InputUtil() {}
    public static String readLine(String prompt) { System.out.print(prompt); return SC.nextLine().trim(); }
    public static int readInt(String prompt) {
        while (true) try { return Integer.parseInt(readLine(prompt)); } catch (NumberFormatException e) { System.out.println("Enter a valid number."); }
    }
    public static double readDouble(String prompt) {
        while (true) try { return Double.parseDouble(readLine(prompt)); } catch (NumberFormatException e) { System.out.println("Enter a valid amount."); }
    }
}