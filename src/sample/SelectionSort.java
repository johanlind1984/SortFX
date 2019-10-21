package sample;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SelectionSort {

    public static int[] startSort(int[] array) {
        // outer forloop iterates through the array, storing a value at position i in varible min, stores position i in
        // minId. Inner forloop starts at position i+1 and iterates through the array checking if position j is less
        // than position min (which is position i in array). If the value at position j is less than position min,
        // set min to hold position j instead, and set varible min to the value at position j. When the inner forloop
        // is completed the min hold the lowest value in the array and the position of the lowest value in the array.
        // After the inner forloop ends, swap array[min] with array[i].

        boolean swapped = false;

        for (int i = 0; i < array.length; i++) {
            int min = array[i];
            int minId = i;

            if(swapped) {
                return array;
            }

            for (int j = i+1; j < array.length; j++) {
                if (array[j] < min) {
                    min = array[j];
                    minId = j;
                    swapped = true;
                }
            }

            int temp = array[i];
            array[i] = min;
            array[minId] = temp;


        }

        return array;
    }
}
