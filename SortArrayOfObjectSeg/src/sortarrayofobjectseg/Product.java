/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sortarrayofobjectseg;

/**
 *
 * @author lab_services_student
 */
public class Product {
    //Data members
    private String productName;
    private double productPrice;
    
    //Constructor

    public Product(String productName, double productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
       
    }
    
    //Setters

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
    
    //Getters

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }
    
    
}
