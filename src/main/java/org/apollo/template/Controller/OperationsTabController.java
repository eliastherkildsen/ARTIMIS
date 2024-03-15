package org.apollo.template.Controller;

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apollo.template.Domain.Bin;
import org.apollo.template.Domain.BinStatus;
import org.apollo.template.Domain.Resturent;
import org.apollo.template.Service.Bin.BinDAO;
import org.apollo.template.Service.Bin.BinDAODB;
import org.apollo.template.Service.BinStatus.BinStatusDAOExtend;
import org.apollo.template.Service.BinStatus.BinStatusDBSearch;
import org.apollo.template.Service.BinStatus.BinStatusUtil;
import org.apollo.template.Service.CSVParser.CSVParserDAO;
import org.apollo.template.Service.CSVParser.CSVParserDAODB;
import org.apollo.template.Service.Debugger.DebugMessage;
import org.apollo.template.Service.Resturent.ResturenDAODB;
import org.apollo.template.Service.Resturent.ResturentDAO;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OperationsTabController implements Initializable {

    private static OperationsTabController INSTANCE = new OperationsTabController();


    @FXML
    private BorderPane borderPane;
    @FXML
    private ChoiceBox<Resturent> cbLocations;
    @FXML
    private ListView<String> LVAllBins;
    @FXML
    private Button btnConfirm, btnViewAll;
    @FXML
    private Label lbTotal, lbTotalCap, lbWeeklyWeight, lbLifeTimeWaste, lbCurrentLevel;
    @FXML
    private PieChart OperationsPieChart;


    private List<Bin> listOfFoundBins;
    private String noOfBins;
    private BinDAO binDAO = new BinDAODB();
    private CSVParserDAO parserDAO = new CSVParserDAODB();
    private Stage stage;

    @FXML
        public void importCSVAction() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getInitialDirectory();
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("Microsoft Excel Comma Separated Files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(csvFilter);
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                System.out.println("Selected File: " + selectedFile.getAbsolutePath());
                List<String> contentLines = Files.readAllLines(selectedFile.toPath());

                if (!contentLines.isEmpty()) {
                    contentLines.remove(0);
                }

                List<String[]> data = new ArrayList<>();
                for (String line : contentLines) {
                    String[] fields = line.split(",");

                    String date = convertToSQLDateTime(fields[1]);
                    String weight = fields[2];
                    String raspberryID = fields[3];
                    data.add(new String[]{date, weight, raspberryID});
                }

                parserDAO.insert(data);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private String convertToSQLDateTime(String dateTimeString) {
        return dateTimeString.replace('/', '-');
    }

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

    /***
     * Method that confirms the selected location
     */
    @FXML
    private void onActionBtnConfirm(){

        if(cbLocations.getSelectionModel().getSelectedItem() != null){
            LVAllBins.getItems().clear();
            populateOverviewStatus();
            populatePieChart();
        }

    }

    /***
     * Method that populates the choice box, view list and labels
     */
    private void populateOverviewStatus(){

        int selected = cbLocations.getSelectionModel().getSelectedItem().getResturentID();

        List<Integer> binIDList = populateBinLW(selected);

        noOfBins = String.valueOf(binIDList.size());
        lbTotal.setText(noOfBins);

        double totalCap = (double) getTotalCapacity() / 1000;
        lbTotalCap.setText(String.format("%.1f g",totalCap));

        lbWeeklyWeight.setText(String.format("%.1f g",((double) getWeeklyWaste(listOfFoundBins) / 1000)));
        lbLifeTimeWaste.setText(String.format("%.1f g",((double) BinStatusUtil.getLifeTimeWaste(listOfFoundBins) / 1000)));

        // only for the view and alignment
        lbCurrentLevel.setText(String.format("%.1f g", 0.0));

    }

    private List<Integer> populateBinLW(int selected){

        List<Integer> binIDList = new ArrayList<>();

        listOfFoundBins = binDAO.readAllFromResturentID(selected);
        listOfFoundBins.forEach(b -> binIDList.add(b.getBinID()));
        for (Bin b : listOfFoundBins){
            LVAllBins.getItems().add(b.toString());
        }

        return binIDList;

    }

    /***
     * Method for populating the list view with all bins
     * @return Returns a list with all bin ID
     */
    private List<Integer> populateBinLWWithALl(){

        List<Integer> binIDList = new ArrayList<>();

        listOfFoundBins = binDAO.readAll();
        listOfFoundBins.forEach(b -> binIDList.add(b.getBinID()));
        lbWeeklyWeight.setText(String.format("%.1f g",((double) getWeeklyWaste(listOfFoundBins) / 1000)));
        for (Bin b : listOfFoundBins){
            LVAllBins.getItems().add(b.toString());
        }

        return binIDList;

    }

    /***
     *  Method that gets the total capacity of the bins in listOfFoundBins
     * @return returns the total capacity
     */
    private int getTotalCapacity(){

        int totalCapacity = 0;
        for (Bin bin : listOfFoundBins){
            totalCapacity += bin.getMaxCapacity();
        }
        return totalCapacity;
    }


    /***
     * Methods for populating the pie chart
     */
    @FXML
    private void populatePieChart(){

        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();

        chartData.add(new PieChart.Data( "Operational",7));
        chartData.add(new PieChart.Data("Unavailable", 5));
        chartData.add(new PieChart.Data("Full", 3));

        OperationsPieChart.setData(chartData);

    }

    /***
     *  Method that gives an overview over all bins in the system
     */
    @FXML
    private void btnViewAllOnAction(){

        LVAllBins.getItems().clear();
        cbLocations.getSelectionModel().clearSelection();
        populatePieChart();
        populateBinLWWithALl();

        double totalCap = (double) getTotalCapacity() / 1000;

        lbTotalCap.setText(String.format("%.1f g",totalCap));
        // only for the view and alignment
        lbCurrentLevel.setText(String.format("%.1f g", 0.0));
        lbLifeTimeWaste.setText(String.format("%.1f g",((double) BinStatusUtil.getLifeTimeWaste(listOfFoundBins) / 1000)));

    }

    /***
     * Method for getting the waste from the past week
     * @param listOfBins
     * @return Returns for int for weekly waste
     */
    private int getWeeklyWaste(List<Bin> listOfBins){

        BinStatusDAOExtend binStatusDAOExtend = new BinStatusDBSearch();
        int weeklyWeight = 0;

        LocalDate currentLocalDate = LocalDate.now();
        LocalDate pastLocalDate = currentLocalDate.minusDays(7);

        Date currentDate = Date.from(currentLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date pastDate = Date.from(pastLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        for (BinStatus bs : binStatusDAOExtend.fetchStatus(pastDate, currentDate, listOfBins)){

            weeklyWeight += bs.getWeight();

        }

        return weeklyWeight;
    }

}


