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

    @FXML
    private BorderPane borderPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        borderPane.setCenter(loadView(ViewList.HOME));
    }

    @FXML
    protected void onBtnHome() {
        borderPane.setCenter(loadView(ViewList.HOME));
    }

    @FXML
    protected void onBtnSettings( ) {
        borderPane.setCenter(loadView(ViewList.SETTINGS));
    }

    @FXML
    protected void onBtnGame( ) {
        borderPane.setCenter(loadView(ViewList.RESTURENT));
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