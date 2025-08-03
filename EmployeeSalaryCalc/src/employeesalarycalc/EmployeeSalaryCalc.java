
package employeesalarycalc;
import java.util.Scanner;

public class EmployeeSalaryCalc {

    public static void main(String[] args) {
        // The scanner object to collect the values from the input
        Scanner scanner = new Scanner(System.in);

        // Collecting input from the user
        System.out.print("Enter your first name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter your age: ");
        int age = scanner.nextInt();
        System.out.print("Enter your monthly salary: ");
        
        double monthlySalary = scanner.nextDouble();

        // Calculate weekly wage 
        double weeklyWage = monthlySalary / 4;

        // Display the result
        System.out.println("\nEmployee Information:");
        System.out.println("Name: " + name + " " + surname);
        System.out.println("Age: " + age);
        System.out.println("Monthly Salary: R" + monthlySalary);
        System.out.println("Estimated Weekly Wage: R" + weeklyWage);

    }
}



