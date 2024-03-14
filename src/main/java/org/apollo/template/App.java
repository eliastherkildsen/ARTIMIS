package org.apollo.template;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apollo.template.Service.ConfigLoader;
import org.apollo.template.Service.Database.JDBC;
import org.apollo.template.View.ViewList;

import java.io.IOException;


public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        ConfigLoader.get();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(ViewList.MAIN.getFxmlFileName()));
        fxmlLoader.setController(ViewList.MAIN.getController());
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);

        JDBC.get();

        stage.setTitle("Artemis");
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:src/main/resources/org/apollo/template/images/TempLogoBG_shadow.png"));
        stage.setMinHeight(600);
        stage.setMinWidth(600);

        stage.show();

    }

    public static void main(String[] args) { launch(); }
}