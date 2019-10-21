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

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

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
        minValue = 0;
        maxValue = 500;
        arraySize = 100;
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

        speed = (102 - sliderSpeed.getValue());

        if(swapsStored.size() < 1) {
            sliderHistory.setMax(0);
        } else {
            sliderHistory.setMax(swapsStored.size()-1);
        }

        xAxis.setLabel("Array position");
        yAxis.setLabel("Position Value");
        XYChart.Series series = new XYChart.Series();

        for (int i = 0; i < arrayToSort.length; i++) {
            String tempString = "" + (i+1);
            series.getData().add(new XYChart.Data(tempString, arrayToSort[i]));
        }

        arrayDiagram.getData().clear();
        arrayDiagram.getData().add(series);
    }

    private void addToHistory() {
        History tempHistory = new History(arrayToSort);
        swapsStored.add(tempHistory);
    }

    private boolean sorted(int[] array) {
        for (int i=0; i < arraySize-1; i++) {
            if(arrayToSort[i] > arrayToSort[i+1]) {
                return false;
            }
        }

        return true;
    }

    @FXML
    private void moveHistory() {
        arrayToSort = swapsStored.get(((int) sliderHistory.getValue())).getHistoryOfSwap();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                updateChart();
            }
        });

    }

    public void bubbleSort(ActionEvent actionEvent) throws IOException, InterruptedException {
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
