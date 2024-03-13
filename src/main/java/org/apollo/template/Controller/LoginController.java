package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.apollo.template.Service.Debugger.DebugMessage;
import org.apollo.template.View.ViewList;
import org.apollo.template.ViewLoader;

import java.util.Objects;

import static org.apollo.template.ViewLoader.loadView;

public class LoginController  {

    private static LoginController INSTANCE = new LoginController();

    @FXML
    public TextField usernameField, passwordField;
    @FXML
    AnchorPane anchorPane;

    @FXML
    protected void onLoginBTN(){
        String locationUsername = "Loc";
        String concernUsername = "Ceo";
        String passwordCheck = "test123";
        if (Objects.equals(usernameField.getText(), locationUsername) && Objects.equals(passwordField.getText(), passwordCheck)) {
             MainController.getInstance().borderPane.getLeft().setVisible(true);
             anchorPane.setVisible(false);
        }
        else if (Objects.equals(usernameField.getText(), concernUsername) && Objects.equals(passwordField.getText(), passwordCheck)) {
             MainController.getInstance().borderPane.getLeft().setVisible(true);
             anchorPane.setVisible(false);
        }
        else {
             System.out.println("Failed Login");
        }
    }

    private LoginController() {
        if (INSTANCE == null) {
            DebugMessage.info(this, "Creating an instance of " + this);
        }
    }

    public static LoginController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LoginController();
        }
        return INSTANCE;
    }
}

