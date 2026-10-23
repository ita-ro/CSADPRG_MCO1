/*
********************
Last names: Aya-ay, Bicomong
Language: C
Paradigm(s): Procedural, Imperative, Structured
********************
*/

// Build: gcc MP_2_C.c -o MP_2_C.exe -lm

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <math.h>

#define LINE_LEN 256
#define MAX_DAYS 36500

static const char *CURRENCIES[] = {
    "Philippine Peso (PHP)", "United States Dollar (USD)", "Japanese Yen (JPY)",
    "British Pound Sterling (GBP)", "Euro (EUR)", "Chinese Yuan Renminni (CNY)"
};
static double rates[] = {1.00, 62.00, 0.40, 84.00, 72.00, 9.00};
static const double ANNUAL_INTEREST = 0.05;
static char accountName[LINE_LEN] = "";
static double balance = 0.00;

static char *readLine(const char *prompt) {
    static char buf[LINE_LEN];
    size_t n;
    fputs(prompt, stdout);
    if (!fgets(buf, sizeof buf, stdin)) {
        printf("\n");
        exit(0);
    }
    n = strlen(buf);
    while (n > 0 && isspace((unsigned char)buf[n - 1]))
        buf[--n] = '\0';
    return buf + strspn(buf, " \t");
}

static int readChoice(const char *prompt) {
    while (1) {
        char *end, *s = readLine(prompt);
        long v = strtol(s, &end, 10);
        if (end != s && *end == '\0' && v >= 1 && v <= 6)
            return (int)v;
        printf("Invalid input. Enter a number from 1 to 6.\n");
    }
}

static double readPositive(const char *prompt) {
    while (1) {
        char *end, *s = readLine(prompt);
        double v = strtod(s, &end);
        if (end != s && *end == '\0' && isfinite(v) && v > 0)
            return v;
        printf("Invalid input. Enter an amount greater than 0.\n");
    }
}

static int askYesNo(const char *prompt) {
    while (1) {
        char *s = readLine(prompt);
        if (strlen(s) == 1 && strchr("YyNn", s[0]))
            return toupper((unsigned char)s[0]) == 'Y';
        printf("Please enter Y or N.\n");
    }
}

static void printCurrencies(void) {
    for (int i = 0; i < 6; i++)
        printf("[%d] %s\n", i + 1, CURRENCIES[i]);
    printf("\n");
}

static int hasAccount(void) {
    if (accountName[0] == '\0')
        printf("No account registered yet. Please use [1] Register Account Name first.\n");
    return accountName[0] != '\0';
}

static double round2(double v) {
    return floor(v * 100.0 + 0.5) / 100.0;
}

static void printAccount(void) {
    printf("Account Name: %s\n", accountName);
    printf("Current Balance: %.2f\n", balance);
    printf("Currency: PHP\n");
}

static void registerAccount(void) {
    char *name;
    printf("\nRegister Account Name\n");
    do {
        name = readLine("Account Name: ");
    } while (name[0] == '\0');
    strcpy(accountName, name);
}

static void deposit(void) {
    printf("\nDeposit Amount\n");
    if (!hasAccount())
        return;
    printAccount();
    balance = round2(balance + readPositive("Deposit Amount: "));
    printf("Updated Balance: %.2f\n", balance);
}

static void withdraw(void) {
    double amount;
    printf("\nWithdraw Amount\n");
    if (!hasAccount())
        return;
    printAccount();
    amount = readPositive("Withdraw Amount: ");
    if (amount > balance) {
        printf("Insufficient balance. Withdrawal cancelled.\n");
        return;
    }
    balance = round2(balance - amount);
    printf("Updated Balance: %.2f\n", balance);
}

static void recordExchangeRate(void) {
    int choice;
    printf("\nRecord Exchange Rate\n");
    printCurrencies();
    choice = readChoice("Select Foreign Currency: ");
    if (choice == 1) {
        printf("PHP is the base currency; its rate is fixed at 1.00.\n");
        return;
    }
    rates[choice - 1] = readPositive("Exchange Rate: ");
}

static void currencyExchange(void) {
    do {
        printf("\nForeign Currency Exchange\nSource Currency Option:\n");
        printCurrencies();
        int src = readChoice("Source Currency: ");
        double amount = readPositive("Source Amount: ");
        printf("Exchanged Currency Options:\n");
        printCurrencies();
        int dst = readChoice("Exchange Currency: ");
        printf("Exchange Amount: %.2f\n", round2(amount * rates[src - 1] / rates[dst - 1]));
    } while (askYesNo("Convert another currency (Y/N)? "));
}

static void showInterest(void) {
    double v, running = balance;
    printf("\nShow Interest Amount\n");
    if (!hasAccount())
        return;
    printAccount();
    printf("Interest Rate: 5%%\n");

    while ((v = readPositive("Total Number of Days: ")) < 1 || v >= MAX_DAYS + 1)
        printf("Invalid input. Enter a number from 1 to %d.\n", MAX_DAYS);

    printf("Day | Interest | Balance |\n");
    for (int day = 1; day <= (int)v; day++) {
        double interest = round2(running * (ANNUAL_INTEREST / 365));
        running = round2(running + interest);
        printf("%d | %.2f | %.2f |\n", day, interest, running);
    }
}

int main(void) {
    do {
        printf("\nSelect Transaction:\n"
               "[1] Register Account Name\n"
               "[2] Deposit Amount\n"
               "[3] Withdraw Amount\n"
               "[4] Currency Exchange\n"
               "[5] Record Exchange Rates\n"
               "[6] Show Interest Amount\n\n");
        switch (readChoice("Choice: ")) {
            case 1: registerAccount();    break;
            case 2: deposit();            break;
            case 3: withdraw();           break;
            case 4: currencyExchange();   break;
            case 5: recordExchangeRate(); break;
            case 6: showInterest();       break;
        }
    } while (askYesNo("Back to the Main Menu (Y/N): "));
    return 0;
}
