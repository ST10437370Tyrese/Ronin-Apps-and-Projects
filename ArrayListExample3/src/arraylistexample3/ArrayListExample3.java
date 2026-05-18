/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package arraylistexample3;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author lab_services_student
 */
public class ArrayListExample3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Declarations
        ArrayList<Student> myStudents = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("**************************");
            System.out.println("The app will show the menu");
            System.out.println("**************************");
            menu();

            choice = Integer.parseInt(input.nextLine());

            switch (choice) {
                case 1:
                    myStudents.add(capture(input));
                    break;
                case 2:
                    display(myStudents, input);
                    break;
                case 3:
                    erase(myStudents, input);
                    break;
                case 4:
                    closeApplication();
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    //method to display menu
    public static void menu() {
        System.out.println("Choose from the menu below");
        System.out.println("<1> Capture student information");
        System.out.println("<2> Show student information");
        System.out.println("<3> Erase student information");
        System.out.println("<4> Close the application");
    }

    //method to close the application
    public static void closeApplication() {
        System.out.println("Closing application... Goodbye!");
        System.exit(0);
    }

    //method to capture student info
    public static Student capture(Scanner input) {
        int studentID;
        String firstName;
        String lastName;

        System.out.println("Enter Student ID: ");
        studentID = Integer.parseInt(input.nextLine());

        System.out.println("Enter first name: ");
        firstName = input.nextLine();

        System.out.println("Enter last name: ");
        lastName = input.nextLine();

        Student student = new Student(studentID, firstName, lastName);
        return student;
    }

    //method to display
    public static void display(ArrayList<Student> myStudents, Scanner input) {
        if (myStudents.isEmpty()) {
            System.out.println("No students available to display.");
            return;
        }

        System.out.println("Enter a first name to search (or press Enter to show all): ");
        String answer = input.nextLine();

        boolean found = false;
        if (answer.isEmpty()) {
            // Show all students
            System.out.println("All Students:");
            for (var student : myStudents) {
                System.out.println(student);
            }
            found = true;
        } else {
            // Search by first name
            for (var student : myStudents) {
                if (answer.equalsIgnoreCase(student.getFirstName())) {
                    System.out.println("--------------------------");
                    System.out.println("Student ID: " + student.getStudentID());
                    System.out.println("Student First Name: " + student.getFirstName());
                    System.out.println("Student Last Name: " + student.getLastName());
                    System.out.println("--------------------------");
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No student found with that first name.");
        }
    }

    //method to erase
    public static void erase(ArrayList<Student> myStudents, Scanner input) {
        if (myStudents.isEmpty()) {
            System.out.println("No students available to erase.");
            return;
        }

        System.out.println("Enter a student ID to delete: ");
        int answer = Integer.parseInt(input.nextLine());

        boolean removed = false;
        for (int i = 0; i < myStudents.size(); i++) {
            if (answer == myStudents.get(i).getStudentID()) {
                myStudents.remove(i);
                System.out.println("Student with ID " + answer + " removed.");
                removed = true;
                break;
            }
        }

        if (!removed) {
            System.out.println("No student found with that ID.");
        }
    }
}
