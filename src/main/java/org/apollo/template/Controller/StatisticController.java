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

        //weekBarChart();
        //monthBarChart();
        yearBarChart();
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
            seriesWeek.setName("lålå");


            List<Records> chosenRecords = statisticDAO.readRecords(daysToShow);

            List<String> date = new ArrayList<>(StatisticUtil.datesPeriod(chosenRecords));
            List<Integer> weight = new ArrayList<>(StatisticUtil.weightsPerDayPeriod(chosenRecords));

            System.out.println("antal date: "+ date.size());
            System.out.println("date:");
            for (String day : date){
                System.out.println(day);
            }

            System.out.println("antal vægt: "+ weight.size());
            System.out.println("weight:");
            for (int gram : weight){
                System.out.println(gram);
            }



            for (int i = 0; i < date.size(); i++) {
                System.out.println("??");

                System.out.println("Weight: " + weight.get(i) + "index: " + i);
                System.out.println("date: " + date.get(i) + "index: " + i);

                seriesWeek.getData().add(new XYChart.Data<>(date.get(i), weight.get(i)));
            }

            chartData.add(seriesWeek);
            barChart.setData(chartData);
            root.getChildren().add(barChart);

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
        seriesMonth.setName("lålå");


        List<Records> chosenRecords = statisticDAO.readRecords(daysToShow);

        System.out.println("back again");

        List<String> date = new ArrayList<>(StatisticUtil.datesPeriod(chosenRecords));
        List<Integer> weight = new ArrayList<>(StatisticUtil.weightsPerDayPeriod(chosenRecords));


        System.out.println("antal date: "+ date.size());
        System.out.println("date:");
        for (String day : date){
            System.out.println(day);
        }

        System.out.println("antal vægt: "+ weight.size());
        System.out.println("weight:");
        for (int gram : weight){
            System.out.println(gram);
        }



        if (date.size() < daysToShow){

            List<String> datesPeriod = new ArrayList<>();
            List<Integer> weightsPeriod = new ArrayList<>(daysToShow);

            LocalDate currentDate = LocalDate.now();
            LocalDate backwardsDate = currentDate.minusDays(daysToShow);
            LocalDate startDate = currentDate.minusDays(1);

            System.out.println("current date: " + currentDate);
            System.out.println("back date: " + backwardsDate);
            System.out.println("start date: " + startDate);

            for (int i = 1; i <= daysToShow; i++){

                LocalDate test = currentDate.minusDays(i);
                // parse to String
                String dateToAdd = String.valueOf(Date.valueOf(test));
                datesPeriod.add(dateToAdd);
            }

            // reversing the order to correctly populate the x-axis
            Collections.reverse(datesPeriod);

            int længde = datesPeriod.size() - weight.size();
            System.out.println("længde: " + længde);

            int j = 0;

            for (int i = 0; i < datesPeriod.size(); i++) {

                if (i < længde) {
                    weightsPeriod.add(0);
                }
                else {
                    weightsPeriod.add(weight.get(j));
                    j++;
                }
            }


            for (Integer newWeight : weightsPeriod){
                System.out.println(newWeight);
            }

            System.out.println("YO!");

            for (int i = 0; i < datesPeriod.size(); i++) {

                seriesMonth.getData().add(new XYChart.Data<>(datesPeriod.get(i), weightsPeriod.get(i)));

                System.out.println("Weight: " + weightsPeriod.get(i) + " index: " + i);
                System.out.println("date: " + datesPeriod.get(i) + " index: " + i);
            }

            System.out.println("???");

        }




        else {

            for (int i = 0; i < date.size(); i++) {
                seriesMonth.getData().add(new XYChart.Data<>(date.get(i), weight.get(i)));

                System.out.println("Weight: " + weight.get(i) + "index: " + i);
                System.out.println("date: " + date.get(i) + "index: " + i);
            }
        }

        System.out.println("huhu");
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
        seriesYear.setName("lålå");


        List<Records> chosenRecords = statisticDAO.readRecords(daysToShow);

        List<String> month = new ArrayList<>(StatisticUtil.prev12MonthsByName());
        List<Integer> weight = new ArrayList<>(StatisticUtil.weightsPerMonth(chosenRecords));

        System.out.println(weight.size());


        if (weight.size() < month.size()) {
            System.out.println("HALLO?");
            List<Integer> hola = new ArrayList<>(month.size());

            int j = 0;

            for (int i = 0; i < month.size(); i++) {
                System.out.println("ER DU MED?");
                if (i < (month.size() - weight.size())) {
                    hola.add(0);
                    System.out.println("MÅSKE?");
                } else {
                    hola.add(weight.get(j));
                    j++;
                    System.out.println("HELT VILDT :D");
                }
            }

            System.out.println("OUT");
            for (Integer newWeight : hola) {
                System.out.println(newWeight);
            }


            System.out.println("YO!");


            for (int i = 0; i < month.size(); i++) {

                seriesYear.getData().add(new XYChart.Data<>(month.get(i), hola.get(i)));

                System.out.println("date: " + month.get(i) + " index: " + i);
                System.out.println("weight: " + hola.get(i) + " index: " + i);
            }

            System.out.println("???");


        }

        else {

            for (int i = 0; i < month.size(); i++) {
                seriesYear.getData().add(new XYChart.Data<>(month.get(i), weight.get(i)));
            }

        }
            chartData.add(seriesYear);
            barChart.setData(chartData);
            root.getChildren().add(barChart);
        }



}
