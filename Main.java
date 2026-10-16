import java.io.*;
import java.util.*;

class Account {
    private int pin;
    private double balance;
    private ArrayList<String> transactions = new ArrayList<>();

    Account(int pin, double balance) {
        this.pin = pin;
        this.balance = balance;
    }

    boolean validatePin(int p) {
        return pin == p;
    }

    void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactions.add("Deposited: " + amount);
            System.out.println("Deposit Successful");
        } else {
            System.out.println("Invalid Amount");
        }
    }

    void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid Amount");
        } else if (amount <= balance) {
            balance -= amount;
            transactions.add("Withdrawn: " + amount);
            System.out.println("Withdrawal Successful");
        } else {
            System.out.println("Insufficient Balance");
        }
    }

    void checkBalance() {
        System.out.printf("Available Balance: %.2f%n", balance);
    }

    void showTransactions() {
        transactions.stream().forEach(System.out::println);
    }

    void saveTransactions() {
        try (PrintWriter out = new PrintWriter(new FileWriter("transactions.txt", true))) {
            transactions.forEach(out::println);
            System.out.println("Transactions Saved");
        } catch (IOException e) {
            System.out.println("Error Saving Transactions");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Account account = new Account(1234, 10000);
        int attempts = 0;

        while (attempts < 3) {
            System.out.print("Enter PIN: ");
            int pin = sc.nextInt();

            if (account.validatePin(pin)) {
                System.out.println("Login Successful");
                break;
            }

            attempts++;
            System.out.println("Incorrect PIN");
        }

        if (attempts == 3) {
            System.out.println("Account Locked");
            sc.close();
            return;
        }

        int choice;

        do {
            System.out.println("\n1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. View Transactions");
            System.out.println("5. Save Transactions");
            System.out.println("6. Exit");
            System.out.print("Enter Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    account.checkBalance();
                    break;
                case 2:
                    System.out.print("Enter Amount: ");
                    account.deposit(sc.nextDouble());
                    break;
                case 3:
                    System.out.print("Enter Amount: ");
                    account.withdraw(sc.nextDouble());
                    break;
                case 4:
                    account.showTransactions();
                    break;
                case 5:
                    account.saveTransactions();
                    break;
                case 6:
                    System.out.println("Thank You for Using ATM");
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        } while (choice != 6);

        sc.close();
    }
}