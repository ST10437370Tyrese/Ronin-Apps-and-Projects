
package waterbottlecalc;
import java.util.Scanner;

public class WaterBottleCalc {
    public static void main(String[] args) {
       
        final double BOTTLE_PRICE = 8.0;
        final double VAT_RATE = 0.10; 

        // Scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Please ask for input
        System.out.print("Enter the number of water bottles purchased: ");
        int numberOfBottles = scanner.nextInt();

        // Calculating total cost before VAT
        double totalBeforeVAT = numberOfBottles * BOTTLE_PRICE;

        // Calculating VAT amount
        double vatAmount = totalBeforeVAT * VAT_RATE;

        // Calculating final total cost
        double totalCost = totalBeforeVAT + vatAmount;

        // Displaying results
        System.out.println("Total cost before VAT: R" + totalBeforeVAT);
        System.out.println("VAT (10%): R" + vatAmount);
        System.out.println("Final total cost: R" + totalCost);
    }
    
}
