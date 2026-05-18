/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package libraryfinecalculator;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author lab_services_student
 */

public class LibraryFineCalculator {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        
        ArrayList<LibraryItem> libraryItems = new ArrayList<>();
        
        
        libraryItems.add(new Book("The Great Gatsby"));
        libraryItems.add(new Book("To Kill a Mockingbird"));
        libraryItems.add(new Magazine("National Geographic"));
        libraryItems.add(new Magazine("Time Magazine"));
        libraryItems.add(new DVD("The Shawshank Redemption"));
        libraryItems.add(new DVD("Inception"));
        
        // Get user input
        System.out.println("=== Library Fine Calculator ===");
        System.out.print("Enter number of days late: ");
        int daysLate = scanner.nextInt();
        scanner.nextLine(); 
        
        System.out.print("Enter member type (adult/child): ");
        String memberType = scanner.nextLine();
        
        System.out.println("\n=== Fine Calculation Results ===");
        
        // Loop through items and calculate fines
        for (LibraryItem item : libraryItems) {
            double fine = item.calculateFine(daysLate, 0, memberType);
            
            System.out.printf("%s - Fine: R%.2f%n", 
                             item.toString(), 
                             fine);
        }
        
        // Overloaded Methods
        System.out.println("\n=== Examples of Overloaded Methods ===");
        
        LibraryItem sampleBook = new Book("Sample Book");
        LibraryItem sampleDVD = new DVD("Sample DVD");
        
        
        System.out.printf("Book with default rate (3 days): R%.2f%n", 
                         sampleBook.calculateFine(3));
        
       
        System.out.printf("DVD with custom rate R3 (2 days): R%.2f%n", 
                         sampleDVD.calculateFine(2, 3.0));
        
        
        System.out.printf("Book for child (3 days): R%.2f%n", 
                         sampleBook.calculateFine(3, 0, "child"));
        
        scanner.close();
    }
}
