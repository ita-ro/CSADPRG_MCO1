//  ********************
//  Last names: Aya-ay, Bicomong
//  Language: kotlin
//  Paradigm(s): Functional, Imperative, Object-oriented
//  ********************

// Build: kotlinc MP_2_kotlin.kt -include-runtime -d MP_2_kotlin.jar 
// Run: java -jar MP_2_kotlin.jar

import java.util.Scanner

val scanner = Scanner(System.`in`)
val NAMES = listOf(
    "Philippine Peso (PHP)", "United States Dollar (USD)", "Japanese Yen (JPY)",
    "British Pound Sterling (GBP)", "Euro (EUR)", "Chinese Yuan Renminni (CNY)"
)
val rates = doubleArrayOf(1.00, 62.00, 0.40, 84.00, 72.00, 9.00)
const val ANNUAL_INTEREST = 0.05

var accountName: String? = null
var balance = 0.00

// validators
fun readInput(prompt: String): String {
    print(prompt)
    return scanner.nextLine().trim()
}

fun readChoice(prompt: String): Int {
    while (true) {
        val v = readInput(prompt).toIntOrNull()

        if (v != null && v in 1..6) {
            return v
        }

        println("Invalid input. Enter a number from 1 to 6.")
    }
}

fun isPositive(prompt: String): Double {
    while (true) {
        val v = readInput(prompt).toDoubleOrNull()

        if (v != null && v > 0) {
            return v
        }

        println("Invalid input. Enter an amount greater than 0.")
    }
}

fun isYorN(prompt: String): Boolean {
    while (true) {
        when (readInput(prompt).uppercase()) {
            "Y" -> return true
            "N" -> return false
        }
        println("Please enter Y or N.")
    }
}

fun printCurrencies() {
    NAMES.forEachIndexed { i, n -> println("[${i + 1}] $n") }
    println()
}

fun hasAccount(): Boolean {

    if (accountName == null) {
        println("No account registered yet. Please use [1] Register Account Name first.")
        return false
    }

    return true
}

fun round2(v: Double) = Math.round(v * 100.0) / 100.0

// transactions
fun registerAccount() {
    println("\nRegister Account Name")
    var name: String
    do { name = readInput("Account Name: ") } while (name.isEmpty())
    accountName = name
}

fun deposit() {
    println("\nDeposit Amount")

    if (!hasAccount()) {
        return
    }

    println("Account Name: $accountName")
    System.out.printf("Current Balance: %.2f\n", balance)
    println("Currency: PHP")
    val amount = isPositive("Deposit Amount: ")

    balance = round2(balance + amount)
    System.out.printf("Updated Balance: %.2f\n", balance)
}

fun withdraw() {
    println("\nWithdraw Amount")

    if (!hasAccount()) {
        return
    }

    println("Account Name: $accountName")
    System.out.printf("Current Balance: %.2f\n", balance)
    println("Currency: PHP")
    val amount = isPositive("Withdraw Amount: ")

    if (amount > balance) {
        println("Insufficient balance. Withdrawal cancelled.")
        return
    }

    balance = round2(balance - amount)
    System.out.printf("Updated Balance: %.2f\n", balance)
}

fun recordExchangeRate() {
    println("\nRecord Exchange Rate")
    printCurrencies()
    val choice = readChoice("Select Foreign Currency: ")

    if (choice == 1) {
        println("PHP is the base currency; its rate is fixed at 1.00.")
        return
    }

    rates[choice - 1] = isPositive("Exchange Rate: ")
}

fun currencyExchange() {
    do {
        println("\nForeign Currency Exchange")
        println("Source Currency Option:")
        printCurrencies()

        val src = readChoice("Source Currency: ")
        val amount = isPositive("Source Amount: ")
        println("Exchanged Currency Options:")
        printCurrencies()

        val choice = readChoice("Exchange Currency: ")
        val result = amount * rates[src - 1] / rates[choice - 1]
        System.out.printf("Exchange Amount: %.2f\n", result)
    } while (isYorN("Convert another currency (Y/N)? "))
}

fun showInterest() {
    println("\nShow Interest Amount")

    if (!hasAccount()) {
        return
    }

    println("Account Name: $accountName")
    System.out.printf("Current Balance: %.2f\n", balance)
    println("Currency: PHP")
    println("Interest Rate: 5%")
    var days: Int

    while (true) {
        days = isPositive("Total Number of Days: ").toInt()

        if (days >= 1 && days <= 36500) {
            break
        }

        println("Invalid input. Enter a number from 1 to 36500.")
    }

    println("Day | Interest | Balance |")
    var running = balance

    for (d in 1..days) {
        val interest = round2(running * (ANNUAL_INTEREST / 365))
        running = round2(running + interest)
        System.out.printf("%d | %.2f | %.2f |\n", d, interest, running)
    }
    
}

fun main() {
    do {
        println("\nSelect Transaction:")
        println("[1] Register Account Name")
        println("[2] Deposit Amount")
        println("[3] Withdraw Amount")
        println("[4] Currency Exchange")
        println("[5] Record Exchange Rates")
        println("[6] Show Interest Amount")
        println()
        when (readChoice("Choice: ")) {
            1 -> registerAccount()
            2 -> deposit()
            3 -> withdraw()
            4 -> currencyExchange()
            5 -> recordExchangeRate()
            6 -> showInterest()
        }
    } while (isYorN("Back to the Main Menu (Y/N): "))
}
