/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package junitexample;

/**
 *
 * @author lab_services_student
 */
public class Product {
    //Data memebers
    
    private String productName;
    private double productPrice;
    
    
    //Setters and getters

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public void setProductPrice(double productPrice) {
            this.productPrice = productPrice;
        }

        public String getProductName() {
            return productName;
        }

        public double getProductPrice() {
            return productPrice;
        }

    

   
    
    
    
    //Calculate amount due
    public double calcAmtDue(int numItems){
        double amtDue;
        
        amtDue = numItems * productPrice;

        return amtDue;
    }
    
}
