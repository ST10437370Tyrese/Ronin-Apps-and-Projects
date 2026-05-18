/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libraryfinecalculator;
public class Book extends LibraryItem {
    
    public Book(String title) {
        super(title);
    }
    
    @Override
    public double calculateFine(int daysLate, double rate, String memberType) {
        
        double fine = daysLate * 2.0;
        if (memberType.equalsIgnoreCase("child")) {
            fine *= 0.5;
        }
        return fine;
    }
}
