/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagement;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManagement manager = new StudentManagement(10);
        
        // Create some sample courses
        Course math = new Course("MATH101", "Calculus", 4);
        Course java = new Course("JAVA101", "Java Programming", 3);
        Course physics = new Course("PHYS101", "Physics", 4);
        
        System.out.println("STUDENT MANAGEMENT SYSTEM");
        System.out.println("=========================");
        
        // Add sample students
        Student student1 = new Student("S001", "John Doe", "john@email.com", 5);
        student1.addCourse(math, 85);
        student1.addCourse(java, 92);
        
        Student student2 = new Student("S002", "Jane Smith", "jane@email.com", 5);
        student2.addCourse(math, 78);
        student2.addCourse(physics, 88);
        student2.addCourse(java, 95);
        
        manager.addStudent(student1);
        manager.addStudent(student2);
        
        // Interactive menu
        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. View Class Report");
            System.out.println("2. Find Student by ID");
            System.out.println("3. Add New Student");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    manager.generateClassReport();
                    break;
                    
                case 2:
                    System.out.print("Enter Student ID: ");
                    String id = scanner.nextLine();
                    Student found = manager.findStudentById(id);
                    if (found != null) {
                        System.out.println("\n" + found.generateReport());
                    } else {
                        System.out.println("Student not found!");
                    }
                    break;
                    
                case 3:
                    if (manager.getStudentCount() >= 10) {
                        System.out.println("Maximum students reached!");
                        break;
                    }
                    
                    System.out.print("Enter Student ID: ");
                    String newId = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    
                    Student newStudent = new Student(newId, name, email, 5);
                    manager.addStudent(newStudent);
                    System.out.println("Student added successfully!");
                    break;
                    
                case 4:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                    
                default:
                    System.out.println("Invalid option!");
            }
        }
        
        scanner.close();
    }
}