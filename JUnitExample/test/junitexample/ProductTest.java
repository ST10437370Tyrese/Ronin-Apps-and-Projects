/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package junitexample;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author lab_services_student
 */
public class ProductTest {
    
    public ProductTest() {
    }

    @Test
    public void  testAmtDueEquals(){
        //Arange / Expected
        int numItems = 5;
        double productPrice = 2;
        double expectedAmtDue;
        
        
        expectedAmtDue = 10;
        
        //Act / Actual
        Product myProduct = new Product();
        myProduct.setProductPrice(productPrice);
        double actualAmtDue;
        actualAmtDue = myProduct.calcAmtDue(numItems);
        
        //Assert
        
        assertEquals(expectedAmtDue, actualAmtDue, "Amount due must be 10 when numItems");
    }
    
    @Test
    public void testAmtDueZeroOrMore(){
        //Arange
        int numItems = 5;
        double productPrice = 2;
        
        //Act
        Product myProduct = new Product();
        myProduct.setProductPrice(productPrice);
        double actualAmtDue;
        actualAmtDue = myProduct.calcAmtDue(numItems);
        
        
        //Assert
        assertTrue(actualAmtDue >= 0, "The amount due must be positive");
    }
    
}
