package sample;

public class ArrayFunctions {

    public static int[] createArray(int minValue, int maxValue, int totalValues) {
        // creates array with random numbers between minValue and

        int[] unSortedArray = new int[totalValues];
        int tempNum;

        for (int i = 0; i <= totalValues - 1; i++) {
            tempNum = (int) ((maxValue - minValue) * Math.random() + minValue);
            unSortedArray[i] = tempNum;
        }

        return unSortedArray;
    }
}
