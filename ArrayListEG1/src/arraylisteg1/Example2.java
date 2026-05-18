/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arraylisteg1;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author lab_services_student
 */
public class Example2 {

    // Declarations
    String firstName;
    String lastName;
    int test;
    int assignment;
    int exam;
    double finalMark;

    // constructor
    public Example2(String firstName, String lastName, int test, int assignment, int exam) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.test = test;
        this.assignment = assignment;
        this.exam = exam;
    }

    // getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void calculateFinalMark() {
        finalMark = (test * 0.2) + (assignment * 0.3) + (exam * 0.5);
    }

    public String result() {
        return finalMark >= 50 ? "Pass" : "Fail";
    }

    // Call Method to display student info
    public void displayStudentInfo() {
        System.out.println(firstName + " " + lastName +
                           " | Test: " + test +
                           " | Assignment: " + assignment +
                           " | Exam: " + exam +
                           " | Final: " + finalMark +
                           " | Result: " + result());
    }

    // call Method 
    public void updateMarks(int newTest, int newAssignment, int newExam) {
        this.test = newTest;
        this.assignment = newAssignment;
        this.exam = newExam;
        calculateFinalMark();
    }

    // method call
    private void calculateFinalmark() {
        calculateFinalMark();
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        //Declarations
        String firstName;
        String lastName;
        int test;
        int assignment;
        int exam;
        int noStudents;

        ArrayList<Example2> myStudents = new ArrayList<>();
        Scanner input = new Scanner(System.in);

        System.out.print("Enter number of students: ");
        noStudents = Integer.parseInt(input.nextLine());

        for (int i = 0; i < noStudents; i++) {
            System.out.print("First name: ");
            firstName = input.nextLine();
            System.out.print("Last name: ");
            lastName = input.nextLine();
            System.out.print("Test mark: ");
            test = Integer.parseInt(input.nextLine());
            System.out.print("Assignment mark: ");
            assignment = Integer.parseInt(input.nextLine());
            System.out.print("Exam mark: ");
            exam = Integer.parseInt(input.nextLine());

            if (test < 0 || assignment < 0 || exam < 0) {
                System.out.println("Wrong test mark entered!!! re enter!!");
                i = i + 1;
            } else {
                myStudents.add(new Example2(firstName, lastName, test, assignment, exam));
                myStudents.get(i).calculateFinalmark();
            }

            System.out.println("============= Student Results =============");

            for (Example2 student : myStudents) {
                student.displayStudentInfo();
            }
        }
    }
}
