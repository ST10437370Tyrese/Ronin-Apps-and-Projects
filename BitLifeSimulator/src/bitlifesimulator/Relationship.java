/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bitlifesimulator;
/**
 * Represents a relationship with another character in the game.
 * This is a separate, dedicated class for better code organization.
 */
public class Relationship {
    String name;
    String type; // e.g., "Friend", "Partner"
    int status; // e.g., 0-100

    public Relationship(String name, String type) {
        this.name = name;
        this.type = type;
        this.status = 50; // Initial status
    }
}

