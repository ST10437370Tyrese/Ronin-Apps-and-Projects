/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sortarrayofobjectseg;

import java.util.Scanner;

/**
 *
 * @author lab_services_student
 */
public class SortArrayOfObjectSeg {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Declaration
        String firstName, lastName;
        int booksBorrowed, days;
        double ratePerBook;

        Scanner input = new Scanner(System.in);

        //Get number of borrowers
        System.out.print("Enter number of borrowers: ");
        int numBorrowers = Integer.parseInt(input.nextLine());

        //Declare Array of Borrower objects
        Product[] borrowers = new Product[numBorrowers];

        //Populate Array of Objects
        for (int i = 0; i < borrowers.length; i++) {
            //Input
            System.out.println("\nBorrower " + (i+1));
            System.out.print("Enter first name: ");
            firstName = input.nextLine();
            System.out.print("Enter last name: ");
            lastName = input.nextLine();
            System.out.print("Enter number of books borrowed: ");
            booksBorrowed = Integer.parseInt(input.nextLine());
            System.out.print("Enter rate per book: ");
            ratePerBook = Double.parseDouble(input.nextLine());
            System.out.print("Enter number of days borrowed: ");
            days = Integer.parseInt(input.nextLine());

            borrowers[i] = new Borrowers(firstName, lastName, booksBorrowed, ratePerBook);
            borrowers[i].setFine(100.00); // Set fine to R100
            borrowers[i].calculatePayment(days);
        }
        
        sortBorrowersByPayment(borrowers);
        displayBorrowers(borrowers);
    }

    public static void sortBorrowersByPayment(Product[] borrowers) {
        Product temp;

        for(int j = 0; j < borrowers.length; j++) {
            for(int i = 0; i < borrowers.length - 1; i++){
                if(borrowers[i].getTotal() < borrowers[i+1].getTotal()){
                    temp = borrowers[i];
                    borrowers[i] = borrowers[i+1];
                    borrowers[i+1] = temp;
                }
            }
        }
    }

    public static void displayBorrowers(Product[] borrowers) {
        System.out.println("\nSorted List of Borrowers:");
        for (int i = 0; i < borrowers.length; i++) {
            System.out.println(borrowers[i].getFirstName() + " " + borrowers[i].getLastName() + 
                    ": Total Payment R" + borrowers[i].getTotal());
        }
    }
}