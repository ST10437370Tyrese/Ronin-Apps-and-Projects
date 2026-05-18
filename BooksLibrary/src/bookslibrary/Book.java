/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookslibrary;

/**
 *
 * @author lab_services_student
 */
public abstract class Book {
    //Data members
    private String title;
    private String author;
    
    //Constructor

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }
    //Getters

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
    
    //Abstract method
    public abstract double calculateFine(int daysLate);
    
    
    
}
