package org.apollo.template.View;

import org.apollo.template.Controller.*;
import org.apollo.template.Controller.Resturent.ResturentController;
import org.apollo.template.Controller.Resturent.ResturentCreateController;
import org.apollo.template.Controller.Resturent.ResturentEditController;

public enum ViewList {

    MAIN("MainView.fxml", MainController.getInstance()),
    RESTURENT("ResturentView.fxml", ResturentController.getInstance()),
    RESTURENT_EDIT("ResturentEditView.fxml", ResturentEditController.getInstance()),
    RESTURENT_CREATE("ResturentCreateView.fxml", ResturentCreateController.getInstance()),
    SETTINGS("SettingsView.fxml", SettingsController.getInstance()),
    HOME("HomeView.fxml", HomeController.getInstance()),
    OPERATION("OperationsTab.fxml", OperationsTabController.getInstance()),
    LOGIN("LoginView.fxml", LoginController.getInstance()),
    CREATE_BIN("CreateBinView.fxml", BinCreateController.getInstance()),
    STATISTIC("StatisticView.fxml", StatisticController.getInstance());

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
