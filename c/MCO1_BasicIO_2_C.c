/*
********************
Last names: Aya-ay, Bicomong
Language: C
Paradigm(s): Procedural, Imperative, Structured
********************
*/

// Run with:  gcc MCO1_BasicIO_2_C.c -o MCO1_BasicIO_2_C.exe

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define LINE_LEN 256
#define DEFAULT_BALANCE 1000.00
#define DEFAULT_CURRENCY "PHP"

static void readLine(char *buf, int size) {
    if (fgets(buf, size, stdin) == NULL) {
        buf[0] = '\0';
        return;
    }
    buf[strcspn(buf, "\r\n")] = '\0';
}

static double parseNumber(const char *line) {
    char *end;
    double value = strtod(line, &end);
    if (end == line)
        return 0.0;
    while (*end == ' ' || *end == '\t')
        end++;
    return (*end == '\0') ? value : 0.0;
}

static int readInt(void) {
    char line[LINE_LEN];
    double value;
    readLine(line, LINE_LEN);
    value = parseNumber(line);
    return (value == (int)value) ? (int)value : 0;
}

static double readDouble(void) {
    char line[LINE_LEN];
    readLine(line, LINE_LEN);
    return parseNumber(line);
}

int main(void) {
    char accountName[LINE_LEN];
    int choice, currency, i;
    double amount, rate;

    const char *currencies[] = {
        "Philippine Peso (PHP)",
        "United States Dollar (USD)",
        "Japanese Yen (JPY)",
        "British Pound Sterling (GBP)",
        "Euro (EUR)",
        "Chinese Yuan Renminni (CNY)"
    };
    const double rates[] = {1.00, 62.00, 0.40, 84.00, 72.00, 9.00};

    // Menu
    printf("Select Transaction:\n");
    printf("[1] Register Account Name\n");
    printf("[2] Deposit Amount\n");
    printf("[3] Withdraw Amount\n");
    printf("[4] Currency Exchange\n");
    printf("[5] Record Exchange Rates\n");
    printf("[6] Show Interest Amount\n\n");
    printf("Choice: ");
    choice = readInt();
    printf("\n***\nChoice = %d\n\n", choice);

    // Reg
    printf("Register Account Name\n");
    printf("Account Name: ");
    readLine(accountName, LINE_LEN);
    printf("\n***\nAccount Name = %s\n\n", accountName);

    // Dep
    printf("Deposit Amount\n");
    printf("Account Name: ");
    readLine(accountName, LINE_LEN);
    printf("Current Balance: %.2f\n", DEFAULT_BALANCE);
    printf("Currency: %s\n\n", DEFAULT_CURRENCY);
    printf("Deposit Amount: ");
    amount = readDouble();
    printf("\n***\nAccount Name = %s\nDeposit Amount = %.2f\n\n", accountName, amount);

    // Wit
    printf("Withdraw Amount\n");
    printf("Account Name: ");
    readLine(accountName, LINE_LEN);
    printf("Current Balance: %.2f\n", DEFAULT_BALANCE);
    printf("Currency: %s\n\n", DEFAULT_CURRENCY);
    printf("Withdraw Amount: ");
    amount = readDouble();
    printf("\n***\nAccount Name = %s\nWithdraw Amount = %.2f\n\n", accountName, amount);

    // Rec
    printf("Record Exchange Rate\n\n");
    printf("Currencies:\n");
    for (i = 0; i < 6; i++)
        printf("[%d] %s\n", i + 1, currencies[i]);
    printf("\nSelect Foreign Currency: ");
    currency = readInt();
    printf("Exchange Rate: ");
    rate = readDouble();
    printf("\n***\nSelect Foreign Currency = [%d]\nExchange Rate = %.2f\n\n", currency, rate);

    // Exch
    printf("Foreign Currency Exchange\n");
    printf("Source Amount (PHP): ");
    amount = readDouble();
    printf("\nExchanged Currency\n");
    for (i = 0; i < 6; i++)
        printf("[%d] %s = %.2f\n", i + 1, currencies[i], amount * rates[i]);
    printf("\n***\nSource Currency = %s\n", currencies[0]);
    printf("Source Amount (PHP) = %.2f\n", amount);

    return 0;
}
