/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookslibrary;

/**
 *
 * @author lab_services_student
 */
public class LibraryBook extends FictionBook implement Borrowable {

    public LibraryBook(String title, String author) {
        super(title, author);
    }
    
  //Override  
  @Override
  public double borrow(){
      System.out.println("Hello"){
      
  }
  
  //Override
  @Override
  public void returnedBook(){
      System.out.println("adasda");
      
  }
    
    
}
