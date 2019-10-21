package sample;

public class BubbleSort {

    public static int[] startSort(int[] array) {
        // while the array is not sorted keep stepping through the arrays positions checking if array[i] holds a value
        // greater than array[i+1], if position i holds a greater value than position i+1, swap the positions placing
        // the greater value at pos i + 1.
        int iterations = 0;
        int swaps = 0;
        boolean sorted = false;
        int temp;

        while(!sorted) {
            sorted = true;

            for (int i = 0; i < array.length - 1; i++) {
                iterations++;

                if (array[i] > array[i + 1]) {
                    temp = array[i];
                    array[i] = array[i+1];
                    array[i+1] = temp;
                    sorted = false;
                    swaps++;

                    return array;
                }

            }
        }
        return array;
    }
}
