package org.apollo.template.Controller;

import javafx.fxml.Initializable;
import org.apollo.template.Domain.Resturent;
import org.apollo.template.Service.Debugger.DebugMessage;
import org.apollo.template.Service.Resturent.ResturentDAO;
import org.apollo.template.View.ViewList;

import java.net.URL;
import java.util.ResourceBundle;


public class ResturentEditController implements Initializable {
    private static ResturentEditController INSTANCE = new ResturentEditController();
    private ResturentDAO DAO;
    private Resturent selectedResturent = null;



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
        System.out.println("INITININ");
    }

    public void onBtnCancel(){
        MainController.getInstance().changeView(ViewList.RESTURENT);
    }

    public Resturent getSelectedResturent() {
        return selectedResturent;
    }

    public void setSelectedResturent(Resturent selectedResturent) {
        this.selectedResturent = selectedResturent;
    }
}
