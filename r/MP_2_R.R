# ********************
# Last names: Aya-ay, Bicomong
# Language: R
# Paradigm(s): Functional, Imperative, Array-oriented, Object-oriented
# ********************

# Run with:  Rscript MP_2_R.R

con <- file("stdin")
open(con)

CURRENCIES <- c("Philippine Peso (PHP)", "United States Dollar (USD)",
                "Japanese Yen (JPY)", "British Pound Sterling (GBP)",
                "Euro (EUR)", "Chinese Yuan Renminni (CNY)")
rates <- c(1.00, 62.00, 0.40, 84.00, 72.00, 9.00)
ANNUAL_INTEREST <- 0.05
MAX_DAYS <- 36500
account_name <- ""
balance <- 0.00

read_input <- function(prompt) {
  cat(prompt)
  line <- readLines(con, n = 1, warn = FALSE)
  if (length(line) == 0) {
    cat("\n")
    quit(save = "no")
  }
  trimws(line)
}

read_choice <- function(prompt) {
  repeat {
    s <- read_input(prompt)
    if (s %in% 1:6) return(as.integer(s))
    cat("Invalid input. Enter a number from 1 to 6.\n")
  }
}

read_positive <- function(prompt) {
  repeat {
    v <- suppressWarnings(as.numeric(read_input(prompt)))
    if (is.finite(v) && v > 0) return(v)
    cat("Invalid input. Enter an amount greater than 0.\n")
  }
}

ask_yes_no <- function(prompt) {
  repeat {
    s <- toupper(read_input(prompt))
    if (s %in% c("Y", "N")) return(s == "Y")
    cat("Please enter Y or N.\n")
  }
}

print_currencies <- function() {
  cat(paste0("[", 1:6, "] ", CURRENCIES, "\n"), "\n", sep = "")
}

has_account <- function() {
  if (account_name == "")
    cat("No account registered yet. Please use [1] Register Account Name first.\n")
  account_name != ""
}

round2 <- function(v) floor(v * 100 + 0.5) / 100

print_account <- function() {
  cat(sprintf("Account Name: %s\nCurrent Balance: %.2f\nCurrency: PHP\n",
              account_name, balance))
}

register_account <- function() {
  cat("\nRegister Account Name\n")
  repeat {
    name <- read_input("Account Name: ")
    if (nzchar(name)) break
  }
  account_name <<- name
}

deposit <- function() {
  cat("\nDeposit Amount\n")
  if (!has_account()) return()
  print_account()
  balance <<- round2(balance + read_positive("Deposit Amount: "))
  cat(sprintf("Updated Balance: %.2f\n", balance))
}

withdraw <- function() {
  cat("\nWithdraw Amount\n")
  if (!has_account()) return()
  print_account()
  amount <- read_positive("Withdraw Amount: ")
  if (amount > balance) {
    cat("Insufficient balance. Withdrawal cancelled.\n")
    return()
  }
  balance <<- round2(balance - amount)
  cat(sprintf("Updated Balance: %.2f\n", balance))
}

record_exchange_rate <- function() {
  cat("\nRecord Exchange Rate\n")
  print_currencies()
  choice <- read_choice("Select Foreign Currency: ")
  if (choice == 1) {
    cat("PHP is the base currency; its rate is fixed at 1.00.\n")
    return()
  }
  rates[choice] <<- read_positive("Exchange Rate: ")
}

currency_exchange <- function() {
  repeat {
    cat("\nForeign Currency Exchange\nSource Currency Option:\n")
    print_currencies()
    src <- read_choice("Source Currency: ")
    amount <- read_positive("Source Amount: ")
    cat("Exchanged Currency Options:\n")
    print_currencies()
    dst <- read_choice("Exchange Currency: ")
    cat(sprintf("Exchange Amount: %.2f\n", round2(amount * rates[src] / rates[dst])))
    if (!ask_yes_no("Convert another currency (Y/N)? ")) break
  }
}

show_interest <- function() {
  cat("\nShow Interest Amount\n")
  if (!has_account()) return()
  print_account()
  cat("Interest Rate: 5%\n")

  repeat {
    days <- read_positive("Total Number of Days: ")
    if (days >= 1 && days < MAX_DAYS + 1) break
    cat(sprintf("Invalid input. Enter a number from 1 to %d.\n", MAX_DAYS))
  }

  cat("Day | Interest | Balance |\n")
  running <- balance
  for (day in seq_len(as.integer(days))) {
    interest <- round2(running * (ANNUAL_INTEREST / 365))
    running <- round2(running + interest)
    cat(sprintf("%d | %.2f | %.2f |\n", day, interest, running))
  }
}

repeat {
  cat("\nSelect Transaction:\n",
      "[1] Register Account Name\n",
      "[2] Deposit Amount\n",
      "[3] Withdraw Amount\n",
      "[4] Currency Exchange\n",
      "[5] Record Exchange Rates\n",
      "[6] Show Interest Amount\n\n", sep = "")
  switch(read_choice("Choice: "),
         register_account(),
         deposit(),
         withdraw(),
         currency_exchange(),
         record_exchange_rate(),
         show_interest())
  if (!ask_yes_no("Back to the Main Menu (Y/N): ")) break
}

close(con)
