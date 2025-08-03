
package salarycalc;
import java.util.Scanner;

public class SalaryCalc {
    public static void main(String[] args) {
       // Define the hourly rate
        final double hourlyRate = 50.0;
        
        // Create a Scanner object for user input
        Scanner scanner = new Scanner(System.in);
        
        // Ask the user for the number of hours worked
        System.out.println("Enter the number of hours worked this week: ");
        int hoursWorked = scanner.nextInt();
        
        // Calculate the total salary
        double totalSalary = hoursWorked * hourlyRate;
        
        // Display the total salary
        System.out.println("Total weekly salary: R" + totalSalary);
        
    }
    
}
