package volunteerportal;
import java.util.Scanner;

public class VolunteerPortal {


    // Global variables to simulate user "Database"
    static String registeredUsername;
    static String registeredPassword;
    static String registeredFirstName;
    static String registeredLastName;
    static String registeredCell;
    static String registeredEmail;
    static String agreedTerms;

    /**
     * USERNAME: contains underscore and <= 5 characters
     */
    public static boolean checkUsername(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    /**
     * PASSWORD: At least 8 chars, 1 capital, 1 digit, 1 special character
     */
    public static boolean checkPasswordComplexity(String password) {
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        if (password.length() < 8) {
            return false;
        }

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUpper = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(ch)) {
                hasSpecial = true;
            }
        }
        return hasUpper && hasDigit && hasSpecial;
    }

    /**
     * CELLPHONE: starts with +27 and has 11 digits
     */
    public static boolean checkCellphoneNumber(String cell) {
        return cell.startsWith("+27") && cell.length() == 11;
    }

    /**
     * EMAIL: contains "@" and "." with min 6 characters
     */
    public static boolean checkEmailFormat(String email) {
        return email.contains("@") && email.contains(".") && email.length() >= 6;
    }

    /**
     * TERMS: must say "yes"
     */
    public static boolean acceptTerms(String input) {
        return input.equalsIgnoreCase("yes");
    }

    /**
     * REGISTRATION METHOD
     */
    public static boolean registerUser(String username, String password, String cell, String email, String terms) {
        if (!checkUsername(username)) {
            System.out.println("Invalid username: must include an underscore and be 5 characters or less.");
            return false;
        }
        if (!checkPasswordComplexity(password)) {
            System.out.println("Invalid password: must be at least 8 chars, include uppercase, number, and special character.");
            return false;
        }
        if (!checkCellphoneNumber(cell)) {
            System.out.println("Invalid cellphone number: must start with +27 and have 11 digits.");
            return false;
        }
        if (!checkEmailFormat(email)) {
            System.out.println("Invalid email format: must contain '@' and '.' with a minimum length of 6.");
            return false;
        }
        if (!acceptTerms(terms)) {
            System.out.println("You must accept the terms and conditions by typing 'yes'.");
            return false;
        }

        // All details valid. Registration successful!
        registeredUsername = username;
        registeredPassword = password;
        registeredCell = cell;
        registeredEmail = email;
        agreedTerms = terms;
        System.out.println("Registration successful!");
        return true;
    }

    /**
     * LOGIN METHOD
     */
    public static boolean loginUser(String registeredUsername, String registeredPassword) {
        return registeredUsername.equals(VolunteerPortal.registeredUsername) && registeredPassword.equals(VolunteerPortal.registeredPassword);
    }

    /**
     * LOGIN STATUS METHOD
     */
    public static String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Login successful!";
        } else {
            return "Username or password incorrect. Please try again.";
        }
    }

    /**
     * DISPLAY USER PROFILE
     */
    public static void displayUserProfile() {
        System.out.println("\n--- VOLUNTEER PROFILE ---");
        System.out.println("First Name: " + registeredFirstName);
        System.out.println("Last Name: " + registeredLastName);
        System.out.println("Username: " + registeredUsername);
        System.out.println("Cell Phone: " + registeredCell);
        System.out.println("Email: " + registeredEmail);
        System.out.println("Terms Agreed: " + agreedTerms);
        System.out.println("-------------------------");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Step 1: Registration
        System.out.println("--- Volunteer Registration Form ---");
        System.out.print("Enter First Name: ");
        registeredFirstName = input.nextLine();
        System.out.print("Enter Last Name: ");
        registeredLastName = input.nextLine();
        System.out.print("Create Username (underscore & max 5 characters): ");
        String username = input.nextLine();
        System.out.print("Create Password (8+ chars, 1 cap, 1 digit, 1 special): ");
        String password = input.nextLine();
        System.out.print("Enter Cellphone (+27...): ");
        String cell = input.nextLine();
        System.out.print("Enter Email: ");
        String email = input.nextLine();
        System.out.print("Do you accept the terms & conditions? (yes/no): ");
        String terms = input.nextLine();

        if (registerUser(username, password, cell, email, terms)) {
            // Step 2: Login
            System.out.println("\n--- Volunteer Login ---");
            System.out.print("Enter your username: ");
            String loginUsername = input.nextLine();
            System.out.print("Enter your password: ");
            String loginPassword = input.nextLine();

            boolean loginSuccess = loginUser(loginUsername, loginPassword);
            System.out.println(returnLoginStatus(loginSuccess));

            if (loginSuccess) {
                // Step 3: Display Profile
                displayUserProfile();
            }
        }

        input.close();
    }
}