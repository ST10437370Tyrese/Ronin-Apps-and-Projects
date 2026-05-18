
package elementcalc;
import java.util.Scanner;


public class ElementCalc {
    public static void main(String[] args) {
        //Declaration
        int sum = 0;
        int element = 1; // First element of the sequence
        
        //Determine the sum
        for (int i = 0; i < 20; i++) {
            sum += element;
            
            // Add between adding 3 and 2
            if (i % 2 == 0) {
                element += 3;
            } else {
                element += 2;
            }
        }
        
        //Display sum
        System.out.println("The sum of the first 20 elements is: " + sum);
        
    }
}
