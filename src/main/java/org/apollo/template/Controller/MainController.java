package org.apollo.template.Controller;

/*

    This it the main controller of the View, all other views are precedent with in this view.

 */

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.apollo.template.Service.Debugger.DebugMessage;
import org.apollo.template.View.ViewList;
import java.net.URL;
import java.util.ResourceBundle;

import static org.apollo.template.ViewLoader.loadView;

public class MainController implements Initializable {
    private static MainController INSTANCE = new MainController();

    private boolean loginCheck = true;
    @FXML
    public BorderPane borderPane;
    @FXML
    private AnchorPane anchorPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!loginCheck) {
            borderPane.setCenter(loadView(ViewList.LOGIN));
            borderPane.getLeft().setVisible(false);
        }
    }

    @FXML
    protected void onBtnHome() {
        borderPane.setCenter(loadView(ViewList.HOME));
    }
    @FXML
    protected void onBtnCreateBin() {
        borderPane.setCenter(loadView(ViewList.CREATE_BIN));
    }

    @FXML
    protected void onBtnStatistic( ) {
        borderPane.setCenter(loadView(ViewList.STATISTIC));
    }

    @FXML
    protected void onBtnGame( ) {
        borderPane.setCenter(loadView(ViewList.RESTURENT));
    }

    @FXML
    protected void onBtnOperationStatus ( ) { borderPane.setCenter(loadView(ViewList.OPERATION)); }
    @FXML
    protected void onBtnAnalytics  ( ) {
        borderPane.setCenter(loadView(ViewList.ANALYTICS));
    }

    public void changeView(ViewList viewList){
        borderPane.setCenter(loadView(viewList));
    }


    @FXML
    private AnchorPane root;



    private MainController() {
        if (INSTANCE == null) {
            DebugMessage.info(this, "Creating an instance of " + this);
        }
    }

    public static MainController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainController();
        }
        return INSTANCE;
    }









}