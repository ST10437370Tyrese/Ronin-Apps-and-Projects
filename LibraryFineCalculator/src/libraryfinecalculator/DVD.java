/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libraryfinecalculator;
public class DVD extends LibraryItem {
    
    public DVD(String title) {
        super(title);
    }
    
    @Override
    public double calculateFine(int daysLate, double rate, String memberType) {
        
        double fine = daysLate * 5.0;
        if (memberType.equalsIgnoreCase("child")) {
            fine *= 0.5;
        }
        return fine;
    }
}
