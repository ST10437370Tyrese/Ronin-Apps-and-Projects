// Conceptual Test Cases (not part of the MyChatApp class)
public class MyChatAppTests {

    public static void main(String[] args) {
        System.out.println("--- Testing checkUsername ---");
        assertEqual(MyChatApp.checkUsername("user_1"), true, "Test 1: Valid username");
        assertEqual(MyChatApp.checkUsername("toolong_"), false, "Test 2: Username too long");
        assertEqual(MyChatApp.checkUsername("nouser"), false, "Test 3: No underscore");
        assertEqual(MyChatApp.checkUsername("_only"), true, "Test 4: Underscore at the beginning");
        assertEqual(MyChatApp.checkUsername("user_"), true, "Test 5: Underscore at the end");
        assertEqual(MyChatApp.checkUsername(null), false, "Test 6: Null username");
        assertEqual(MyChatApp.checkUsername("u_ser1"), false, "Test 7: More than one underscore");
        assertEqual(MyChatApp.checkUsername("kyle!!!!!"), false, "Test 8: Username incorrectly formatted (kyle!!!!!)");

        System.out.println("\n--- Testing checkPasswordComplexity ---");
        assertEqual(MyChatApp.checkPasswordComplexity("Ch&&sec@ke99!"), true, "Test 9: Valid password");
        assertEqual(MyChatApp.checkPasswordComplexity("password"), false, "Test 10: Password does not meet complexity requirements (no capital, no number, no special)");
        assertEqual(MyChatApp.checkPasswordComplexity("Short1$A"), false, "Test 11: Password too short");
        assertEqual(MyChatApp.checkPasswordComplexity("NOUPPER123$"), false, "Test 12: No uppercase");
        assertEqual(MyChatApp.checkPasswordComplexity("noupper123$"), false, "Test 13: No uppercase");
        assertEqual(MyChatApp.checkPasswordComplexity("PassABC$"), false, "Test 14: No digit");
        assertEqual(MyChatApp.checkPasswordComplexity("Pass123A"), false, "Test 15: No special character");
        assertEqual(MyChatApp.checkPasswordComplexity("PASS123$"), false, "Test 16: No lowercase (assuming lowercase is a typical requirement)");
        assertEqual(MyChatApp.checkPasswordComplexity(null), false, "Test 17: Null password");

        System.out.println("\n--- Testing checkCellphoneNumber ---");
        assertEqual(MyChatApp.checkCellphoneNumber("+27838968976"), false, "Test 18: Valid international format (not the current requirement)"); // Based on current code's requirement
        assertEqual(MyChatApp.checkCellphoneNumber("08966553"), false, "Test 19: Incorrectly formatted cell number (too short)");
        assertEqual(MyChatApp.checkCellphoneNumber("0123456789"), true, "Test 20: Valid SA number");
        assertEqual(MyChatApp.checkCellphoneNumber("1234567890"), false, "Test 21: Does not start with 0");
        assertEqual(MyChatApp.checkCellphoneNumber("012345678"), false, "Test 22: Too short");
        assertEqual(MyChatApp.checkCellphoneNumber("012345678901"), false, "Test 23: Too long");
        assertEqual(MyChatApp.checkCellphoneNumber(null), false, "Test 24: Null cell number");

        System.out.println("\n--- Testing loginUser (requires a successful registration first) ---");
        MyChatApp.createdUsername = "test_1";
        MyChatApp.createdPassword = "ValidPwd1$";
        assertEqual(MyChatApp.loginUser("test_1", "ValidPwd1$"), true, "Test 25: Login Successful");
        assertEqual(MyChatApp.loginUser("wrong_user", "ValidPwd1$"), false, "Test 26: Login Failed - Incorrect username");
        assertEqual(MyChatApp.loginUser("test_1", "WrongPwd"), false, "Test 27: Login Failed - Incorrect password");
        assertEqual(MyChatApp.loginUser(null, "ValidPwd1$"), false, "Test 28: Login Failed - Null login username");
        assertEqual(MyChatApp.loginUser("test_1", null), false, "Test 29: Login Failed - Null login password");
    }

    public static void assertEqual(boolean actual, boolean expected, String message) {
        if (actual == expected) {
            System.out.println("PASS: " + message);
        } else {
            System.err.println("FAIL: " + message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }
}