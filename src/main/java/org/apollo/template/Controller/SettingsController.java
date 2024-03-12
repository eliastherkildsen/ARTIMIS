package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.apollo.template.Service.Debugger.DebugMessage;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController {
    private static SettingsController INSTANCE = new SettingsController();




    private SettingsController() {
        if (INSTANCE == null) {
            DebugMessage.info(this, "Creating an instance of " + this);
        }
    }

    public static SettingsController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SettingsController();
        }
        return INSTANCE;
    }

}
