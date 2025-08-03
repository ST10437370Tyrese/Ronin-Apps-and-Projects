package simplecalculator;
import java.util.Scanner;

public class SimpleCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose an operation:");
        System.out.println("1: Add");
        System.out.println("2: Subtract");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        System.out.print("Enter first number: ");
        double num1 = scanner.nextDouble();
        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();
        // Using switch
        switch (choice) {
            case 1:               
                System.out.println("Result: " + (num1 + num2));
                break;
            case 2:
                System.out.println("Result: " + (num1 - num2));
                break;
            default:               
                System.out.println("Invalid option selected.");
        }
        scanner.close();
    }
}
