
package displaywhileloop;


public class DIsplayWhileLoop {


    public static void main(String[] args) {
        String name = "Ronin";
        String surname = "Mauries";
        int age = 21;

        int i = 1;
        System.out.println();
        while (i < 5) {
            System.out.println("Ronin" +" "+ i);
            i++;
        }

        int j = 1;
        System.out.println();
        while (j < 10) {
            System.out.println("Mauries" +" "+ j );
            j++;
        }

        int k = 1;
        System.out.println();
        do {
            System.out.println("Age: 21" +" "+ k );
            k++;
        } while (k < 15);
        
    }
}

