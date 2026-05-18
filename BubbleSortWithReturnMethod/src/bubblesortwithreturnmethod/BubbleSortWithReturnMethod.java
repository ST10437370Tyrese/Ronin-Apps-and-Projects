package bubblesortwithreturnmethod;

public class BubbleSortWithReturnMethod {

    public static void main(String[] args) {
        // Declare input array
        int[] numbersArr = {7, 2, 5, 3};

        // Call method and get sorted array
        int[] sortedArr = sortAscending(numbersArr);

        // Display sorted array
        for (int num : sortedArr) {
            System.out.println(num + " ");
        }
    }

    public static int[] sortAscending(int[] numbersArr) {
        int temp;

        // Bubble Sort in ascending order
        for (int j = 0; j < numbersArr.length - 1; j++) {
            for (int i = 0; i < numbersArr.length - 1 - j; i++) {
                if (numbersArr[i] > numbersArr[i + 1]) {
                    // Swap
                    temp = numbersArr[i];
                    numbersArr[i] = numbersArr[i + 1];
                    numbersArr[i + 1] = temp;
                }
            }
        }

        return numbersArr;
    }
}
