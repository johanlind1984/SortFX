package sample;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Controller {
    private int arraySize;
    private int minValue;
    private int maxValue;
    private double speed;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private ArrayList<History> swapsStored;
    private static int[] arrayToSort;


    @FXML
    Slider sliderSpeed;
    @FXML
    Slider sliderHistory;
    @FXML
    BarChart<Object, ObservableList> arrayDiagram;
    @FXML
    Button buttonGenerateArray;
    @FXML
    Button buttonBubbleSort;
    @FXML
    Button buttonInserstionSort;
    @FXML
    Button buttonSelectionSort;
    @FXML
    Button buttonMergeSort;

    @FXML
    private void initialize() {
        // Initializer, sets all values to standard values and creates a array with random numbers feel fre to try som
        // changes out.

        minValue = 0;
        maxValue = 500;
        arraySize = 250;
        sliderSpeed.setValue(50.0);
        speed = (102 - sliderSpeed.getValue())*5;
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        arrayToSort = new int[arraySize];
        swapsStored = new ArrayList<>();
        arrayToSort = ArrayFunctions.createArray(minValue, maxValue, arraySize);
        arrayDiagram.setBarGap(1);
        arrayDiagram.setCategoryGap(0);
        arrayDiagram.setAnimated(false);
        sliderHistory.setMax(swapsStored.size());
        updateChart();
    }

    public void updateChart(){
        // Updates the UI: copies array into BarChart, checks slider to adjust speed and sets the sliderHistory max value to the number of
        // values held in swapsStored if value does not exceed 2000 (2000 is max for sliders).

        speed = (102 - sliderSpeed.getValue());

        // Checks if the the history has more than one position, else slider max is 0. If more than 2000 entries, max
        // value is 2000.
        if(swapsStored.size() < 1) {
            sliderHistory.setMax(0);
        } else if (swapsStored.size() > 2000){
            sliderHistory.setMax(2000);
        } else {
            sliderHistory.setMax(swapsStored.size()-1);
        }

        // Creates a new series for BarChart
        xAxis.setLabel("Array position");
        yAxis.setLabel("Position Value");
        XYChart.Series series = new XYChart.Series();

        // copies the array values to the series from the array
        for (int i = 0; i < arrayToSort.length; i++) {
            String tempString = "" + (i+1);
            series.getData().add(new XYChart.Data(tempString, arrayToSort[i]));
        }


        // clears the BarChart before adding the new series.
        arrayDiagram.getData().clear();

        // adds the new series
        arrayDiagram.getData().add(series);
    }

    private void addToHistory() {
        // stores the action made (swap) in a arraylist so that the user can step through the swaphistory later.
        History tempHistory = new History(arrayToSort);
        swapsStored.add(tempHistory);
    }

    private boolean sorted(int[] array) {
        // Checks if the array is sorted and returns true or false.
        for (int i=0; i < arraySize-1; i++) {
            if(arrayToSort[i] > arrayToSort[i+1]) {
                return false;
            }
        }

        return true;
    }

    @FXML
    private void moveHistory() {
        // Not finished
        // After a sort the user can check each step in the sortingalgorithm with the slider. Slider is hidden at the
        // moment since i haven't got this working yet.

        arrayToSort = swapsStored.get(((int) sliderHistory.getValue())).getHistoryOfSwap();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                updateChart();
            }
        });

    }

    public void bubbleSort(ActionEvent actionEvent) throws IOException, InterruptedException {
        // Starts bubblesorting in a new thread. Updates UI via GUI thread and runLater.
        new Thread(() -> {
        while (!sorted(arrayToSort)) {
            try {
                Thread.sleep(((int) speed));
                arrayToSort = BubbleSort.startSort(arrayToSort);
                addToHistory();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    updateChart();
                }
            });
        }
    }).start();
    }

    public void inserstionSort(ActionEvent actionEvent) {
        // Starts insertionsorting in a new thread. Updates UI via GUI thread and runLater.
        new Thread(() -> {
            while (!sorted(arrayToSort)) {
                try {
                    Thread.sleep(((int) speed * 5));
                    arrayToSort = InsertionSort.startSort(arrayToSort);
                    addToHistory();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        updateChart();
                    }
                });
            }
        }).start();
    }

    public void selectionSort(ActionEvent actionEvent) {
        // Starts selectionsorting in a new thread. Updates UI via GUI thread and runLater.
        new Thread(() -> {
            while (!sorted(arrayToSort)) {
                try {
                    Thread.sleep(((int) speed*5));
                    arrayToSort = SelectionSort.startSort(arrayToSort);
                    addToHistory();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        updateChart();
                    }
                });
            }
        }).start();
    }

    public void mergeSort(ActionEvent actionEvent) {
        // not finished. Probably needs a custom updateChart method since mergesort differs too much from selection-,
        // bubble- and insertionsort.
        // Starts mergesorting in a new thread. Updates UI via GUI thread and runLater.
        new Thread(() -> {
            while (!sorted(arrayToSort)) {
                try {
                    Thread.sleep(((int) speed*50));
                    arrayToSort = MergeSort.mergeSort(arrayToSort, 0, arrayToSort.length-1);
                    addToHistory();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        updateChart();
                    }
                });
            }
        }).start();
    }

    public void generateArray(ActionEvent actionEvent) {
        arrayToSort = ArrayFunctions.createArray(minValue, maxValue, arraySize);
        swapsStored = new ArrayList<>();
        updateChart();

    }
}
