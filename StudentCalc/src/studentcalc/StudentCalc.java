
package studentcalc;
import java.util.Scanner;

public class StudentCalc {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Create variables to store marks and total marks
        int marksObtained, totalMarks, totalObtained = 0, totalPossible = 0;
        
        // Loop for three subjects
        for (int i = 1; i <= 3; i++) {
            System.out.print("Please enter marks obtained for subject " + i + ": ");
            marksObtained = scanner.nextInt();
            
            System.out.print("Please enter total possible marks for subject " + i + ": ");
            totalMarks = scanner.nextInt();
            
            totalObtained += marksObtained;
            totalPossible += totalMarks;
        }
        
        // Calculate percentage
        double percentage = ((double) totalObtained / totalPossible) * 100;
        
        // Display results
        System.out.printf("Final Percentage: %.2f%%\n", percentage);
        if (percentage > 50) {
            System.out.println("Congratulations! You Passed.");
        } else {
            System.out.println("My apologies! You Failed.");
        }
        
    }
}

