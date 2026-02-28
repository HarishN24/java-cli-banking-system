package com.consolebased;

import java.util.ArrayList;
import java.util.Scanner;

class Account {
    private int accountNumber;
    private String name;
    private int pin;
    private double balance;
    private ArrayList<String> transactions;

    public Account(int accountNumber, String name, int pin, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.pin = pin;
        this.balance = balance;
        this.transactions = new ArrayList<>();
        transactions.add("Account created with balance: " + balance);
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public boolean validatePin(int inputPin) {
        return this.pin == inputPin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        balance += amount;
        transactions.add("Deposited: " + amount);
        System.out.println("Deposit successful.");
    }

    public void withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            System.out.println("Invalid or insufficient balance.");
            return;
        }

        balance -= amount;
        transactions.add("Withdrawn: " + amount);
        System.out.println("Withdrawal successful.");
    }

    public void transfer(Account receiver, double amount) {
        if (amount <= 0 || amount > balance) {
            System.out.println("Invalid or insufficient balance.");
            return;
        }

        balance -= amount;
        receiver.balance += amount;

        transactions.add("Transferred " + amount + " to Acc: " + receiver.accountNumber);
        receiver.transactions.add("Received " + amount + " from Acc: " + accountNumber);

        System.out.println("Transfer successful.");
    }

    public void showTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("Transaction History:");
        for (String t : transactions) {
            System.out.println(t);
        }
    }
}

public class BankingApplication {

    private static Scanner sc = new Scanner(System.in);
    private static ArrayList<Account> accounts = new ArrayList<>();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== BANKING SYSTEM =====");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Thank you for using our bank.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void createAccount() {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        for (Account acc : accounts) {
            if (acc.getAccountNumber() == accNo) {
                System.out.println("Account number already exists!");
                return;
            }
        }

        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Set PIN: ");
        int pin = sc.nextInt();

        System.out.print("Initial Deposit: ");
        double balance = sc.nextDouble();

        accounts.add(new Account(accNo, name, pin, balance));
        System.out.println("Account created successfully.");
    }

    private static void login() {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        System.out.print("Enter PIN: ");
        int pin = sc.nextInt();

        Account user = null;

        for (Account acc : accounts) {
            if (acc.getAccountNumber() == accNo && acc.validatePin(pin)) {
                user = acc;
                break;
            }
        }

        if (user == null) {
            System.out.println("Invalid account number or PIN.");
            return;
        }

        System.out.println("Login successful. Welcome " + user.getName());

        int choice;
        do {
            System.out.println("\n1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Transfer");
            System.out.println("5. Transaction History");
            System.out.println("6. Logout");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter amount: ");
                    user.deposit(sc.nextDouble());
                    break;

                case 2:
                    System.out.print("Enter amount: ");
                    user.withdraw(sc.nextDouble());
                    break;

                case 3:
                    System.out.println("Current Balance: " + user.getBalance());
                    break;

                case 4:
                    transfer(user);
                    break;

                case 5:
                    user.showTransactions();
                    break;

                case 6:
                    System.out.println("Logged out.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 6);
    }

    private static void transfer(Account sender) {
        System.out.print("Enter Receiver Account Number: ");
        int receiverAccNo = sc.nextInt();

        Account receiver = null;

        for (Account acc : accounts) {
            if (acc.getAccountNumber() == receiverAccNo) {
                receiver = acc;
                break;
            }
        }

        if (receiver == null) {
            System.out.println("Receiver account not found.");
            return;
        }

        System.out.print("Enter Amount to Transfer: ");
        double amount = sc.nextDouble();

        sender.transfer(receiver, amount);
    }
}