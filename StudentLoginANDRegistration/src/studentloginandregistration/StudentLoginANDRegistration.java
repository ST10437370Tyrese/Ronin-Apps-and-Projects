package studentloginandregistration;
import java.util.Scanner;

public class StudentLoginANDRegistration {
    public static void main(String[] args) {
        login();  
    }

    public static void login() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter password: "); 
        String password = scanner.nextLine();

        if (username.equals("student") && password.equals("vc123")) {
            System.out.println("Login successful.");
            registerCourse(); 
        } else {
            System.out.println("Access Denied");
            System.exit(0); 
        }
    }

    public static void registerCourse() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter your full name: ");
        String name = scanner.nextLine();

        System.out.println("Please select a course:");
        System.out.println("1. Information Technology");
        System.out.println("2. Business Management");
        System.out.println("3. Graphic Design");

        System.out.print("Enter your choice (1-3): ");
        int choice = scanner.nextInt();

        String course = "";
        if (choice == 1) {
            course = "Information Technology";
        } else if (choice == 2) {
            course = "Business Management";
        } else if (choice == 3) {
            course = "Graphic Design";
        } else {
            System.out.println("Invalid choice.");
            return; 
        }

        System.out.println("Thank you " + name + ", you have successfully registered for " + course + ".");
    }
}
