# ********************
# Last names: Aya-ay, Bicomong
# Language: R
# Paradigm(s): Functional, Imperative, Array-oriented, Object-oriented
# ********************

# Run with:  Rscript MCO1_BasicIO_2_R.R

con <- file("stdin")
open(con)

CURRENCIES <- c("Philippine Peso (PHP)",
                "United States Dollar (USD)",
                "Japanese Yen (JPY)",
                "British Pound Sterling (GBP)",
                "Euro (EUR)",
                "Chinese Yuan Renminni (CNY)")
rates <- c(1.00, 62.00, 0.40, 84.00, 72.00, 9.00)
ANNUAL_INTEREST <- 0.05
MAX_DAYS <- 36500

account_name <- NULL
balance <- 0.00

read_input <- function(prompt) {
  cat(prompt)
  if (interactive()) {
    line <- readline()
  } else {
    line <- readLines(con, n = 1, warn = FALSE)
    if (length(line) == 0) {
      cat("\n")
      quit(save = "no", status = 0)
    }
  }
  trimws(line)
}

read_choice <- function(prompt) {
  repeat {
    s <- read_input(prompt)
    if (grepl("^[+-]?[0-9]+$", s)) {
      v <- suppressWarnings(as.numeric(s))
      if (!is.na(v) && v >= 1 && v <= 6) return(as.integer(v))
    }
    cat("Invalid input. Enter a number from 1 to 6.\n")
  }
}

is_positive <- function(prompt) {
  repeat {
    s <- read_input(prompt)
    if (grepl("^[0-9.+eE-]+$", s)) {
      v <- suppressWarnings(as.numeric(s))
      if (!is.na(v) && is.finite(v) && v > 0) return(v)
    }
    cat("Invalid input. Enter an amount greater than 0.\n")
  }
}

is_y_or_n <- function(prompt) {
  repeat {
    s <- read_input(prompt)
    if (toupper(s) == "Y") return(TRUE)
    if (toupper(s) == "N") return(FALSE)
    cat("Please enter Y or N.\n")
  }
}

print_currencies <- function() {
  for (i in seq_along(CURRENCIES)) {
    cat(sprintf("[%d] %s\n", i, CURRENCIES[i]))
  }
  cat("\n")
}

require_account <- function() {
  if (is.null(account_name)) {
    cat("No account registered yet. Please use [1] Register Account Name first.\n")
    return(FALSE)
  }
  TRUE
}

round2 <- function(v) floor(v * 100 + 0.5) / 100

register_account <- function() {
  cat("\nRegister Account Name\n")
  repeat {
    name <- read_input("Account Name: ")
    if (nchar(name) > 0) break
  }
  account_name <<- name
}

deposit <- function() {
  cat("\nDeposit Amount\n")
  if (!require_account()) return(invisible(NULL))
  cat(sprintf("Account Name: %s\n", account_name))
  cat(sprintf("Current Balance: %.2f\n", balance))
  cat("Currency: PHP\n")

  amount <- is_positive("Deposit Amount: ")
  balance <<- round2(balance + amount)

  cat(sprintf("Updated Balance: %.2f\n", balance))
}

withdraw <- function() {
  cat("\nWithdraw Amount\n")
  if (!require_account()) return(invisible(NULL))
  cat(sprintf("Account Name: %s\n", account_name))
  cat(sprintf("Current Balance: %.2f\n", balance))
  cat("Currency: PHP\n")
  amount <- is_positive("Withdraw Amount: ")

  if (amount > balance) {
    cat("Insufficient balance. Withdrawal cancelled.\n")
    return(invisible(NULL))
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
    return(invisible(NULL))
  }

  rates[choice] <<- is_positive("Exchange Rate: ")
}

currency_exchange <- function() {
  repeat {
    cat("\nForeign Currency Exchange\n")
    cat("Source Currency Option:\n")
    print_currencies()

    src <- read_choice("Source Currency: ")
    amount <- is_positive("Source Amount: ")
    cat("Exchanged Currency Options:\n")
    print_currencies()

    dst <- read_choice("Exchange Currency: ")
    result <- amount * rates[src] / rates[dst]
    cat(sprintf("Exchange Amount: %.2f\n", round2(result)))

    if (!is_y_or_n("Convert another currency (Y/N)? ")) break
  }
}

show_interest <- function() {
  cat("\nShow Interest Amount\n")
  if (!require_account()) return(invisible(NULL))

  cat(sprintf("Account Name: %s\n", account_name))
  cat(sprintf("Current Balance: %.2f\n", balance))
  cat("Currency: PHP\n")
  cat("Interest Rate: 5%\n")

  repeat {
    v <- is_positive("Total Number of Days: ")
    if (v <= MAX_DAYS && trunc(v) >= 1) {
      days <- as.integer(trunc(v))
      break
    }
    cat(sprintf("Invalid input. Enter a number from 1 to %d.\n", as.integer(MAX_DAYS)))
  }

  cat("Day | Interest | Balance |\n")
  running <- balance

  for (d in seq_len(days)) {
    interest <- round2(running * (ANNUAL_INTEREST / 365))
    running <- round2(running + interest)
    cat(sprintf("%d | %.2f | %.2f |\n", d, interest, running))
  }
}

repeat {
  cat("\nSelect Transaction:\n")
  cat("[1] Register Account Name\n")
  cat("[2] Deposit Amount\n")
  cat("[3] Withdraw Amount\n")
  cat("[4] Currency Exchange\n")
  cat("[5] Record Exchange Rates\n")
  cat("[6] Show Interest Amount\n")
  cat("\n")
  switch(read_choice("Choice: "),
         register_account(),
         deposit(),
         withdraw(),
         currency_exchange(),
         record_exchange_rate(),
         show_interest())
  if (!is_y_or_n("Back to the Main Menu (Y/N): ")) break
}

close(con)
