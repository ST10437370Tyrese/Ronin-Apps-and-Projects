/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bitlifesimulator;
/**
 * Represents a job a character can have in the game.
 * This is a separate, dedicated class for better code organization.
 */
public class Job {
    String title;
    int salary;
    int smartsRequired;

    public Job(String title, int salary, int smartsRequired) {
        this.title = title;
        this.salary = salary;
        this.smartsRequired = smartsRequired;
    }
}
