package org.apollo.template.Controller.Resturent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.apollo.template.Controller.MainController;
import org.apollo.template.Domain.Bin;
import org.apollo.template.Domain.City;
import org.apollo.template.Domain.Resturent;
import org.apollo.template.Service.Bin.BinDAO;
import org.apollo.template.Service.Bin.BinDAODB;
import org.apollo.template.Service.City.CityDAO;
import org.apollo.template.Service.City.CityDAODB;
import org.apollo.template.Service.Debugger.DebugMessage;
import org.apollo.template.Service.Resturent.ResturenDAODB;
import org.apollo.template.Service.Resturent.ResturentDAO;
import org.apollo.template.View.ViewList;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ResturentEditController implements Initializable {
    private static ResturentEditController INSTANCE = new ResturentEditController();
    private ResturentDAO resturentDAO;
    private CityDAO cityDAO;
    private BinDAO binDAO;
    private Resturent selectedResturent = null;
    @FXML
    private TextField tfAdress, tfName;
    @FXML
    private ChoiceBox<City> cbCity;
    @FXML
    private ListView<Bin> lwAllBins, lwAssignedBins;
    private List<Bin> selectedBins = new ArrayList<>();
    private List<Bin> resturentAssignedBinsInDB;




    private ResturentEditController() {
        if (INSTANCE == null) {
            DebugMessage.info(this, "Creating an instance of " + this);
        }
    }

    public static ResturentEditController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ResturentEditController();
        }
        return INSTANCE;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCitys();
        loadSelectedResturent();
        loadAllBins();
        loadAssignedBins();
    }

    public void onBtnCancel(){
        MainController.getInstance().changeView(ViewList.RESTURENT);
    }

    // region Buttons region
    public void onBtnSave(){

        binDAO = new BinDAODB();

        // creating a new resturent object.
        if (tfAdress.getLength() > 0 && tfName.getLength() > 0 && cbCity.getSelectionModel().getSelectedItem() != null) {

            Resturent newResturent = new Resturent(selectedResturent.getResturentID(), tfName.getText(),
                    tfAdress.getText(), cbCity.getSelectionModel().getSelectedItem());

            resturentDAO = new ResturenDAODB();

            //clearResturentBinRefferences();

            resturentDAO.update(selectedResturent.getResturentID(), newResturent);

            updateAssigned();

        }

        else {
            DebugMessage.error(this, "Not all fields was populated! ");
        }


        // adds the selected bins to the restaurant.

        for (Bin bin : lwAssignedBins.getItems()){
            bin.setResturentID(selectedResturent.getResturentID());
            binDAO.update(bin.getBinID(), bin);
        }





    }
    public void onBtnDelete(){

        if (selectedResturent != null) {

            resturentDAO = new ResturenDAODB();
            resturentDAO.delete(selectedResturent.getResturentID());

            // returns the user to Resturent tab
            onBtnCancel();
        }
    }
    public void onBtnAdd(){

        // check if an item has been selected form LW
        if (lwAllBins.getSelectionModel().getSelectedIndex() == -1) return;

        Bin selectedBin = lwAllBins.getSelectionModel().getSelectedItem();


        // check if selected item exist in LW
        boolean inLWSelected = false;
        for (Bin bin: lwAssignedBins.getItems()) {
            if (bin.getBinID() == selectedBin.getBinID()){
                inLWSelected = true;
                break;
            }
        }

        if (inLWSelected) return;

        lwAssignedBins.getItems().add(selectedBin);
        DebugMessage.info(this, "in OnBtnAdd; New bin has been added to selected bins LW : " + selectedBin.toString());
    }
    public void btnRemove(){
        // checks if an item has been selected.
        if (lwAssignedBins.getSelectionModel().getSelectedItem() == null) return;

        lwAssignedBins.getItems().remove(lwAssignedBins.getSelectionModel().getSelectedItem());


    }

    // endregion

    public Resturent getSelectedResturent() {
        return selectedResturent;
    }

    public void setSelectedResturent(Resturent selectedResturent) {
        this.selectedResturent = selectedResturent;
    }
    private void loadSelectedResturent(){

        cityDAO = new CityDAODB();

        if (selectedResturent != null){

            // setting text fields.
            tfAdress.setText(selectedResturent.getResturentAdress());
            tfName.setText((selectedResturent.getResturentName()));

        }

    }

    private void loadCitys(){

        cityDAO = new CityDAODB();
        cbCity.getItems().addAll(cityDAO.readAll());

        for (City city : cbCity.getItems()){
            if (city.getZipCode() == selectedResturent.getResturentZip()){
                cbCity.getSelectionModel().select(city);
            }
        }

    }

    private void loadAllBins(){
        binDAO = new BinDAODB();
        lwAllBins.getItems().addAll(binDAO.readAll());
    }

    private void loadAssignedBins(){

        if (selectedResturent == null) return;

        binDAO = new BinDAODB();
        resturentAssignedBinsInDB = binDAO.readAllFromResturentID(selectedResturent.getResturentID());
        lwAssignedBins.getItems().addAll(resturentAssignedBinsInDB);

    }


    /**
     * Method that comapirs the bins in the database, with the
     * selected bins in the list view. removes all resturent refferences to bins
     * witch is not in the listView of selected bins.
     *
     */
    private void updateAssigned() {
        BinDAODB binDAODB = new BinDAODB();

        // Fetch all selected bins.
        List<Bin> selectedBins = lwAssignedBins.getItems();

        // Remove unassigned bins from the resturentAssignedBinsInDB list.
        resturentAssignedBinsInDB.removeIf(bin -> selectedBins.contains(bin));

        // Remove restaurant references from bins that are not assigned in the list view.
        for (Bin bin : resturentAssignedBinsInDB) {

            binDAODB.setResturentNull(bin.getBinID());

        }
    }

}
