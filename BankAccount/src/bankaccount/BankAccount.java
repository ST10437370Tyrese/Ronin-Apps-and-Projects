package bankaccount;
import java.util.Scanner;

public class BankAccount {
    // methods
    private double balance;

    // constructor to set initial balance
    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
        System.out.println("New bank account created with balance: R" + balance);
    }

    // method to deposit money
    public void deposit(double amount) {
        this.balance += amount;
        System.out.println("Deposited: R" + amount);
        checkBalance();
    }

    public void withdraw(double amount) {
        if (amount <= this.balance) {
            this.balance -= amount;
            System.out.println("Withdrawal: R" + amount);
            checkBalance();
        } else {
            System.out.println("Insufficient funds for withdrawal of: R" + amount);
            checkBalance();
        }
    }

    // method to check current balance
    public void checkBalance() {
        System.out.println("Current Balance: R" + balance);
    }

    // New method: printStatement (you'll need to implement this)
    public void printStatement() {
        System.out.println("\n--- Account Statement ---");
        System.out.println("Current Balance: R" + this.balance);
        // You might want to add details of deposits and withdrawals here
        System.out.println("-----------------------");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // step 1: create account
        System.out.println("Please enter Initial Balance: ");
        double initialBalance = scanner.nextDouble();
        BankAccount account = new BankAccount(initialBalance);

        // step 2: create three deposits
        System.out.println("Please enter the first deposit amount");
        double deposit1 = scanner.nextDouble();
        account.deposit(deposit1);

        System.out.println("Please enter the second deposit amount");
        double deposit2 = scanner.nextDouble();
        account.deposit(deposit2);

        System.out.println("Please enter the third deposit amount");
        double deposit3 = scanner.nextDouble();
        account.deposit(deposit3);

        // step 3: create three withdrawals
        System.out.println("Please enter your first withdrawal amount: ");
        double withdrawal1 = scanner.nextDouble();
        account.withdraw(withdrawal1);

        System.out.println("Please enter your second withdrawal amount: ");
        double withdrawal2 = scanner.nextDouble();
        account.withdraw(withdrawal2);

        System.out.println("Please enter your third withdrawal amount: ");
        double withdrawal3 = scanner.nextDouble();
        account.withdraw(withdrawal3);

        // step 4: final statement
        account.printStatement();

        scanner.close();
    }
}