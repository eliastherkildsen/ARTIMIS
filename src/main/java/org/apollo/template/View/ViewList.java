package org.apollo.template.View;

import org.apollo.template.Controller.GameController;
import org.apollo.template.Controller.HomeController;
import org.apollo.template.Controller.MainController;
import org.apollo.template.Controller.SettingsController;

public enum ViewList {

    MAIN("MainView.fxml", MainController.getInstance()),
    GAME("GameView.fxml", GameController.getInstance()),
    SETTINGS("SettingsView.fxml", SettingsController.getInstance()),
    HOME("HomeView.fxml", HomeController.getInstance());

    private final String FXML_FILE_NAME;
    private final Object CONTROLLER;

    ViewList(String fxmlFileName, Object controller) {
        this.FXML_FILE_NAME = fxmlFileName;
        this.CONTROLLER = controller;
    }

    public String getFxmlFileName() {
        return this.FXML_FILE_NAME;
    }

    public Object getController() {
        return this.CONTROLLER;
    }

    public static final String LOADER_LOCATION = "/org/apollo/template/";

}
