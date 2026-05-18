/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tvseriesmanager;
import java.util.ArrayList;
import java.util.Scanner;

public class Series {
    
    // Package-private access for testing
    ArrayList<SeriesModel> seriesList = new ArrayList<>();
    private int seriesCounter = 1;
    Scanner scanner;
    
    public Series(Scanner scanner) {
        this.scanner = scanner;
    }
    
    public void CaptureSeries() {
        System.out.println("\nCAPTURE A NEW SERIES");
        System.out.println("***********************");
        
        // Get series ID from user (as shown in sample screenshot)
        System.out.print("Enter the series id: ");
        String seriesId = scanner.nextLine();
        
        System.out.print("Enter the series name: ");
        String seriesName = scanner.nextLine();
        
        // Validate age restriction
        String seriesAge = validateAgeRestriction();
        
        System.out.print("Enter the number of episodes for " + seriesName + ": ");
        String seriesNumberOfEpisodes = scanner.nextLine();
        
        // Constructor 
        SeriesModel newSeries = new SeriesModel(seriesId, seriesName, seriesAge, seriesNumberOfEpisodes);
        
        // Add to ArrayList
        seriesList.add(newSeries);
        
        // Inform user that series details have been successfully saved
        System.out.println("Series details have been successfully saved!");
        System.out.println("Series processed successfully!!!");
        System.out.print("Enter (1) to launch menu or any other key to exit: ");
        
        String choice = scanner.nextLine();
        if (!"1".equals(choice)) {
            ExitSeriesApplication();
        }
    }
    
    public void SearchSeries() {
        System.out.println("\n--- Search for a Series ---");
        System.out.print("Enter the series id to search: ");
        String searchId = scanner.nextLine();
        
        boolean found = false;
        
        // Search through all series
        for (SeriesModel series : seriesList) {
            if (series.SeriesId.equalsIgnoreCase(searchId)) {
                // Display series details exactly as shown in sample screenshot
                System.out.println("---");
                System.out.println("SERIES ID: " + series.SeriesId);
                System.out.println("SERIES NAME: " + series.SeriesName);
                System.out.println("SERIES AGE RESTRICTION: " + series.SeriesAge);
                System.out.println("SERIES NUMBER OF EPISODES: " + series.SeriesNumberOfEpisodes);
                System.out.println("---");
                found = true;
                break;
            }
        }
        
        // Display error message 
        if (!found) {
            System.out.println("---");
            System.out.println("Series with Series Id: " + searchId + " was not found!");
            System.out.println("---");
        }
        
        // Prompt to return to menu 
        System.out.print("Enter (1) to launch menu or any other key to exit: ");
        String choice = scanner.nextLine();
        if (!"1".equals(choice)) {
            ExitSeriesApplication();
        }
    }
    
    public void UpdateSeries() {
        System.out.println("\n--- Update Series ---");
        System.out.print("Enter the series id to update: ");
        String seriesId = scanner.nextLine();
        
        boolean found = false;
        
        // Search for the series to update
        for (SeriesModel series : seriesList) {
            if (series.SeriesId.equalsIgnoreCase(seriesId)) {
                found = true;
                
                // Display current values
                System.out.println("Current series name: " + series.SeriesName);
                System.out.println("Current age restriction: " + series.SeriesAge);
                System.out.println("Current number of episodes: " + series.SeriesNumberOfEpisodes);
                System.out.println("----------------------------------------");
                
                
                System.out.print("Enter the series name: ");
                String newName = scanner.nextLine();
                
                System.out.print("Enter the age restriction: ");
                String newAgeRestriction = scanner.nextLine();
                
                // Validate age restriction 
                if (!newAgeRestriction.isEmpty()) {
                    while (true) {
                        try {
                            int age = Integer.parseInt(newAgeRestriction);
                            if (age < 2 || age > 18) {
                                System.out.println("Age restriction must be between 2 and 18. Please try again.");
                                System.out.print("Enter the age restriction: ");
                                newAgeRestriction = scanner.nextLine();
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid number for age restriction.");
                            System.out.print("Enter the age restriction: ");
                            newAgeRestriction = scanner.nextLine();
                        }
                    }
                }
                
                System.out.print("Enter the number of episodes: ");
                String newEpisodes = scanner.nextLine();
                
                // Update the series with new values (only if user entered something)
                if (!newName.isEmpty()) {
                    series.SeriesName = newName;
                }
                if (!newAgeRestriction.isEmpty()) {
                    series.SeriesAge = newAgeRestriction;
                }
                if (!newEpisodes.isEmpty()) {
                    series.SeriesNumberOfEpisodes = newEpisodes;
                }
                
                System.out.println("Series updated successfully!");
                break;
            }
        }
        
        if (!found) {
            System.out.println("Series with Series Id: " + seriesId + " was not found!");
        }
        
        // Prompt to return to menu 
        System.out.print("Enter (1) to launch menu or any other key to exit: ");
        String choice = scanner.nextLine();
        if (!"1".equals(choice)) {
            ExitSeriesApplication();
        }
    }
    
    public void DeleteSeries() {
        System.out.println("\n--- Delete a Series ---");
        System.out.print("Enter the series id to delete: ");
        String seriesId = scanner.nextLine();
        
        boolean found = false;
        
        for (int i = 0; i < seriesList.size(); i++) {
            if (seriesList.get(i).SeriesId.equalsIgnoreCase(seriesId)) {
                found = true;
                String seriesName = seriesList.get(i).SeriesName;
                
                // Exact confirmation message as shown in sample screenshot
                System.out.print("Are you sure you want to delete series " + seriesId + " from the system? Yes (y) to delete.\n");
                String confirmation = scanner.nextLine();
                
                if ("y".equalsIgnoreCase(confirmation)) {
                    seriesList.remove(i);
                    // Exact success message as shown in sample screenshot
                    System.out.println("---Series with Series Id: " + seriesId + " WAS deleted!---");
                } else {
                    System.out.println("Deletion cancelled.");
                }
                break;
            }
        }
        
        if (!found) {
            System.out.println("Series with Series Id: " + seriesId + " was not found!");
        }
        
        // Exact exit prompt 
        System.out.print("Enter (1) to launch menu or any other key to exit: ");
        String choice = scanner.nextLine();
        if (!"1".equals(choice)) {
            ExitSeriesApplication();
        }
    }
    
    public void SeriesReport() {
        System.out.println("\n--- Series Report - 2025 ---");
        
        if (seriesList.isEmpty()) {
            System.out.println("No series available.");
            System.out.print("Enter (1) to launch menu or any other key to exit: ");
            String choice = scanner.nextLine();
            if (!"1".equals(choice)) {
                ExitSeriesApplication();
            }
            return;
        }
        
        // Display each series in the exact format 
        for (int i = 0; i < seriesList.size(); i++) {
            SeriesModel series = seriesList.get(i);
            System.out.println("Series " + (i + 1));
            System.out.println("---");
            System.out.println("SERIES ID: " + series.SeriesId);
            System.out.println("SERIES NAME: " + series.SeriesName);
            System.out.println("SERIES AGE RESTRICTION: " + series.SeriesAge);
            System.out.println("NUMBER OF EPISODES: " + series.SeriesNumberOfEpisodes);
            System.out.println("---");
        }
        
        // Prompt to return to menu 
        System.out.print("Enter (1) to launch menu or any other key to exit: ");
        String choice = scanner.nextLine();
        if (!"1".equals(choice)) {
            ExitSeriesApplication();
        }
    }
    
    public void ExitSeriesApplication() {
        System.out.println("Exiting application...");
        System.exit(0);
    }
    
    // Additional helper methods
    private String validateAgeRestriction() {
        String ageRestriction;
        boolean valid = false;
        
        do {
            System.out.print("Enter the series age restriction: ");
            ageRestriction = scanner.nextLine();
            
            // Numeric input
            if (!ageRestriction.matches("\\d+")) {
                System.out.println("You have entered a incorrect series age!!!");
                System.out.print("Please re-enter the series age >> ");
                continue;
            }
            
            // Integer
            try {
                int age = Integer.parseInt(ageRestriction);
                
                if (age < 2 || age > 18) {
                    System.out.println("You have entered a incorrect series age!!!");
                    System.out.print("Please re-enter the series age >> ");
                } else {
                    valid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("You have entered a incorrect series age!!!");
                System.out.print("Please re-enter the series age >> ");
            }
            
        } while (!valid);
        
        return ageRestriction;
    }
    
    // Method to display main menu and handle user choices
    public void displayMainMenu() {
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\nPlease select one of the following menu items:");
            System.out.println("(1) Capture a new series.");
            System.out.println("(2) Search for a series.");
            System.out.println("(3) Update series");
            System.out.println("(4) Delete a series.");
            System.out.println("(5) Print series report - 2025");
            System.out.println("(6) Exit Application.");
            System.out.print("\nEnter your choice: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    CaptureSeries();
                    break;
                case "2":
                    SearchSeries();
                    break;
                case "3":
                    UpdateSeries();
                    break;
                case "4":
                    DeleteSeries();
                    break;
                case "5":
                    SeriesReport();
                    break;
                case "6":
                    exit = true;
                    ExitSeriesApplication();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}