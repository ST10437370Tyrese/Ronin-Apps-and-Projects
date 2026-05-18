/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package runapplication;

/**
 *
 * @author lab_services_student
 */

public abstract class Cricket implements ICricket {
    // Protected variables to store cricket data
    protected String batsmanName;
    protected String stadiumName;
    protected int totalRunsScored;
    
    // Constructor
    public Cricket(String batsmanName, String stadiumName, int totalRunsScored) {
        this.batsmanName = batsmanName;
        this.stadiumName = stadiumName;
        this.totalRunsScored = totalRunsScored;
    }
    
    // Getter methods implementing the interface
    @Override
    public String getBatsman() {
        return batsmanName;
    }
    
    @Override
    public String getStadium() {
        return stadiumName;
    }
    
    @Override
    public int getRunsScored() {
        return totalRunsScored;
    }
    
    // Abstract method to be implemented by subclass
    public abstract void printReport();
}