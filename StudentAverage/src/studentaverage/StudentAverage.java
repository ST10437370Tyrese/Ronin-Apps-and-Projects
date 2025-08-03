
package studentaverage;
import java.util.Scanner;

public class StudentAverage { 
    public static void main(String[] args) {
        // Create a Scanner object for user input
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the first test score: ");
        double score1 = scanner.nextDouble();
        
        System.out.println("Enter the second test score: ");
        double score2 = scanner.nextDouble();
        
        System.out.println("Enter the third test score: ");
        double score3 = scanner.nextDouble();
        
        // Calculate the average score
        double average = (score1 + score2 + score3) / 3;
        

        System.out.println("The average score is: " + average);
        
        
    }
    
}
