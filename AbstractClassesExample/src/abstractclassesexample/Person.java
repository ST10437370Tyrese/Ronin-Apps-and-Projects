/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abstractclassesexample;

/**
 *
 * @author lab_services_student
 */


public abstract class Person {
    
    //Data members
    private String fname;
    private String lname;
    private int age;
    
    
    
    //Default Constructor
    public Person(){


    }
       public Person(String fname, String lname, int age) {
            this.fname = fname;
            this.lname = lname;
            this.age = age;
    }

    //Concrete Method
       public String message(){
           return fname + ":" + lname + ":" + age;
           
       }
    
    
    
    //Abstract Method
    public abstract void eatTime();


}


