package sample;

public class InsertionSort {

    public static int[] startSort(int[] array) {
        // Outer forloop iterates through the array, nested whileloop iterates backwards from position i-1 and
        // looks for a lower value than position array[i], if found place the lower value at position [j+1].

        for (int i = 1; i < array.length; i++) {
            int current = array[i];
            int j = i - 1;

            if(j >= 0 && current < array[j]) {
                array[j+1] = array[j];
                j = j -1;
            }
            array[j+1] = current;

        }
        return array;
    }

}
