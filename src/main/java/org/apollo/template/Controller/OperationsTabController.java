package org.apollo.template.Controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import org.apollo.template.Domain.Bin;
import org.apollo.template.Domain.Resturent;
import org.apollo.template.Service.Bin.BinDAO;
import org.apollo.template.Service.Bin.BinDAODB;
import org.apollo.template.Service.BinStatus.BinStatusUtil;
import org.apollo.template.Service.Debugger.DebugMessage;
import org.apollo.template.Service.Resturent.ResturenDAODB;
import org.apollo.template.Service.Resturent.ResturentDAO;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OperationsTabController implements Initializable {

    private static OperationsTabController INSTANCE = new OperationsTabController();

    @FXML
    private BorderPane borderPane;
    @FXML
    private ChoiceBox<Resturent> cbLocations;
    @FXML
    private ListView<Integer> LVAllBins;
    @FXML
    private Button btnConfirm;
    @FXML
    private Label lbTotal, lbTotalCap, lbLifeTimeWaste;
    @FXML
    private PieChart OperationsPieChart;


    private List<Bin> listOfFoundBins;
    private String noOfBins;

    private OperationsTabController(){
        if (INSTANCE == null){
            DebugMessage.info(this, "Creating an instance of " + this);
        }
    }

    public static OperationsTabController getInstance(){

        if (INSTANCE == null){
            INSTANCE = new OperationsTabController();
        }

        return INSTANCE;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        populateChoiceBox();

    }

    /**
     * Method for populating the location choice box
     */

    private void populateChoiceBox(){

        ResturentDAO resturentDAO = new ResturenDAODB();

        cbLocations.getItems().addAll(resturentDAO.readall());

    }

    @FXML
    private void onActionBtnConfirm(){

        if(cbLocations.getSelectionModel().getSelectedItem() != null){
            LVAllBins.getItems().clear();
            populateOverviewStatus();
            populatePieChart();
        }

    }


    private void populateOverviewStatus(){

        BinDAO binDAO = new BinDAODB();

        int selected = cbLocations.getSelectionModel().getSelectedItem().getResturentID();
        List<Integer> binIDList = new ArrayList<>();

        listOfFoundBins = binDAO.readAllFromResturentID(selected);
        listOfFoundBins.forEach(b -> binIDList.add(b.getBinID()));

        LVAllBins.getItems().addAll(binIDList);

        noOfBins = String.valueOf(binIDList.size());
        lbTotal.setText(noOfBins);

        int totalCapacity = 0;
        for (Bin bin : listOfFoundBins){
            totalCapacity += bin.getMaxCapacity();
        }

        lbTotalCap.setText(String.valueOf(totalCapacity));

        lbLifeTimeWaste.setText(String.valueOf(BinStatusUtil.getLifeTimeWaste(listOfFoundBins)));

    }

    @FXML
    private void populatePieChart(){

        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();

        chartData.add(new PieChart.Data( "Operational",7));
        chartData.add(new PieChart.Data("Unavailable", 5));
        chartData.add(new PieChart.Data("Full", 3));

        OperationsPieChart.setData(chartData);

    }
}


