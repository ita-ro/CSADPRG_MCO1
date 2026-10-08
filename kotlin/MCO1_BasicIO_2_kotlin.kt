//  ********************
//  Last names: Aya-ay, Bicomong
//  Language: kotlin
//  Paradigm(s): Functional, Imperative, Array-oriented, Object-oriented
//  ********************
import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)

    // main
    println("Select Transaction:")
    println("[1] Register Account Name")
    println("[2] Deposit Amount")
    println("[3] Withdraw Amount")
    println("[4] Currency Exchange")
    println("[5] Record Exchange Rates")
    println("[6] Show Interest Amount")
    println()


    print("Choice: ")
    val choice = scanner.nextLine()
    println()
    print("***")


    println("\nChoice = $choice\n")

    // register
    println("Register Account Name")
    print("Account Name: ")
    val accountName = scanner.nextLine()
    println()
    print("***")

    println("\nAccount Name = $accountName\n")

    // deposit
    println("Deposit Amount")
    print("Account Name: ")
    val depAccountName = scanner.nextLine()
    println("Current Balance: 1000.00")
    println("Currency: PHP")
    println()
    print("Deposit Amount: ")
    val depositAmount = scanner.nextLine()
    println()
    print("***")

    println("\nAccount Name = $depAccountName")
    println("Deposit Amount = .2f\n", depositAmount)

    // withdraw
    println("Withdraw Amount")
    print("Account Name: ")
    val withAccountName = scanner.nextLine()
    println("Current Balance: 1000.00")
    println("Currency: PHP")
    println()
    print("Withdraw Amount: ")
    val withdrawAmount = scanner.nextLine()
    println()
    print("***")

    println("\nAccount Name = $withAccountName")
    println("Withdraw Amount = .2f\n",withdrawAmount)

    // record
    println("Record Exchange Rate")
    println()
    println("Currencies:")
    println("[1] Philippine Peso (PHP)")
    println("[2] United States Dollar (USD)")
    println("[3] Japanese Yen (JPY)")
    println("[4] British Pound Sterling (GBP)")
    println("[5] Euro (EUR)")
    println("[6] Chinese Yuan Renminni (CNY)")
    println()

    print("Select Foreign Currency: ")
    val foreignCurrency = scanner.nextLine()
    print("Exchange Rate: ")
    val exchangeRate = scanner.nextLine()
    println()
    print("***")

    println("\nSelect Foreign Currency = $foreignCurrency")
    println("Exchange Rate = $exchangeRate\n")

    // currency
    println("Foreign Currency Exchange")
    print("Source Amount (PHP): ")
    val sourceAmountStr = scanner.nextLine()
    val sourceAmount = sourceAmountStr.toDoubleOrNull() ?: 0.0
    println()
    
    println("Exchanged Currency")
    System.out.printf("[1] Philippine Peso (PHP) = %.2f\n", sourceAmount)
    System.out.printf("[2] United States Dollar (USD) = %.2f\n", (sourceAmount * 62.00))
    System.out.printf("[3] Japanese Yen (JPY) = %.2f\n", (sourceAmount * 0.40))
    System.out.printf("[4] British Pound Sterling (GBP) = %.2f\n", (sourceAmount * 84.00))
    System.out.printf("[5] Euro (EUR) = %.2f\n", (sourceAmount * 72.00))
    System.out.printf("[6] Chinese Yuan Renminni (CNY) = %.2f\n", (sourceAmount * 9.00))
    println()
    print("***")

    println("\nSource Currency = Philippine Peso (PHP)")
    System.out.printf("Source Amount (PHP) = %.2f\n", sourceAmount)
}
