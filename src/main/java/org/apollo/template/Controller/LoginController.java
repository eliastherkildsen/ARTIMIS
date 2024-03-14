package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apollo.template.Service.CSVParser.CSVParserDAO;
import org.apollo.template.Service.CSVParser.CSVParserDAODB;
import org.apollo.template.Service.Debugger.DebugMessage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginController  {

    private static LoginController INSTANCE = new LoginController();

    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    AnchorPane anchorPane;


    @FXML
    protected void onLoginBTN(){
        String locationUsername = "Loc";
        String concernUsername = "Ceo";
        String passwordCheck = "test123";
        String enteredPassword = passwordField.getText();
        if (Objects.equals(usernameField.getText(), locationUsername) && enteredPassword.equals(passwordCheck)) {
             MainController.getInstance().borderPane.getLeft().setVisible(true);
             anchorPane.setVisible(false);
        }
        else if (Objects.equals(usernameField.getText(), concernUsername) && enteredPassword.equals(passwordCheck)) {
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

