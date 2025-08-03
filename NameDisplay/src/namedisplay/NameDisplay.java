package namedisplay;


public class NameDisplay {
    
    public static void main(String[] args) {
        String name = "Ronin";
        String surname = "Mauries";
        int age = 21;

        System.out.println("Displaying name 5 times:");
        for (int i = 0; i < 5; i++) {
            System.out.println(name);
        }

        System.out.println("\nDisplaying surname 10 times:");
        for (int i = 0; i < 10; i++) {
            System.out.println( surname);
        }

        System.out.println("\nDisplaying age 15 times:");
        for (int i = 0; i < 15; i++) {
            System.out.println(age);
        }
    }
}
