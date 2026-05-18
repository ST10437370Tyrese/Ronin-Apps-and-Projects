/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package twodarraysexample2;

import java.util.Scanner;

/**
 *
 * @author lab_services_student
 */
public class TwoDArraysExample2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int[][] numbers = null; // Declare so it's accessible in both cases
        int answer;

        System.out.println("Choose Option:");
        System.out.println("(1) Capture : ");
        System.out.println("(2) Display :");
        System.out.println("(3) Stop :");
        answer = Integer.parseInt(input.nextLine());

        while (answer != 3) {
            switch (answer) {
                case 1:
                    numbers = populate2D(input);
                    break;
                case 2:
                    if (numbers != null) {
                        display2D(numbers);
                    } else {
                        System.out.println("No data captured yet!");
                    }
                    break;
                default:
                    System.out.println("Wrong value entered, please re-enter");
                    break;
            }

            System.out.println("\nChoose Option:");
            System.out.println("(1) Capture : ");
            System.out.println("(2) Display :");
            System.out.println("(3) Stop :");
            answer = Integer.parseInt(input.nextLine());
        }
    }

    public static int[][] populate2D(Scanner input) {
        int[][] numbers = new int[3][2];

        for (int row = 0; row < numbers.length; row++) {
            for (int col = 0; col < numbers[row].length; col++) {
                System.out.print("[" + row + "," + col + "] = ");
                numbers[row][col] = Integer.parseInt(input.nextLine());
            }
        }
        System.out.println("Successfully Captured!!!");
        return numbers;
    }

    public static void display2D(int[][] numbers) {
        for (int row = 0; row < numbers.length; row++) {
            for (int col = 0; col < numbers[row].length; col++) {
                System.out.print(numbers[row][col] + " ");
            }
            System.out.println("");
        }
    }
}
