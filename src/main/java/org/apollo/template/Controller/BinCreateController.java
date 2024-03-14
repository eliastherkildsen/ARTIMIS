package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.apollo.template.Domain.Bin;
import org.apollo.template.Domain.Resturent;
import org.apollo.template.Service.Bin.BinDAO;
import org.apollo.template.Service.Bin.BinDAODB;
import org.apollo.template.Service.Debugger.DebugMessage;
import org.apollo.template.Service.Resturent.ResturenDAODB;
import org.apollo.template.Service.Resturent.ResturentDAO;
import org.apollo.template.View.ViewList;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.PriorityBlockingQueue;

public class BinCreateController implements Initializable {
    private static BinCreateController INSTANCE = new BinCreateController();
    private ResturentDAO resturentDAO;
    private BinDAO binDAO;
    @FXML
    private ChoiceBox<Resturent> cbResturent;
    @FXML
    private ChoiceBox<String> cbRaspID;
    @FXML
    private TextField tfMaxCapacity;
    @FXML
    private DatePicker dpInstalationDate;



    private BinCreateController() {
        if (INSTANCE == null) {
            DebugMessage.info(this, "Creating an instance of " + this);
        }
    }

    public static BinCreateController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BinCreateController();
        }
        return INSTANCE;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadResturentCB();
        loadRaspID();
    }

    // region buttons

    public void onBtnCreate(){

        binDAO = new BinDAODB();

        // check if a name has been given
        String maxCapacity = tfMaxCapacity.getText();
        if (maxCapacity.length() <= 0) {
            DebugMessage.info(this, "No maxCapacity has been given!");
            return;
        }
        Resturent resturent = cbResturent.getSelectionModel().getSelectedItem();
        if (resturent == null) {
            DebugMessage.info(this, "No restaurant has been selected!");
            return;
        }

        LocalDate instalationDate = dpInstalationDate.getValue();
        if (instalationDate == null) {
            DebugMessage.info(this, "No date has been selected!");
            return;
        }

        // converting localDate to Date
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date date = Date.from(instalationDate.atStartOfDay(defaultZoneId).toInstant());

        // converting maxCapacity from string to numerical value.
        int MaxCap = Integer.parseInt(maxCapacity);

        binDAO.add(new Bin(resturent.getResturentID(), MaxCap, date));




    }
    public void onBtnCancel(){

        MainController.getInstance().changeView(ViewList.HOME);

    }
    // endregion

    //region load

    public List<Resturent> loadAllResturents(){

        resturentDAO = new ResturenDAODB();
        return resturentDAO.readall();
    }

    public void loadResturentCB(){
        cbResturent.getItems().addAll(loadAllResturents());
    }

    public void loadRaspID(){
        cbRaspID.getSelectionModel().select("TBD");
    }

    // endregion
}
