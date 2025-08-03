
package lengthwidthcalc;
import java.util.Scanner;

public class LengthWidthCalc {
    public static void main(String[] args) {
        // Create a Scanner object for user input
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the length of the rectangle: ");
        double length = scanner.nextDouble();
        
        System.out.println("Enter the width of the rectangle: ");
        double width = scanner.nextDouble();
        
        double area = length * width;
        
        System.out.println("The area of the rectangle is: " + area);
        
        
       
    }
    
}
