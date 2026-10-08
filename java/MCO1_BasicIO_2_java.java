//  ********************
//  Last names: Aya-ay, Bicomong
//  Language: java
//  Paradigm(s): Functional, Imperative, Array-oriented, Object-oriented
//  ********************
import java.util.Scanner;

public class MCO1_BasicIO_2_java {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // main
        System.out.println("Select Transaction:");
        System.out.println("[1] Register Account Name");
        System.out.println("[2] Deposit Amount");
        System.out.println("[3] Withdraw Amount");
        System.out.println("[4] Currency Exchange");
        System.out.println("[5] Record Exchange Rates");
        System.out.println("[6] Show Interest Amount");
        System.out.println();
        
        System.out.print("Choice: ");
        String choice = scanner.nextLine();
        System.out.println();
        System.out.print("***");
        
        System.out.println("\nChoice = " + choice + "\n");
        
        // register
        System.out.println("Register Account Name");
        System.out.print("Account Name: ");
        String accountName = scanner.nextLine();
        System.out.println();
        System.out.print("***");
        
        System.out.println("\nAccount Name = " + accountName + "\n");
        
        // deposit
        System.out.println("Deposit Amount");

        System.out.print("Account Name: ");
        String depAccountName = scanner.nextLine();
        System.out.println("Current Balance: 1000.00");
        System.out.println("Currency: PHP");
        System.out.println();
        System.out.print("Deposit Amount: ");
        String depositAmount = scanner.nextLine();
        System.out.println();
        System.out.print("***");
        
        System.out.println("\nAccount Name = " + depAccountName);
        System.out.println("Deposit Amount = " + depositAmount + "\n");
        
        // withdraw
        System.out.println("Withdraw Amount");
        System.out.print("Account Name: ");
        String withAccountName = scanner.nextLine();
        System.out.println("Current Balance: 1000.00");
        System.out.println("Currency: PHP");
        System.out.println();
        System.out.print("Withdraw Amount: ");
        String withdrawAmount = scanner.nextLine();
        System.out.println();
        System.out.print("***");
        
        System.out.println("\nAccount Name = " + withAccountName);
        System.out.println("Withdraw Amount = " + withdrawAmount + "\n");
        
        // record
        System.out.println("Record Exchange Rate");
        System.out.println();
        System.out.println("Currencies:");
        System.out.println("[1] Philippine Peso (PHP)");
        System.out.println("[2] United States Dollar (USD)");
        System.out.println("[3] Japanese Yen (JPY)");
        System.out.println("[4] British Pound Sterling (GBP)");
        System.out.println("[5] Euro (EUR)");
        System.out.println("[6] Chinese Yuan Renminni (CNY)");
        System.out.println();
        

        System.out.print("Select Foreign Currency: ");
        String foreignCurrency = scanner.nextLine();
        System.out.print("Exchange Rate: ");
        String exchangeRate = scanner.nextLine();
        System.out.println();
        System.out.print("***");
        
        System.out.println("\nSelect Foreign Currency = " + foreignCurrency);
        System.out.println("Exchange Rate = " + exchangeRate + "\n");
        
        // currency
        System.out.println("Foreign Currency Exchange");
        System.out.print("Source Amount (PHP): ");
        double sourceAmount = Double.parseDouble(scanner.nextLine());
        System.out.println();
        
        System.out.println("Exchanged Currency");
        System.out.printf("[1] Philippine Peso (PHP) = %.2f\n", sourceAmount);
        System.out.printf("[2] United States Dollar (USD) = %.2f\n", (sourceAmount * 62.00));
        System.out.printf("[3] Japanese Yen (JPY) = %.2f\n", (sourceAmount * 0.40));
        System.out.printf("[4] British Pound Sterling (GBP) = %.2f\n", (sourceAmount * 84.00));
        System.out.printf("[5] Euro (EUR) = %.2f\n", (sourceAmount * 72.00));
        System.out.printf("[6] Chinese Yuan Renminni (CNY) = %.2f\n", (sourceAmount * 9.00));
        System.out.println();
        System.out.print("***");

        System.out.println("\nSource Currency = Philippine Peso (PHP)");
        System.out.printf("Source Amount (PHP) = %.2f\n", sourceAmount);
    }
}
