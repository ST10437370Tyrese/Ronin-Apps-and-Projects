/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package abstractclassesexample;

import java.util.Scanner;

/**
 *
 * @author lab_services_student
 */
public class AbstractClassesExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String fname, lname, courseCode;
        int age;
        
        Scanner input = new Scanner(System.in);
        
        
        System.out.println("Enter your first name:");
        fname = input.nextLine();
        
        System.out.println("Enter your last name:");
        lname = input.nextLine();
        
        System.out.println("Enter your age:");
        age = Integer.parseInt(input.nextLine());
        
        System.out.println("Enter your course code:");
        courseCode = input.nextLine();
        
        Student myStudent = new Student("PROG6112", "Ronin", "Mauries", 21);
        
        
        System.out.println(myStudent.message());
    
        
        
    }
    
}
