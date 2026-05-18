/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tvseriesmanager;
import java.util.Scanner;

public class TVSeriesManager {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Display initial menu
        System.out.println("LATEST SERIES - 2025");
        System.out.println("**********************************************************************");
        System.out.print("Enter (1) to launch menu or any other key to exit: ");
        
        String initialChoice = scanner.nextLine();
        
        if ("1".equals(initialChoice)) {
            // Create Series object and start the application
            Series seriesApp = new Series(scanner);
            seriesApp.displayMainMenu();
        } else {
            System.out.println("Exiting application...");
        }
        
        scanner.close();
    }
}