package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import org.apollo.template.Domain.Bin;
import org.apollo.template.Domain.Resturent;
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
    private ListView<Bin> LVAllBins;

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

    private void populateListView(){



    }


}


