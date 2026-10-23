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
#include <math.h>

#define LINE_LEN 256
#define NUM_CURRENCIES 6
#define MAX_DAYS 36500

static const char *CURRENCIES[NUM_CURRENCIES] = {
    "Philippine Peso (PHP)",
    "United States Dollar (USD)",
    "Japanese Yen (JPY)",
    "British Pound Sterling (GBP)",
    "Euro (EUR)",
    "Chinese Yuan Renminni (CNY)"
};
static double rates[NUM_CURRENCIES] = {1.00, 62.00, 0.40, 84.00, 72.00, 9.00};
static const double ANNUAL_INTEREST = 0.05;

static char accountName[LINE_LEN];
static int accountFlag = 0;
static double balance = 0.00;

static void readInput(const char *prompt, char *out) {
    char buf[LINE_LEN];
    char *start, *end;
    size_t len;

    fputs(prompt, stdout);
    fflush(stdout);
    if (fgets(buf, sizeof buf, stdin) == NULL) {
        printf("\n");
        exit(0);
    }
    len = strlen(buf);
    if (len == 0 || buf[len - 1] != '\n') {
        int c;
        while ((c = getchar()) != '\n' && c != EOF)
            ;
    }
    start = buf;
    while (*start != '\0' && (unsigned char)*start <= ' ')
        start++;
    end = start + strlen(start);
    while (end > start && (unsigned char)end[-1] <= ' ')
        end--;
    *end = '\0';
    memmove(out, start, (size_t)(end - start) + 1);
}

static int readChoice(const char *prompt) {
    char s[LINE_LEN];
    while (1) {
        const char *p;
        int ok;
        readInput(prompt, s);
        p = s;
        if (*p == '+' || *p == '-')
            p++;
        ok = (*p != '\0');
        for (; *p != '\0'; p++)
            if (*p < '0' || *p > '9')
                ok = 0;
        if (ok) {
            long v = strtol(s, NULL, 10);
            if (v >= 1 && v <= 6)
                return (int)v;
        }
        printf("Invalid input. Enter a number from 1 to 6.\n");
    }
}

static double isPositive(const char *prompt) {
    char s[LINE_LEN];
    while (1) {
        const char *p;
        int ok;
        readInput(prompt, s);
        ok = (s[0] != '\0');
        for (p = s; *p != '\0'; p++)
            if (!((*p >= '0' && *p <= '9') || *p == '.' || *p == '+' ||
                  *p == '-' || *p == 'e' || *p == 'E'))
                ok = 0;
        if (ok) {
            char *end;
            double v = strtod(s, &end);
            if (*end == '\0' && isfinite(v) && v > 0)
                return v;
        }
        printf("Invalid input. Enter an amount greater than 0.\n");
    }
}

static int isYorN(const char *prompt) {
    char s[LINE_LEN];
    while (1) {
        readInput(prompt, s);
        if (s[0] != '\0' && s[1] == '\0') {
            if (s[0] == 'Y' || s[0] == 'y')
                return 1;
            if (s[0] == 'N' || s[0] == 'n')
                return 0;
        }
        printf("Please enter Y or N.\n");
    }
}

static void printCurrencies(void) {
    int i;
    for (i = 0; i < NUM_CURRENCIES; i++)
        printf("[%d] %s\n", i + 1, CURRENCIES[i]);
    printf("\n");
}

static int hasAccount(void) {
    if (!accountFlag) {
        printf("No account registered yet. Please use [1] Register Account Name first.\n");
        return 0;
    }
    return 1;
}

static double round2(double v) {
    return floor(v * 100.0 + 0.5) / 100.0;
}

static void registerAccount(void) {
    char name[LINE_LEN];
    printf("\nRegister Account Name\n");
    do {
        readInput("Account Name: ", name);
    } while (name[0] == '\0');
    strcpy(accountName, name);
    accountFlag = 1;
}

static void deposit(void) {
    double amount;
    printf("\nDeposit Amount\n");
    if (!hasAccount())
        return;
    printf("Account Name: %s\n", accountName);
    printf("Current Balance: %.2f\n", balance);
    printf("Currency: PHP\n");

    amount = isPositive("Deposit Amount: ");
    balance = round2(balance + amount);

    printf("Updated Balance: %.2f\n", balance);
}

static void withdraw(void) {
    double amount;
    printf("\nWithdraw Amount\n");
    if (!hasAccount())
        return;
    printf("Account Name: %s\n", accountName);
    printf("Current Balance: %.2f\n", balance);
    printf("Currency: PHP\n");
    amount = isPositive("Withdraw Amount: ");

    if (amount > balance) {
        printf("Insufficient balance. Withdrawal cancelled.\n");
        return;
    }

    balance = round2(balance - amount);
    printf("Updated Balance: %.2f\n", balance);
}

static void recordExchangeRate(void) {
    int c;
    printf("\nRecord Exchange Rate\n");
    printCurrencies();
    c = readChoice("Select Foreign Currency: ");

    if (c == 1) {
        printf("PHP is the base currency; its rate is fixed at 1.00.\n");
        return;
    }

    rates[c - 1] = isPositive("Exchange Rate: ");
}

static void currencyExchange(void) {
    do {
        int src, dst;
        double amount, result;
        printf("\nForeign Currency Exchange\n");
        printf("Source Currency Option:\n");
        printCurrencies();

        src = readChoice("Source Currency: ");
        amount = isPositive("Source Amount: ");
        printf("Exchanged Currency Options:\n");
        printCurrencies();

        dst = readChoice("Exchange Currency: ");
        result = amount * rates[src - 1] / rates[dst - 1];
        printf("Exchange Amount: %.2f\n", round2(result));
    } while (isYorN("Convert another currency (Y/N)? "));
}

static void showInterest(void) {
    int days, d;
    double running;
    printf("\nShow Interest Amount\n");

    if (!hasAccount())
        return;

    printf("Account Name: %s\n", accountName);
    printf("Current Balance: %.2f\n", balance);
    printf("Currency: PHP\n");
    printf("Interest Rate: 5%%\n");

    while (1) {
        double v = isPositive("Total Number of Days: ");
        if (v <= MAX_DAYS && (int)v >= 1) {
            days = (int)v;
            break;
        }
        printf("Invalid input. Enter a number from 1 to %d.\n", MAX_DAYS);
    }

    printf("Day | Interest | Balance |\n");
    running = balance;

    for (d = 1; d <= days; d++) {
        double interest = round2(running * (ANNUAL_INTEREST / 365));
        running = round2(running + interest);
        printf("%d | %.2f | %.2f |\n", d, interest, running);
    }
}

int main(void) {
    do {
        printf("\nSelect Transaction:\n");
        printf("[1] Register Account Name\n");
        printf("[2] Deposit Amount\n");
        printf("[3] Withdraw Amount\n");
        printf("[4] Currency Exchange\n");
        printf("[5] Record Exchange Rates\n");
        printf("[6] Show Interest Amount\n");
        printf("\n");
        switch (readChoice("Choice: ")) {
            case 1: registerAccount();     break;
            case 2: deposit();             break;
            case 3: withdraw();            break;
            case 4: currencyExchange();    break;
            case 5: recordExchangeRate();  break;
            case 6: showInterest();        break;
        }
    } while (isYorN("Back to the Main Menu (Y/N): "));

    return 0;
}
