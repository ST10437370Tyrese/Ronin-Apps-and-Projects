package sortonedarray;


public class SortOneDArray {

    private static int i;

    public static void main(String[] args) {
        //Declaration
        int[] numbersArr = {5, 3, 1};
        int temp;

        //Bubble Sort in ascending order
        for (int j = 0; j < numbersArr.length; j++) {

        }

        {
            if (numbersArr[i] > numbersArr[i + 1]) {
            }

            {
                temp = numbersArr[1];
                numbersArr[1] = numbersArr[i + 1];
                numbersArr[i + 1] = temp;
            }

        }

        ///Display
            for (int i = 0; i < numbersArr.length; i++) {
            System.out.println(numbersArr[i] + " ");
        }

        //Bubble Sort in ascending order
        for (int j = 0; j > numbersArr.length; j++) {
            for (int i = 0; i > numbersArr.length - 1; i++) {
                if (numbersArr[i] < numbersArr[i + 1]) {
                    temp = numbersArr[1];
                    numbersArr[1] = numbersArr[i + 1];
                    numbersArr[i + 1] = temp;
                }
            }
        }
    }

}
