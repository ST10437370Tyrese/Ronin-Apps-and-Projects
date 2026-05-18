/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bitlifesimulator;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 
 */
public class BitLifeSimulator {

    // --- Player Stats ---
    private static int age = 0;
    private static int health = 100;
    private static int happiness = 100;
    private static int money = 0;
    private static int smarts = 0;
    private static Job currentJob = new Job("Unemployed", 0, 0);
    private static List<Relationship> relationships = new ArrayList<>();
    private static int relationshipCount = 0;

    // --- Game Utilities ---
    private static final Random random = new Random();
    private static final Scanner scanner = new Scanner(System.in);

    // List of possible jobs with their requirements
    private static final Job[] availableJobs = {
        new Job("Retail Associate", 25000, 10),
        new Job("Office Clerk", 35000, 25),
        new Job("Software Developer", 75000, 50),
        new Job("Doctor", 150000, 80)
    };

    public static void main(String[] args) {
        System.out.println("Welcome to the Java Life Simulator!");
        System.out.println("Let's see how your life turns out...");

        // Main game loop. The game continues as long as the player is alive.
        while (health > 0 && age < 100) {
            liveAYear();
            // Wait for user input before moving to the next year
            System.out.println("\nPress Enter to live another year...");
            scanner.nextLine();
        }

        // Game over
        System.out.println("----------------------------------------");
        System.out.println("Game Over!");
        if (health <= 0) {
            System.out.println("Your health reached zero. You have passed away.");
        } else {
            System.out.println("You lived to the ripe old age of 100!");
        }
        System.out.println("Your final stats:");
        displayStats();
        scanner.close();
    }

    /**
     * Simulates one year of the character's life.
     */
    private static void liveAYear() {
        age++;
        System.out.println("\n----------------------------------------");
        System.out.println("Year " + age + ":");

        // Display current stats
        displayStats();

        // Random events for the year
        handleRandomEvents();

        // Player choices
        makeChoice();

        // Ensure stats don't go below or above boundaries
        health = Math.max(0, Math.min(100, health));
        happiness = Math.max(0, Math.min(100, happiness));
        smarts = Math.max(0, Math.min(100, smarts));
    }

    /**
     * Displays the current stats of the player.
     */
    private static void displayStats() {
        System.out.println("Health: " + health + "/100");
        System.out.println("Happiness: " + happiness + "/100");
        System.out.println("Money: $" + money);
        System.out.println("Smarts: " + smarts + "/100");
        System.out.println("Job: " + currentJob.title);
        System.out.print("Relationships: ");
        if (relationships.isEmpty()) {
            System.out.println("None");
        } else {
            for (Relationship r : relationships) {
                System.out.print(r.name + " (" + r.type + ": " + r.status + ") | ");
            }
            System.out.println();
        }
    }

    /**
     * Presents a menu of choices to the player and handles the chosen action.
     */
    private static void makeChoice() {
        System.out.println("\nWhat would you like to do this year?");
        System.out.println("1. Work (Earn money)");
        System.out.println("2. Go to the gym (Increase health)");
        System.out.println("3. Go on vacation (Increase happiness, lose money)");
        System.out.println("4. Study (Increase smarts)");
        System.out.println("5. Apply for a job");
        System.out.println("6. Interact with someone");
        System.out.print("Enter your choice (1-6): ");

        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    work();
                    break;
                case 2:
                    goToGym();
                    break;
                case 3:
                    goOnVacation();
                    break;
                case 4:
                    study();
                    break;
                case 5:
                    applyForJob();
                    break;
                case 6:
                    interactWithSomeone();
                    break;
                default:
                    System.out.println("Invalid choice. You did nothing this year.");
                    break;
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. You did nothing this year.");
            scanner.nextLine(); // Clear the invalid input
        }
    }

    /**
     * Handles the 'Work' choice.
     */
    private static void work() {
        if (currentJob.salary > 0) {
            money += currentJob.salary;
            happiness -= 5;
            System.out.println("You worked hard at your job as a " + currentJob.title + " and earned $" + currentJob.salary + ".");
        } else {
            System.out.println("You are currently unemployed. You should apply for a job first.");
        }
    }

    /**
     * Handles the 'Go to the gym' choice.
     */
    private static void goToGym() {
        health += 15;
        happiness += 5;
        System.out.println("You worked out and feel much healthier.");
    }

    /**
     * Handles the 'Go on vacation' choice.
     */
    private static void goOnVacation() {
        int cost = 300 + random.nextInt(200);
        if (money >= cost) {
            money -= cost;
            happiness += 30;
            System.out.println("You went on a relaxing vacation and feel great.");
        } else {
            happiness -= 5;
            System.out.println("You couldn't afford a vacation. That's a bummer.");
        }
    }

    /**
     * Handles the 'Study' choice.
     */
    private static void study() {
        int smartsIncrease = 5 + random.nextInt(10);
        smarts += smartsIncrease;
        happiness -= 5;
        System.out.println("You studied hard. Smarts +" + smartsIncrease + ". You feel smarter but a little bored.");
    }

    /**
     * Handles the 'Apply for a job' choice.
     */
    private static void applyForJob() {
        System.out.println("Available jobs:");
        for (int i = 0; i < availableJobs.length; i++) {
            System.out.println((i + 1) + ". " + availableJobs[i].title + " (Salary: $" + availableJobs[i].salary + ", Smarts required: " + availableJobs[i].smartsRequired + ")");
        }
        System.out.print("Enter the number of the job you want to apply for: ");

        try {
            int jobChoice = scanner.nextInt();
            scanner.nextLine();

            if (jobChoice > 0 && jobChoice <= availableJobs.length) {
                Job selectedJob = availableJobs[jobChoice - 1];
                if (smarts >= selectedJob.smartsRequired) {
                    currentJob = selectedJob;
                    System.out.println("Congratulations! You got the job as a " + currentJob.title + ".");
                } else {
                    System.out.println("You don't have enough smarts for this job. You need to study more.");
                }
            } else {
                System.out.println("Invalid job choice.");
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Try again next year.");
                scanner.nextLine();
        }
    }

    /**
     * Handles the 'Interact with someone' choice.
     */
    private static void interactWithSomeone() {
        if (relationships.isEmpty()) {
            System.out.println("You meet someone new!");
            String newFriendName = "Friend " + (++relationshipCount);
            relationships.add(new Relationship(newFriendName, "Friend"));
            System.out.println("You are now friends with " + newFriendName + ".");
        } else {
            System.out.println("Who would you like to interact with?");
            for (int i = 0; i < relationships.size(); i++) {
                System.out.println((i + 1) + ". " + relationships.get(i).name);
            }
            System.out.print("Enter the number: ");

            try {
                int personChoice = scanner.nextInt();
                scanner.nextLine();

                if (personChoice > 0 && personChoice <= relationships.size()) {
                    Relationship person = relationships.get(personChoice - 1);
                    System.out.println("\nWhat would you like to do with " + person.name + "?");
                    System.out.println("1. Hang out");
                    System.out.println("2. Get in a fight");
                    System.out.print("Enter your choice: ");
                    int actionChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (actionChoice == 1) {
                        person.status += 10;
                        happiness += 10;
                        System.out.println("You hung out with " + person.name + ". Your relationship improved.");
                    } else if (actionChoice == 2) {
                        person.status -= 20;
                        happiness -= 15;
                        health -= 5;
                        System.out.println("You got in a fight with " + person.name + ". Your relationship got worse.");
                    } else {
                        System.out.println("Invalid choice. Nothing happened.");
                    }
                } else {
                    System.out.println("Invalid choice.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Try again next year.");
                scanner.nextLine();
            }
        }
    }

    /**
     * Handles random events that can occur during the year.
     */
    private static void handleRandomEvents() {
        int event = random.nextInt(15);
        switch (event) {
            case 0:
                System.out.println("Oh no! You got sick. Health -20.");
                health -= 20;
                break;
            case 1:
                System.out.println("You found a lottery ticket! Money +$1000.");
                money += 1000;
                break;
            case 2:
                System.out.println("You had a great year! Happiness +15.");
                happiness += 15;
                break;
            case 3:
                System.out.println("You were promoted at work! Money +$5000!");
                money += 5000;
                happiness += 10;
                break;
            case 4:
                System.out.println("You were in an accident. Health -30.");
                health -= 30;
                break;
            default:
                // Nothing special happens this year
                break;
        }
    }
}
