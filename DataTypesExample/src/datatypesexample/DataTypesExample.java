
package datatypesexample;
import java.util.Scanner; // importing scanner class for user input




public class DataTypesExample {

  
    public static void main(String[] args) {
        // create a scanner object to taken input from the user
        Scanner scanner = new Scanner (System.in);
        
        
        // Prompt user for the their name
        System.out.println("Please Enter Your Name: ");
        String name = scanner.nextLine();
        
        //Display name back to the user
        System.out.println("Hello: " + name);
        
        
      
    }
    
}
