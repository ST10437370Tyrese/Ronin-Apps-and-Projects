package variableswapping;
import java.util.Scanner;

public class VariableSwapping {
    public static void main(String[] args) {
        int valA = 8;
        int valB = 10;

        System.out.println("Before Swap:");
        System.out.println("valA = " + valA);
        System.out.println("valB = " + valB);

        
        // This variablewill swap the variables.
        int temp = valA;
        valA = valB;
        valB = temp;

        System.out.println("\nAfter Swap:");
        System.out.println("valA = " + valA);
        System.out.println("valB = " + valB);
        
       
    }
}
