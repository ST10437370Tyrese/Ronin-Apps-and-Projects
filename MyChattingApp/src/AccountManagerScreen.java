package mychatapp;

public class AccountManagerScreen {
    public static String createdUsername;
    public static String createdPassword;

    public static boolean loginUser(String username, String password) {
        return username != null && password != null && 
               username.equals(createdUsername) && password.equals(createdPassword);
    }

    public static String returnLoginStatus(boolean isLoggedIn, String username) {
        return isLoggedIn ? "Welcome " + username + ", it is great to see you again." :
                           "Username or password incorrect, please try again.";
    }

    public static boolean checkCellphoneNumber(String cell) {
        return cell != null && cell.matches("0\\d{9}") && cell.startsWith("0");
    }
}