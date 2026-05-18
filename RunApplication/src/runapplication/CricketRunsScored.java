/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package runapplication;
// CricketRunsScored.java
public class CricketRunsScored extends Cricket {
    
    // Constructor
    public CricketRunsScored(String batsmanName, String stadiumName, int totalRunsScored) {
        super(batsmanName, stadiumName, totalRunsScored);
    }
    
    // Implementation of the abstract printReport method
    @Override
    public void printReport() {
        System.out.println("=========================================");
        System.out.println("        BATSMAN RUNS SCORED REPORT");
        System.out.println("=========================================");
        System.out.printf("%-20s: %s\n", "CRICKET PLAYER", getBatsman());
        System.out.printf("%-20s: %s\n", "STADIUM", getStadium());
        System.out.printf("%-20s: %,d runs\n", "TOTAL RUNS SCORED", getRunsScored());
        System.out.println("=========================================");
        System.out.println();
    }
    
    // Additional method to display formatted output
    public void displayCareerStats() {
        System.out.printf("%-25s at %-20s: %,d runs\n", 
                         getBatsman(), getStadium(), getRunsScored());
    }
}