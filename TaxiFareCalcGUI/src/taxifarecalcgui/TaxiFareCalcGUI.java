
package taxifarecalcgui;
import javax.swing.JOptionPane;

public class TaxiFareCalcGUI {
    public static void main(String[] args) {
      // Get user input for distance traveled
        double distance = Double.parseDouble(JOptionPane.showInputDialog("Enter the distance traveled in kilometers:"));
        
        // Get user input for fare per kilometer
        double farePerKm = Double.parseDouble(JOptionPane.showInputDialog("Enter the fare per kilometer:"));
        
        // Calculate the total fare
        double totalFare = distance * farePerKm;
        
        // Display the total fare using JOptionPane
        String message = String.format("Total Fare: R%.2f", totalFare);
        JOptionPane.showMessageDialog(null, message, "Taxi Fare Calculator", JOptionPane.INFORMATION_MESSAGE);  
    }
    
}
