/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagement;


public class Person {
    private String id;
    private String name;
    private String email;
    
    public Person(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    // Getters with information hiding
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    
    // Setters with validation
    public void setName(String name) { 
        if (name != null && !name.trim().isEmpty()) {
            this.name = name; 
        }
    }
    
    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email;
        }
    }
    
    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Email: " + email;
    }
}