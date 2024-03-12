package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.apollo.template.Service.Debugger.DebugMessage;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController {
    private static HomeController INSTANCE = new HomeController();
    @FXML
    private AnchorPane root;

    private HomeController() {
        if (INSTANCE == null) {
            DebugMessage.info(this, "Creating an instance of " + this);
        }
    }

    public static HomeController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HomeController();
        }
        return INSTANCE;
    }
}
