package org.apollo.template.Controller.Resturent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.apollo.template.Controller.MainController;
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

public class ResturentCreateController implements Initializable {

    private static ResturentCreateController INSTANCE = new ResturentCreateController();
    @FXML
    private AnchorPane root;
    @FXML
    private TextField tfName, tfAdress;
    @FXML
    private ChoiceBox<City> cbCity;
    private ResturentDAO resturentDAO;
    private CityDAO cityDAO;

    private ResturentCreateController() {
        if (INSTANCE == null) {
            DebugMessage.info(this, "Creating an instance of " + this);
        }
    }

    public static ResturentCreateController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ResturentCreateController();
        }
        return INSTANCE;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCityCb();
    }

    // region buttons

    public void onBtnCreate(){

        // checks for an address.
        if (loadAdress() == null){
            DebugMessage.info(this, "In onBtnCreate; No address has been given! ");
            return;
        }

        // checks for a name.
        if (loadName() == null){
            DebugMessage.info(this, "In onBtnCreate; No name has been given! ");
            return;
        }

        // checks for a city.
        if (loadCity() == null){
            DebugMessage.info(this, "In onBtnCreate; No city has been selected! ");
            return;
        }

        // create restaurant
        resturentDAO = new ResturenDAODB();
        resturentDAO.add(new Resturent(loadName(), loadAdress(), loadCity()));





    }

    public void onBtnCancel(){
        MainController.getInstance().changeView(ViewList.RESTURENT);
    }

    // endregion

    // region load

    private String loadName(){

        if (tfName.getLength() > 0){
            return tfName.getText();
        }

        return null;
    }

    private String loadAdress(){

        if (tfAdress.getLength() > 0){
            return tfAdress.getText();
        }

        return null;
    }

    private City loadCity(){
        return cbCity.getSelectionModel().getSelectedItem();
    }

    private void loadCityCb(){

        cityDAO = new CityDAODB();
        cbCity.getItems().addAll(cityDAO.readAll());

    }

    // endregion


}
