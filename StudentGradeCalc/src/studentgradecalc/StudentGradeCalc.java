package studentgradecalc;
import java.util.Scanner;

public class StudentGradeCalc {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Please accept student's name and test scores
        System.out.print("Enter the student's name: ");
        String studentName = scanner.nextLine();
        
        System.out.print("Enter the score for test 1: ");
        double score1 = scanner.nextDouble();
        
        System.out.print("Enter the score for test 2: ");
        double score2 = scanner.nextDouble();
        
        System.out.print("Enter the score for test 3: ");
        double score3 = scanner.nextDouble();
        
        // Calculate total and average scores
        double total = score1 + score2 + score3;
        double average = total / 3;
        
        // Display the student's name, total, and average scores
        System.out.println("\nStudent Name: " + studentName);
        System.out.println("Total Marks: " + total);
        System.out.println("Average Marks: " + average);
        
        // Display the student's report based on the average score
        System.out.println("\n--- Student Report ---");
        if (average >= 90) {
            System.out.println("Grade: A");
            System.out.println("Excellent performance!");
        } else if (average >= 80) {
            System.out.println("Grade: B");
            System.out.println("Good job, but there's room for improvement.");
        } else if (average >= 70) {
            System.out.println("Grade: C");
            System.out.println("You passed, but work harder next time.");
        } else if (average >= 60) {
            System.out.println("Grade: D");
            System.out.println("You barely passed. It's time to focus more on your studies.");
        } else {
            System.out.println("Grade: F");
            System.out.println("Unfortunately, you did not pass. Please seek help to improve.");
            
            scanner.close();
        }
   
    }
}
