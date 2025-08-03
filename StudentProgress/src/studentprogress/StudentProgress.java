package studentprogress;
import java.util.Scanner;

public class StudentProgress {

    public static void main(String[] args) {
         // Create a Scanner object to read input from the user
        Scanner scanner = new Scanner(System.in);
        
        //Welcome message
        System.out.println("Welcome to the Student Profile Generator!");


        // Get student name
        System.out.print("Please enter First Name: ");
        String firstName = scanner.nextLine();

        // Get student surname
        System.out.print("Please enter Last Name: ");
        String lastName = scanner.nextLine();

        // Get student age
        System.out.print("Please enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // clear newline

        // Get student's favorite subject
        System.out.print("Please enter Favorite Subject: ");
        String favoriteSubject = scanner.nextLine();

        // Get college name
        System.out.print("Please enter College Name: ");
        String collegeName = scanner.nextLine();

        // Display student details
        System.out.println("\n---Student Profile:---");
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Age: " + age);
        System.out.println("Favorite Subject: " + favoriteSubject);
        System.out.println("College Name: " + collegeName);

        // Display success message
        System.out.println("\nProfile created successfully!");

        // Close the scanner
        scanner.close();
    }
}
