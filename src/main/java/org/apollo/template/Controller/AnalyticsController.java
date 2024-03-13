package org.apollo.template.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.apollo.template.Domain.Bin;
import org.apollo.template.Domain.BinStatus;
import org.apollo.template.Domain.Resturent;
import org.apollo.template.Service.Bin.BinDAO;
import org.apollo.template.Service.Bin.BinDAODB;
import org.apollo.template.Service.BinStatus.BinStatusDBSearch;
import org.apollo.template.Service.Debugger.DebugMessage;
import org.apollo.template.Service.Resturent.ResturenDAODB;
import org.apollo.template.Service.Resturent.ResturentDAO;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AnalyticsController implements Initializable {

    private static AnalyticsController INSTANCE = new AnalyticsController();
    @FXML
    private AnchorPane root;
    private ResturentDAO resturentDAO;
    private BinDAO binDAO;
    @FXML
    private DatePicker dpStartDate, dpEndDate;
    private ZoneId defaultZoneId = ZoneId.systemDefault();
    @FXML
    private ChoiceBox<Resturent> cbResturent;
    @FXML
    private ListView<Resturent> lwSelectedData;
    @FXML
    private TableView<BinStatus> tblData;
    private List<BinStatus> binStatusList;


    private AnalyticsController() {
        if (INSTANCE == null) {
            DebugMessage.info(this, "Creating an instance of " + this);
        }
    }

    public static AnalyticsController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AnalyticsController();
        }
        return INSTANCE;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadResturentIntoCb();
        createTableColumns();
    }

    // region buttons

    public void onBtnAdd() {

        // checks if an item has been selected from cbResturent.
        if (cbResturent.getItems() == null) {
            DebugMessage.info(this, "No resturent has been selected!");
            return;
        }

        // check if the resturent is already selected.
        if (lwSelectedData.getItems().contains(cbResturent.getSelectionModel().getSelectedItem())) {
            DebugMessage.info(this, "The restaurant is already selected.");
            return;
        }

        lwSelectedData.getItems().add(cbResturent.getSelectionModel().getSelectedItem());



    }
    public void onBtnRemove() {

        if (cbResturent.getSelectionModel().getSelectedItem() == null){
            DebugMessage.info(this, "No restaurant has been selected!");
            return;
        }

        Resturent selectedResturent = cbResturent.getSelectionModel().getSelectedItem();

        if (lwSelectedData.getItems().contains(selectedResturent)){
            lwSelectedData.getItems().remove(selectedResturent);
        }
    }

    public void onBtnUpdate(){



        if (dpStartDate.getValue() == null) {
            DebugMessage.info(this, "No start date has been selected!");
            return;
        }
        if (dpEndDate.getValue() == null) {
            DebugMessage.info(this, "No end date has been selected!");
            return;
        }

        if (lwSelectedData.getItems() == null) {
            DebugMessage.info(this, "No restaurants has been selected!");
            return;
        }


        Date startDate = convertFromLocalDateToDate(dpStartDate.getValue());
        Date endDate = convertFromLocalDateToDate(dpEndDate.getValue());

        loadResturentData(startDate, endDate, lwSelectedData.getItems());
        List<BinStatus> binStatusList = loadResturentData(startDate, endDate, lwSelectedData.getItems());


        if (binStatusList == null){
            DebugMessage.info(this, "The selected restaurant has not data!");
            return;
        }
        tblData.getItems().addAll(binStatusList);

    }



    // endregion

    // region loader

    private List<Resturent> loadAllResturents(){
        resturentDAO = new ResturenDAODB();

        return resturentDAO.readall();

    }
    private Date loadStartDate(){

        LocalDate localDate = dpStartDate.getValue();
        return Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

    }
    private Date loadEndDate(){

        LocalDate localDate = dpEndDate.getValue();
        return Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

    }
    private void loadResturentIntoCb(){

        cbResturent.getItems().addAll(loadAllResturents());

    }
    private List<BinStatus> loadResturentData(Date startDate, Date endDate, List<Resturent> resturents){

        ArrayList<Bin> binIDList = new ArrayList<>();
        binDAO = new BinDAODB();

        for (Resturent resturent : resturents){

            binIDList.addAll(binDAO.readAllFromResturentID(resturent.getResturentID()));

        }

        return binStatusList = new BinStatusDBSearch().fetchStatus(startDate, endDate, binIDList);

    }

    // endregion

    private Date convertFromLocalDateToDate(LocalDate localDate){

        return Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

    }

    private void createTableColumns() {
        TableColumn<BinStatus, Integer> binIDColumn = new TableColumn<>("Bin ID");
        binIDColumn.setCellValueFactory(new PropertyValueFactory<>("binID"));

        TableColumn<BinStatus, Date> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        TableColumn<BinStatus, Integer> weightColumn = new TableColumn<>("Weight");
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));

        TableColumn<BinStatus, Integer> RestaurantColumn = new TableColumn<>("Restaurant");
        RestaurantColumn.setCellValueFactory(new PropertyValueFactory<>("assignedResturent"));
;



        tblData.getColumns().clear();
        tblData.getColumns().addAll(binIDColumn, dateColumn, weightColumn, RestaurantColumn);
    }



}
