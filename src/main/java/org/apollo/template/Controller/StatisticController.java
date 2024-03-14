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
    private Button btnExport, btn_Confirm;
    @FXML
    private Label label_Min, label_Max, label_Average, label_empties, label_Min_Res, label_Max_Res, label_Average_Res;

    private StatisticDAO statisticDAO = new StatisticDAODB();
    private int binID = 1;
    private int daysToShow;
    private String wasteDesignation = "in grams";



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
        yesterdayBarChart();
        //weekBarChart();
        //monthBarChart();
        //yearBarChart();

        root.getChildren().add(barChart);
    }


    public void onBtnConfirmClick(){

        barChart.getData().clear();

        int indexChosen = choiceBox.getSelectionModel().getSelectedIndex();

        switch (indexChosen){

            case 0:
                handleYesterdaySelection();
                break;

            case 1:
                handleLastWeekSelection();
                break;

            case 2:
                handleLastMonthSelection();
                break;

            case 3:
                handleLastYearSelection();
                break;
        }
    }




    private void yesterdayBarChart() {
        daysToShow = 1;
        String yesterdayDate = dateYesterday();


        try {
            // label x-axis
            barChart.getXAxis().setLabel("Time");
            // label y-axis
            barChart.getYAxis().setLabel("Food waste ");
            // title of graph
            barChart.setTitle(String.format("Food waste yesterday\n\t%s", yesterdayDate));


            ObservableList<XYChart.Series<String, Number>> chartData = FXCollections.observableArrayList();

            XYChart.Series<String, Number> seriesYesterday = new XYChart.Series<>();
            seriesYesterday.setName("Waste " + wasteDesignation);


            List<Records> chosenRecords = statisticDAO.readRecords(daysToShow, binID);

            List<String> time = new ArrayList<>(StatisticUtil.timeDay(chosenRecords));
            List<Integer> weight = new ArrayList<>(StatisticUtil.timeWeights(chosenRecords));


            for (int i = 0; i < time.size(); i++) {
                seriesYesterday.getData().add(new XYChart.Data<>(time.get(i), weight.get(i)));
            }

            chartData.add(seriesYesterday);
            barChart.setData(chartData);

            setLabelStatistic(weight);


        }catch (Exception e){
            System.out.println("[yesterday barChart] Error");
        }
    }

    private String dateYesterday() {
        LocalDate date = LocalDate.now().minusDays(1);
        return String.valueOf(date);
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
            seriesWeek.setName("Waste " + wasteDesignation);


            List<Records> chosenRecords = statisticDAO.readRecords(daysToShow, binID);

            List<String> date = new ArrayList<>(StatisticUtil.datesPeriod(chosenRecords));
            List<Integer> weight = new ArrayList<>(StatisticUtil.weightsPerDayPeriod(chosenRecords));


            for (int i = 0; i < date.size(); i++) {
                seriesWeek.getData().add(new XYChart.Data<>(date.get(i), weight.get(i)));
                //System.out.println(date.get(i));
                //System.out.println(weight.get(i));
            }



            chartData.add(seriesWeek);
            barChart.setData(chartData);

            setLabelStatistic(weight);


        }catch (Exception e){
            System.out.println("[week barChart] Error");
        }
    }



    private void monthBarChart() {
        daysToShow = 31;

        try{
            // label x-axis
            barChart.getXAxis().setLabel("Date");
            // label y-axis
            barChart.getYAxis().setLabel("Total food waste per day");
            // title of graph
            barChart.setTitle("Food waste for the last month");


            ObservableList<XYChart.Series<String,Number>> chartData = FXCollections.observableArrayList();

            XYChart.Series<String, Number> seriesMonth = new XYChart.Series<>();
            seriesMonth.setName("Waste " + wasteDesignation);


            List<Records> chosenRecords = statisticDAO.readRecords(daysToShow, binID);

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
                setLabelStatistic(weightsPeriod);
            }


            else {
                for (int i = 0; i < date.size(); i++) {
                    seriesMonth.getData().add(new XYChart.Data<>(date.get(i), weight.get(i)));
                }

                setLabelStatistic(weight);
            }

            chartData.add(seriesMonth);
            barChart.setData(chartData);

        }catch (Exception e){
            System.out.println("[month barChart] Error");
        }
    }




    private void yearBarChart() {
        daysToShow = 365;

        try {
            // label x-axis
            barChart.getXAxis().setLabel("Month");
            // label y-axis
            barChart.getYAxis().setLabel("Total food waste per month");
            // title of graph
            barChart.setTitle("Food waste for the last year");


            ObservableList<XYChart.Series<String, Number>> chartData = FXCollections.observableArrayList();

            XYChart.Series<String, Number> seriesYear = new XYChart.Series<>();
            seriesYear.setName("Waste " + wasteDesignation);


            List<Records> chosenRecords = statisticDAO.readRecords(daysToShow, binID);

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

                setLabelStatistic(weightPerMonth);
            } else {

                for (int i = 0; i < month.size(); i++) {
                    seriesYear.getData().add(new XYChart.Data<>(month.get(i), weight.get(i)));
                }

                setLabelStatistic(weight);

            }
            chartData.add(seriesYear);
            barChart.setData(chartData);

        }catch (Exception e){
            System.out.println("[year barChart] Error");
        }
    }


    private void setLabelStatistic(List<Integer> weight) {

        int minVal = StatisticUtil.minWeigth(weight);
        int maxVal = StatisticUtil.maxWeigth(weight);
        double average = StatisticUtil.averageWeight(weight);

        label_Min_Res.setText((String.format("%d %s",minVal, "grams")));
        label_Max_Res.setText((String.format("%d %s",maxVal, "grams")));
        label_Average_Res.setText((String.format("%.1f %s",average, "grams")));
    }



    private void choiseBoxSetVal(){

        choiceBox.getItems().addAll("Yesterday", "Last 7 days", "Last month", "Last year");
        choiceBox.setValue("Yesterday");
    }



    private void handleYesterdaySelection() {
        // reset count of emptyings
        StatisticUtil.cntEmptyings = 1;

        // load week barChart
        yesterdayBarChart();
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
