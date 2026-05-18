/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libraryfinecalculator;


public class LibraryItem {
    protected String title;
    
    // Constructors
    public LibraryItem(String title) {
        this.title = title;
    }
    
    // Getters
    public String getTitle() {
        return title;
    }
    
    // Overloaded Methods
    public double calculateFine(int daysLate) {
        
        return daysLate * 1.0;
    }
    
    public double calculateFine(int daysLate, double rate) {
        return daysLate * rate;
    }
    
    public double calculateFine(int daysLate, double rate, String memberType) {
        double fine = daysLate * rate;
        if (memberType.equalsIgnoreCase("child")) {
            fine *= 0.5; // 50% discount for children
        }
        return fine;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + title;
    }
}
