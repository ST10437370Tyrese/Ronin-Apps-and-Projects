/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package citylibrary;

/**
 *
 * @author lab_services_student
 */
public class Borrower {

    //Declaration
    String firstName;
    String lastName;
    int booksBorrowed;
    double ratePerBook;
    double fine;
    double total;

    //Constructors

    public Borrower() {
    }
    
    
    public Borrower(String firstName, String lastName, int booksBorrowed, double ratePerBook) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.booksBorrowed = booksBorrowed;
        this.ratePerBook = ratePerBook;
    }

    //Setter
    public void setFine(double fine) {
        this.fine = fine;
    }

    //Getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getBooksBorrowed() {
        return booksBorrowed;
    }

    public double getRatePerBook() {
        return ratePerBook;
    }

    public double getTotal() {
        return total;
    }
    
    //Total payments
    
   public void calculateTotal(int days){
       total = booksBorrowed * ratePerBook;
       if(days <= 5){
           total = total;
       }
       else if(days <= 6){
         total = total + (fine + 0.15 * total);  
       }
       else if(days > 10){
         total = total + (fine + 0.25 * total); 
       }
       
   }
}
