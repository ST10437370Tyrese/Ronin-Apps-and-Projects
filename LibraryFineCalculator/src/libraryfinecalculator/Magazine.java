/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libraryfinecalculator;
public class Magazine extends LibraryItem {
    
    public Magazine(String title) {
        super(title);
    }
    
    @Override
    public double calculateFine(int daysLate, double rate, String memberType) {
        // Magazines: R1 per day
        double fine = daysLate * 1.0;
        if (memberType.equalsIgnoreCase("child")) {
            fine *= 0.5;
        }
        return fine;
    }
}