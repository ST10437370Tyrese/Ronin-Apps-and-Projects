
package iftoswitchtask;
import java.util.Scanner;

public class IfToSwitchTask {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Choose a Game Difficulty:");
        System.out.println("(easy, medium, hard)");
        System.out.print("Enter difficulty: ");
        String difficulty = input.nextLine();

        switch (difficulty) {
            case "easy":
                System.out.println("You selected EASY mode. Enemies are slow and weak.");
                break;
            case "medium":
                System.out.println("You selected MEDIUM mode. Balanced difficulty.");
                break;
            case "hard":
                System.out.println("You selected HARD mode. Good luck!");
                break;
            default:
                System.out.println("Invalid difficulty selected.");
        }
        input.close();
    }
}