/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookslibrary;

/**
 *
 * @author lab_services_student
 */
public class NonFictionBook extends Book {
    //Constructor
    public NonFictionBook(String title, String author) {
        super(title, author);
    }
    
    //Override
    
    @Override
    public double calculateFine(int daysLate) {
        return daysLate * 2;
    }
    
}
