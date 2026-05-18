package bubblesortwithmethods;

public class BubbleSortWithMethods {

    public static void main(String[] args) {
        // Declaration
        int[] numbersArr = {5, 3, 1};

        sortAscending(numbersArr);
    }

    public static void sortAscending(int[] numberArr) {
        int temp;

        // Bubble Sort in ascending order
        for (int j = 0; j < numberArr.length - 1; j++) {
            for (int i = 0; i < numberArr.length - 1 - j; i++) {
                if (numberArr[i] > numberArr[i + 1]) {
                    // Swap
                    temp = numberArr[i];
                    numberArr[i] = numberArr[i + 1];
                    numberArr[i + 1] = temp;
                }
            }
        }

        // Display
        for (int i = 0; i < numberArr.length; i++) {
            System.out.println(numberArr[i] + " ");
        }
    }
}
