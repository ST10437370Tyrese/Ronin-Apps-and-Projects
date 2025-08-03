
package arithmeticcalc;
import java.util.Scanner;

public class ArithmeticCalc {
    public static void main(String[] args) {
        // scanner object to collect input
        Scanner scanner = new Scanner(System.in);
        
        
        // Accepting two numbers
        System.out.println("Enter the first number:");
        double num1 = scanner.nextDouble();
        System.out.println("Enter the second number");
        double num2 = scanner.nextDouble();
        
        //perform arithmetic calculations
        System.out.println("Addidtion" + (num1 + num2));
        System.out.println("Subtraction" + (num1 - num2));
        System.out.println("Multiplication" + (num1 * num2));
        System.out.println("Division" + (num1 / num2));
        
        
        
    }
    
}
