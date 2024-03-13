package org.apollo.template.Controller;

import Statistic.StatisticDAO;
import Statistic.StatisticDAODB;
import Statistic.StatisticUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.apollo.template.Model.Records;
import org.apollo.template.Service.Debugger.DebugMessage;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class StatisticController implements Initializable {

    private static StatisticController INSTANCE = new StatisticController();
    @FXML
    private AnchorPane root;
    @FXML
    private VBox scene;
    @FXML
    private BarChart barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private ChoiceBox choiceBox;
    @FXML
    private Button btnExport;
    @FXML
    private Label label_Min, label_Max, label_Average, label_empties;
    @FXML
    private TextField textField_Min, textField_Max, textField_Average;

    private StatisticDAO statisticDAO = new StatisticDAODB();

    private int daysToShow;



    private StatisticController() {
        if (INSTANCE == null) {
            DebugMessage.info(this, "Creating an instance of " + this);
        }
    }

    public static StatisticController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StatisticController();
        }
        return INSTANCE;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        choiseBoxSetVal();
        weekBarChart();
        //monthBarChart();
        //yearBarChart();

    }





    private void weekBarChart() {
        daysToShow = 7;

        try {
            // label x-axis
            barChart.getXAxis().setLabel("Date");
            // label y-axis
            barChart.getYAxis().setLabel("Total food waste per day");
            // title of graph
            barChart.setTitle("Food waste for the last week");


            ObservableList<XYChart.Series<String, Number>> chartData = FXCollections.observableArrayList();

            XYChart.Series<String, Number> seriesWeek = new XYChart.Series<>();
            seriesWeek.setName("Waste");


            List<Records> chosenRecords = statisticDAO.readRecords(daysToShow);

            List<String> date = new ArrayList<>(StatisticUtil.datesPeriod(chosenRecords));
            List<Integer> weight = new ArrayList<>(StatisticUtil.weightsPerDayPeriod(chosenRecords));


            for (int i = 0; i < date.size(); i++) {
                seriesWeek.getData().add(new XYChart.Data<>(date.get(i), weight.get(i)));
            }

            chartData.add(seriesWeek);
            barChart.setData(chartData);
            root.getChildren().add(barChart);

            setTextfieldStatistic(weight);


        }catch (Exception e){
            System.out.println("Error");
        }
    }



    private void monthBarChart() {
        daysToShow = 31;

        // label x-axis
        barChart.getXAxis().setLabel("Date");
        // label y-axis
        barChart.getYAxis().setLabel("Total food waste per day");
        // title of graph
        barChart.setTitle("Food waste for the last month");


        ObservableList<XYChart.Series<String,Number>> chartData = FXCollections.observableArrayList();

        XYChart.Series<String, Number> seriesMonth = new XYChart.Series<>();
        seriesMonth.setName("Waste");


        List<Records> chosenRecords = statisticDAO.readRecords(daysToShow);

        List<String> date = new ArrayList<>(StatisticUtil.datesPeriod(chosenRecords));
        List<Integer> weight = new ArrayList<>(StatisticUtil.weightsPerDayPeriod(chosenRecords));


        if (date.size() < daysToShow){

            List<String> datesPeriod = new ArrayList<>();
            List<Integer> weightsPeriod = new ArrayList<>(daysToShow);

            LocalDate currentDate = LocalDate.now();

            // creates a List with the dates for the last month represent
            for (int i = 1; i <= daysToShow; i++){

                LocalDate newDate = currentDate.minusDays(i);
                // parse to String
                String dateToAdd = String.valueOf(Date.valueOf(newDate));
                datesPeriod.add(dateToAdd);
            }

            // reversing the order to correctly populate the x-axis
            Collections.reverse(datesPeriod);


            // creates a List with the weigths from the DB represent and "0" if no data represent the former date
            int j = 0;

            for (int i = 0; i < datesPeriod.size(); i++) {

                if (i < datesPeriod.size() - weight.size()) {
                    weightsPeriod.add(0);
                }
                else {
                    weightsPeriod.add(weight.get(j));
                    j++;
                }
            }


            for (int i = 0; i < datesPeriod.size(); i++) {

                seriesMonth.getData().add(new XYChart.Data<>(datesPeriod.get(i), weightsPeriod.get(i)));
            }
            setTextfieldStatistic(weightsPeriod);
        }


        else {
            for (int i = 0; i < date.size(); i++) {
                seriesMonth.getData().add(new XYChart.Data<>(date.get(i), weight.get(i)));
            }

            setTextfieldStatistic(weight);
        }

        chartData.add(seriesMonth);
        barChart.setData(chartData);
        root.getChildren().add(barChart);

    }




    private void yearBarChart() {
        daysToShow = 365;

        // label x-axis
        barChart.getXAxis().setLabel("Month");
        // label y-axis
        barChart.getYAxis().setLabel("Total food waste per month");
        // title of graph
        barChart.setTitle("Food waste for the last year");


        ObservableList<XYChart.Series<String, Number>> chartData = FXCollections.observableArrayList();

        XYChart.Series<String, Number> seriesYear = new XYChart.Series<>();
        seriesYear.setName("Waste");


        List<Records> chosenRecords = statisticDAO.readRecords(daysToShow);

        List<String> month = new ArrayList<>(StatisticUtil.prev12MonthsByName());
        List<Integer> weight = new ArrayList<>(StatisticUtil.weightsPerMonth(chosenRecords));


        if (weight.size() < month.size()) {
            List<Integer> weightPerMonth = new ArrayList<>(month.size());

            int j = 0;

            for (int i = 0; i < month.size(); i++) {
                if (i < (month.size() - weight.size())) {
                    weightPerMonth.add(0);
                } else {
                    weightPerMonth.add(weight.get(j));
                    j++;
                }
            }

            for (int i = 0; i < month.size(); i++) {

                seriesYear.getData().add(new XYChart.Data<>(month.get(i), weightPerMonth.get(i)));
            }

            setTextfieldStatistic(weightPerMonth);
        }

        else {

            for (int i = 0; i < month.size(); i++) {
                seriesYear.getData().add(new XYChart.Data<>(month.get(i), weight.get(i)));
            }

            setTextfieldStatistic(weight);

        }
            chartData.add(seriesYear);
            barChart.setData(chartData);
            root.getChildren().add(barChart);
    }


    private void setTextfieldStatistic(List<Integer> weight) {

        int minVal = StatisticUtil.minWeigth(weight);
        int maxVal = StatisticUtil.maxWeigth(weight);
        double average = StatisticUtil.averageWeight(weight);

        textField_Min.setText(String.valueOf(minVal));
        textField_Max.setText(String.valueOf(maxVal));
        textField_Average.setText(String.valueOf(String.format("%.2f",average)));
    }



    private void choiseBoxSetVal(){

        choiceBox.getItems().addAll("Last 7 days", "Last month", "Last year");
        choiceBox.setValue("Last 7 days");
    }

    private void OnMousePressed() {

        if (choiceBox.getValue().equals("Last month")) {
            handleLastMonthSelection();
        } else if (choiceBox.getValue().equals("Last week")) {
            handleLastWeekSelection();
        } else if (choiceBox.getValue().equals("Last year")) {
            handleLastYearSelection();
        }

    }



    private void handleLastWeekSelection() {
        // reset count of emptyings
        StatisticUtil.cntEmptyings = 1;

        // load week barChart
        weekBarChart();
    }


    private void handleLastMonthSelection() {
        // reset count of emptyings
        StatisticUtil.cntEmptyings = 1;

        // load month barChart
        monthBarChart();
    }


    private void handleLastYearSelection() {
        // reset count of emptyings
        StatisticUtil.cntEmptyings = 1;

        // load year barChart
        yearBarChart();
    }



}
