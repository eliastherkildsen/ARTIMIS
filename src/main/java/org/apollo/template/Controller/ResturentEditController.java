package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.apollo.template.Domain.City;
import org.apollo.template.Domain.Resturent;
import org.apollo.template.Service.City.CityDAO;
import org.apollo.template.Service.City.CityDAODB;
import org.apollo.template.Service.Debugger.DebugMessage;
import org.apollo.template.Service.Resturent.ResturenDAODB;
import org.apollo.template.Service.Resturent.ResturentDAO;
import org.apollo.template.View.ViewList;

import java.net.URL;
import java.util.ResourceBundle;


public class ResturentEditController implements Initializable {
    private static ResturentEditController INSTANCE = new ResturentEditController();
    private ResturentDAO resturentDAO;
    private CityDAO cityDAO;
    private Resturent selectedResturent = null;
    @FXML
    private TextField tfAdress, tfName;
    @FXML
    private ChoiceBox<City> cbCity;



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
    }

    public void onBtnCancel(){
        MainController.getInstance().changeView(ViewList.RESTURENT);
    }

    public void onBtnSave(){



        // creating a new resturent object.
        if (tfAdress.getLength() > 0 && tfName.getLength() > 0 && cbCity.getSelectionModel().getSelectedItem() != null) {

            Resturent newResturent = new Resturent(selectedResturent.getResturentID(), tfName.getText(),
                    tfAdress.getText(), cbCity.getSelectionModel().getSelectedItem());

            resturentDAO = new ResturenDAODB();
            resturentDAO.update(selectedResturent.getResturentID(), newResturent);

        }

        else {
            DebugMessage.error(this, "Not all fields was populated! ");
        }



    }

    public Resturent getSelectedResturent() {
        return selectedResturent;
    }

    public void setSelectedResturent(Resturent selectedResturent) {
        this.selectedResturent = selectedResturent;
    }

    public void loadSelectedResturent(){

        cityDAO = new CityDAODB();

        if (selectedResturent != null){

            // setting text fields.
            tfAdress.setText(selectedResturent.getResturentAdress());
            tfName.setText((selectedResturent.getResturentName()));

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

    public void loadCitys(){

        cityDAO = new CityDAODB();
        cbCity.getItems().addAll(cityDAO.readAll());

        for (City city : cbCity.getItems()){
            if (city.getZipCode() == selectedResturent.getResturentZip()){
                cbCity.getSelectionModel().select(city);
            }
        }



    }

}
