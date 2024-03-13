package org.apollo.template.Controller.Resturent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.apollo.template.Controller.MainController;
import org.apollo.template.Domain.Resturent;
import org.apollo.template.Service.Debugger.DebugMessage;
import org.apollo.template.Service.Resturent.ResturenDAODB;
import org.apollo.template.Service.Resturent.ResturentDAO;
import org.apollo.template.View.ViewList;

import java.net.URL;
import java.util.ResourceBundle;

public class ResturentController implements Initializable {
    private static ResturentController INSTANCE = new ResturentController();
    @FXML
    private ListView lwResturents;
    private ResturentDAO DAO;



    private ResturentController() {
        if (INSTANCE == null) {
            DebugMessage.info(this, "Creating an instance of " + this);
        }
    }

    public static ResturentController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ResturentController();
        }
        return INSTANCE;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadResturents();
    }

    public void onBtnEditClick(){

        if (lwResturents.getSelectionModel().getSelectedItems() != null) {
            Resturent selectedResturent = (Resturent) lwResturents.getSelectionModel().getSelectedItem();
            ResturentEditController.getInstance().setSelectedResturent(selectedResturent);
        }

        MainController.getInstance().changeView(ViewList.RESTURENT_EDIT);

    }

    private void loadResturents(){

        // setting up DAO
        DAO = new ResturenDAODB();
        for (Resturent resturent : DAO.readall()){
            lwResturents.getItems().add(resturent);
        }

    }

    public void onBtnCreate(){
        MainController.getInstance().changeView(ViewList.RESTURENT_CREATE);
    }

}
