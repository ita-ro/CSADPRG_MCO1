//  ********************
//  Last names: Aya-ay, Bicomong
//  Language: java
//  Paradigm(s): Functional, Imperative, Object-oriented
//  ********************

// Build: javac MP_2_java.java
// Run: java MP_2_java

import java.util.Scanner;

public class MP_2_java {

    static final Scanner scanner = new Scanner(System.in);
    static final String[] Currencies = {
        "Philippine Peso (PHP)", "United States Dollar (USD)", "Japanese Yen (JPY)",
        "British Pound Sterling (GBP)", "Euro (EUR)", "Chinese Yuan Renminni (CNY)"
    };
    static final double[] rates = {1.00, 62.00, 0.40, 84.00, 72.00, 9.00};
    static final double ANNUAL_INTEREST = 0.05;

    static String accountName = null;
    static double balance = 0.00;

    // validators
    static String readInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    static int readChoice(String prompt) {
        while (true) {
            
            try {
                int v = Integer.parseInt(readInput(prompt));

                if (v >= 1 && v <= 6) {
                    return v;
                }

            } catch (NumberFormatException ignored) { }

            System.out.println("Invalid input. Enter a number from 1 to 6.");
        }
    }

    static double isPositive(String prompt) {
        while (true) {

            try {
                double v = Double.parseDouble(readInput(prompt));

                if (v > 0) {
                    return v;
                }

            } catch (NumberFormatException ignored) { }

            System.out.println("Invalid input. Enter an amount greater than 0.");
        }
    }

    static boolean isYorN(String prompt) {
        while (true) {
            String s = readInput(prompt);

            if (s.equalsIgnoreCase("Y")) {
                return true;
            }

            if (s.equalsIgnoreCase("N")) {
                return false;
            }

            System.out.println("Please enter Y or N.");
        }
    }

    static void printCurrencies() {
        for (int i = 0; i < Currencies.length; i++) {
            System.out.println("[" + (i + 1) + "] " + Currencies[i]);
        }
            System.out.println();
    }

    static boolean hasAccount() {

        if (accountName == null) {
            System.out.println("No account registered yet. Please use [1] Register Account Name first.");
            return false;
        }

        return true;
    }

    static double round(double v) { 
        return Math.round(v * 100.0) / 100.0; 
    }

    // transactions 
    static void registerAccount() {
        System.out.println("\nRegister Account Name");
        String name;
        do { 
            name = readInput("Account Name: "); 
        } while (name.isEmpty());
        accountName = name;
    }

    static void deposit() {
        System.out.println("\nDeposit Amount");

        if (!hasAccount()) {
            return;
        }

        System.out.println("Account Name: " + accountName);
        System.out.printf("Current Balance: %.2f\n", balance);
        System.out.println("Currency: PHP");

        double amount = isPositive("Deposit Amount: ");
        balance = round(balance + amount);

        System.out.printf("Updated Balance: %.2f\n", balance);
    }

    static void withdraw() {
        System.out.println("\nWithdraw Amount");

        if (!hasAccount()){
            return;
        }

        System.out.println("Account Name: " + accountName);
        System.out.printf("Current Balance: %.2f\n", balance);
        System.out.println("Currency: PHP");
        double amount = isPositive("Withdraw Amount: ");

        if (amount > balance) {
            System.out.println("Insufficient balance. Withdrawal cancelled.");
            return;
        }

        balance = round(balance - amount);
        System.out.printf("Updated Balance: %.2f\n", balance);
    }

    static void recordExchangeRate() {
        System.out.println("\nRecord Exchange Rate");
        printCurrencies();
        int c = readChoice("Select Foreign Currency: ");

        if (c == 1) {
            System.out.println("PHP is the base currency; its rate is fixed at 1.00.");
            return;
        }

        rates[c - 1] = isPositive("Exchange Rate: ");
    }

    static void currencyExchange() {
        do {
            System.out.println("\nForeign Currency Exchange");
            System.out.println("Source Currency Option:");
            printCurrencies();

            int src = readChoice("Source Currency: ");
            double amount = isPositive("Source Amount: ");
            System.out.println("Exchanged Currency Options:");
            printCurrencies();

            int dst = readChoice("Exchange Currency: ");
            double result = amount * rates[src - 1] / rates[dst - 1];
            System.out.printf("Exchange Amount: %.2f\n", result);
        } while (isYorN("Convert another currency (Y/N)? "));
    }

    static void showInterest() {
        System.out.println("\nShow Interest Amount");

        if (!hasAccount()) {
            return;
        }

        System.out.println("Account Name: " + accountName);
        System.out.printf("Current Balance: %.2f\n", balance);
        System.out.println("Currency: PHP");
        System.out.println("Interest Rate: 5%");
        int days;

        while (true) {
            days = (int) isPositive("Total Number of Days: ");

            if (days >= 1 && days <= 36500) {
                break;
            }

            System.out.println("Invalid input. Enter a number from 1 to 36500.");
        }

        System.out.println("Day | Interest | Balance |");
        double running = balance;

        for (int d = 1; d <= days; d++) {
            double interest = round(running * (ANNUAL_INTEREST / 365));
            running = round(running + interest);
            System.out.printf("%d | %.2f | %.2f |\n", d, interest, running);
        }
    }

    public static void main(String[] args) {
        do {
            System.out.println("\nSelect Transaction:");
            System.out.println("[1] Register Account Name");
            System.out.println("[2] Deposit Amount");
            System.out.println("[3] Withdraw Amount");
            System.out.println("[4] Currency Exchange");
            System.out.println("[5] Record Exchange Rates");
            System.out.println("[6] Show Interest Amount");
            System.out.println();

            switch (readChoice("Choice: ")) {
                case 1 -> registerAccount();
                case 2 -> deposit();
                case 3 -> withdraw();
                case 4 -> currencyExchange();
                case 5 -> recordExchangeRate();
                case 6 -> showInterest();
            }

        } while (isYorN("Back to the Main Menu (Y/N): "));
    }
}
