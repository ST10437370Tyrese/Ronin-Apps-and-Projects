/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abstractclassesexample;

/**
 *
 * @author lab_services_student
 */
public class Student extends Person {
    //Data Memebers
    private String courseCode;
    
    
    //Constructor

    public Student(String courseCode) {
        this.courseCode = courseCode;
        
    }
    
    @Override
    public void eatTime(){
        System.out.println("12:00"); 
        
    }
    
    @Override
    public String message(){
        return super.message() + " : " + courseCode;
    }
    
    
}
