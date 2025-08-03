
package distancecalc;
import java.util.Scanner;

public class DistanceCalc {
    public static void main(String[] args) {
       // Create Scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Asking for speed input
        System.out.print("Enter the speed in km/h: ");
        double speed = scanner.nextDouble();

        // Asking for time input
        System.out.print("Enter the total time in hours: ");
        double time = scanner.nextDouble();

        // Calculating total distance
        double distance = speed * time;

        // Displaying the result
        System.out.println("Total distance travelled: " + distance + " km");
 
    }
    
}
