package greeting;

public class Greeting {
   
    public void greet() {   
        System.out.println("Hello! Welcome to Java Methods");
    }
    
    // Method to display a thank you message when the system is quit
    public void quit() {
        System.out.println("Thank you for using the system");
    }

   
    public void addNumbers(int num1, int num2) {
    int sum = num1 + num2;
    System.out.println("The sum is: " + sum);
    }

   
    public int multiply(int num3, int num4) {
    return num3 * num4;
    }
    
    
    public static class Step4 {
        public double calculateArea(double length, double width) {
        return length * width;
        }
        
        public void displayArea(double length, double width) {
        double area = calculateArea(length, width);
        System.out.println("Area: " + area);
        }
    }
    public static void main(String[] args) {
        
      
        Greeting obj = new Greeting();
        obj.greet();
        obj.quit();
        obj.addNumbers(10, 20);
        int result = obj.multiply(5, 4);
        System.out.println("Product is: " + result);

        Step4 step4Obj = new Step4();
        step4Obj.displayArea(5.0, 4.0); 
    }
}
