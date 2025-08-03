
package switchtoiftask;
import java.util.Scanner;

public class SwitchToIfTask {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("🔐 Select Access Level:");
        System.out.println("1 – Student");
        System.out.println("2 – Lecturer");
        System.out.println("3 – Admin");
        System.out.println("4 – Guest");
        System.out.print("Enter your option: ");
        int role = scanner.nextInt();

        if (role == 1) {
            System.out.println("Access granted: Student dashboard");
        } else if (role == 2) {
            System.out.println("Access granted: Lecturer tools");
        } else if (role == 3) {
            System.out.println("Access granted: Admin panel");
        } else if (role == 4) {
            System.out.println("Limited access: Guest view only");
        } else {
            System.out.println("Invalid selection.");
        }
    }
}
