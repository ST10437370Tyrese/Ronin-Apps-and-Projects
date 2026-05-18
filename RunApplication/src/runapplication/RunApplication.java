/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
//@st10437370_Ronin Mauries
package runapplication;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class RunApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<CricketRunsScored> cricketRecords = new ArrayList<>();
        
        System.out.println("=========================================");
        System.out.println("   CRICKET RUNS SCORED TRACKING SYSTEM");
        System.out.println("=========================================");
        System.out.println();
        
        boolean continueEntering = true;
        
        while (continueEntering) {
            // Get user input
            System.out.println("Enter Cricket Record Details:");
            System.out.println("-----------------------------");
            
            System.out.print("The cricketer's name: ");
            String batsmanName = scanner.nextLine();
            
            System.out.print("Enter the stadium name: ");
            String stadiumName = scanner.nextLine();
            
            int totalRuns = 0;
            boolean validInput = false;
            
            // Input
            while (!validInput) {
                try {
                    System.out.print("Enter the total runs scored: ");
                    totalRuns = Integer.parseInt(scanner.nextLine());
                    
                    if (totalRuns < 0) {
                        System.out.println("Error: Runs cannot be negative. Please try again.");
                        continue;
                    }
                    
                    validInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Please enter a valid number for runs.");
                }
            }
            
            
            CricketRunsScored record = new CricketRunsScored(batsmanName, stadiumName, totalRuns);
            cricketRecords.add(record);
            
            // Continuation
            System.out.print("\nDo you want to enter another record? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            
            if (!response.equals("yes") && !response.equals("y")) {
                continueEntering = false;
            }
            System.out.println();
        }
        
        // Display individual reports
        System.out.println("\nINDIVIDUAL PLAYER REPORTS:");
        System.out.println("==========================");
        for (CricketRunsScored record : cricketRecords) {
            record.printReport();
        }
        
        // Display summary report
        displaySummaryReport(cricketRecords);
        
        // Display highest scorer
        displayHighestScorer(cricketRecords);
        
        scanner.close();
    }
    
    // Method to display summary report
    private static void displaySummaryReport(List<CricketRunsScored> records) {
        System.out.println("SUMMARY REPORT:");
        System.out.println("===============");
        System.out.printf("%-25s %-20s %-15s\n", "Batsman", "Stadium", "Runs Scored");
        System.out.println("------------------------------------------------------------");
        
        for (CricketRunsScored record : records) {
            System.out.printf("%-25s %-20s %,15d\n", 
                            record.getBatsman(), 
                            record.getStadium(), 
                            record.getRunsScored());
        }
        System.out.println();
    }
    
    // Method to find and display highest scorer
    private static void displayHighestScorer(List<CricketRunsScored> records) {
        if (records.isEmpty()) {
            System.out.println("No records available.");
            return;
        }
        
        CricketRunsScored highestScorer = records.get(0);
        for (CricketRunsScored record : records) {
            if (record.getRunsScored() > highestScorer.getRunsScored()) {
                highestScorer = record;
            }
        }
        
        System.out.println("HIGHEST RUN SCORER:");
        System.out.println("===================");
        highestScorer.printReport();
    }
}