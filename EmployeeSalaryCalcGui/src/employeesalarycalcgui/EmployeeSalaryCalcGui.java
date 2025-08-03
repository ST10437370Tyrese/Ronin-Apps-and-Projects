
package employeesalarycalcgui;
import javax.swing.JOptionPane;

public class EmployeeSalaryCalcGui {

    public static void main(String[] args) {
        // Collect user input using JOptionPane
        String name = JOptionPane.showInputDialog("Enter your first name:");
        String surname = JOptionPane.showInputDialog("Enter your surname:");
        String ageInput = JOptionPane.showInputDialog("Enter your age:");
        String salaryInput = JOptionPane.showInputDialog("Enter your monthly salary:");

        try {
            // Convert input to appropriate data types
            int age = Integer.parseInt(ageInput); 
            double monthlySalary = Double.parseDouble(salaryInput);

            // Calculate weekly wage 
            double weeklyWage = monthlySalary / 4;

            // Create the result message
            String resultMessage = String.format(
                "Employee Information:\n" +
                "Name: %s %s\n" +
                "Age: %d\n" +
                "Monthly Salary: R%.2f\n" +
                "Estimated Weekly Wage: R%.2f",
                name, surname, age, monthlySalary, weeklyWage
            );

            // Display the result in a dialog box
            JOptionPane.showMessageDialog(null, resultMessage, "Wage Estimation", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(null, "Invalid input! Please enter valid numeric values for age and salary.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


