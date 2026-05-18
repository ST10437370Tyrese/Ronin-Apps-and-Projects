/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arraylistexample3;

/**
 *
 * @author lab_services_student
 */
public class Student {
    // Declarations
    private int studentID;
    private String firstName;
    private String lastName;

    // Constructor
    public Student(int studentID, String firstName, String lastName) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters
    public int getStudentID() {
        return studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    // Setters
    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // toString method
    @Override
    public String toString() {
        return "Student{" +
                "ID=" + studentID +
                ", First Name='" + firstName + '\'' +
                ", Last Name='" + lastName + '\'' +
                '}';
    }
}
