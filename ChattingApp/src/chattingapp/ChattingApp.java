
package chattingapp;
import java.util.Scanner;

public class ChattingApp {

    private String registeredName;
    private String registeredEmail;
    private String registeredID;
    private String checkInStatus;

    public boolean validateName(String fullName) {
        return fullName.trim().split("\\s+").length >= 2;
    }

    public boolean validateEmail(String email) {
        return email.contains("@") && email.lastIndexOf(".") > email.indexOf("@");
    }

    public boolean validateID(String id) {
        return id.matches("\\d{13}");
    }

    public String registerGuest() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();
        if (!validateName(fullName)) {
            return "Name must contain both first and last name.";
        }
        System.out.println("Name successfully recorded.");

        System.out.print("Enter email address: ");
        String email = scanner.nextLine();
        if (!validateEmail(email)) {
            return "Email format is invalid.";
        }
        System.out.println("Email successfully captured.");

        System.out.print("Enter South African ID number: ");
        String id = scanner.nextLine();
        if (!validateID(id)) {
            return "Invalid ID number. It must be exactly 13 digits.";
        }
        System.out.println("ID number successfully recorded.");

        registeredName = fullName;
        registeredEmail = email;
        registeredID = id;

        return "Registration successful.";
    }

    public boolean checkIn(String fullName, String id) {
        if (registeredName != null && registeredID != null &&
            registeredName.equalsIgnoreCase(fullName.trim()) &&
            registeredID.equals(id.trim())) {
            checkInStatus = "Welcome " + registeredName + ", your badge has been created. Enjoy the event!";
            return true;
        } else {
            checkInStatus = "Check-in failed. Name or ID incorrect.";
            return false;
        }
    }

    public String returnCheckInStatus() {
        return checkInStatus;
    }

    public static void main(String[] args) {
        ChattingApp event = new ChattingApp();
        String regStatus = event.registerGuest();
        System.out.println(regStatus);

        if (regStatus.equals("Registration successful.")) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n--- Check-In ---");
            System.out.print("Confirm full name: ");
            String name = scanner.nextLine();
            System.out.print("Confirm ID number: ");
            String id = scanner.nextLine();

            event.checkIn(name, id);
            System.out.println(event.returnCheckInStatus());
        }
    }
}
