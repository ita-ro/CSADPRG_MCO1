# ********************
# Last names: Aya-ay, Bicomong
# Language: R
# Paradigm(s): Functional, Imperative, Array-oriented, Object-oriented
# ********************

# Run with:  Rscript MCO1_BasicIO_2_R.R

con <- file("stdin")
open(con)

DEFAULT_BALANCE  <- 1000.00
DEFAULT_CURRENCY <- "PHP"

read_line <- function() {
  if (interactive()) {
    line <- readline()
  } else {
    line <- readLines(con, n = 1, warn = FALSE)
    if (length(line) == 0) line <- ""
  }
  line
}

parse_number <- function(line) {
  value <- suppressWarnings(as.numeric(trimws(line)))
  if (is.na(value) || !is.finite(value)) 0 else value
}

read_int <- function() {
  value <- parse_number(read_line())
  if (value == floor(value)) as.integer(value) else 0L
}

read_double <- function() parse_number(read_line())

currencies <- c("Philippine Peso (PHP)",
                "United States Dollar (USD)",
                "Japanese Yen (JPY)",
                "British Pound Sterling (GBP)",
                "Euro (EUR)",
                "Chinese Yuan Renminni (CNY)")
rates <- c(1.00, 62.00, 0.40, 84.00, 72.00, 9.00)

# Menu
cat("Select Transaction:\n")
cat("[1] Register Account Name\n")
cat("[2] Deposit Amount\n")
cat("[3] Withdraw Amount\n")
cat("[4] Currency Exchange\n")
cat("[5] Record Exchange Rates\n")
cat("[6] Show Interest Amount\n\n")
cat("Choice: ")
choice <- read_int()
cat(sprintf("\n***\nChoice = %d\n\n", choice))

# Reg
cat("Register Account Name\n")
cat("Account Name: ")
account_name <- read_line()
cat(sprintf("\n***\nAccount Name = %s\n\n", account_name))

# Dep
cat("Deposit Amount\n")
cat("Account Name: ")
account_name <- read_line()
cat(sprintf("Current Balance: %.2f\n", DEFAULT_BALANCE))
cat(sprintf("Currency: %s\n\n", DEFAULT_CURRENCY))
cat("Deposit Amount: ")
amount <- read_double()
cat(sprintf("\n***\nAccount Name = %s\nDeposit Amount = %.2f\n\n", account_name, amount))

# Wit
cat("Withdraw Amount\n")
cat("Account Name: ")
account_name <- read_line()
cat(sprintf("Current Balance: %.2f\n", DEFAULT_BALANCE))
cat(sprintf("Currency: %s\n\n", DEFAULT_CURRENCY))
cat("Withdraw Amount: ")
amount <- read_double()
cat(sprintf("\n***\nAccount Name = %s\nWithdraw Amount = %.2f\n\n", account_name, amount))

# Rec
cat("Record Exchange Rate\n\n")
cat("Currencies:\n")
for (i in seq_along(currencies)) {
  cat(sprintf("[%d] %s\n", i, currencies[i]))
}
cat("\nSelect Foreign Currency: ")
currency <- read_int()
cat("Exchange Rate: ")
rate <- read_double()
cat(sprintf("\n***\nSelect Foreign Currency = [%d]\nExchange Rate = %.2f\n\n", currency, rate))

# Exch
cat("Foreign Currency Exchange\n")
cat("Source Amount (PHP): ")
amount <- read_double()
cat("\nExchanged Currency\n")
for (i in seq_along(currencies)) {
  cat(sprintf("[%d] %s = %.2f\n", i, currencies[i], amount * rates[i]))
}
cat(sprintf("\n***\nSource Currency = %s\n", currencies[1]))
cat(sprintf("Source Amount (PHP) = %.2f\n", amount))

close(con)
