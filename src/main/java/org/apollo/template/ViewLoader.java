package org.apollo.template;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.apollo.template.Service.Debugger.DebugMessage;
import org.apollo.template.View.ViewList;

import java.io.IOException;

public class ViewLoader {

    /**
     * Java method for loading an anchor pane from a FXML file, and forwarding it as an anchor pane obj,
     * with the corresponding controller. Returns NULL if an IOException is thrown.
     *
     * @param fxmlFileName ViewList
     * @return AnchorPane
     */
    public static AnchorPane loadView(ViewList fxmlFileName) {

        // Fetching anchor pane from a FXML file.
        FXMLLoader loader = new FXMLLoader();
        loader.setController(fxmlFileName.getController());
        loader.setLocation(ViewLoader.class.getResource(fxmlFileName.getFxmlFileName()));

        DebugMessage.info("ViewLoader", "Loading view: " + fxmlFileName.getFxmlFileName()
                + " With Controller: " + fxmlFileName.getController());

        try {
            return loader.load();

        } catch (IOException e) {
            DebugMessage.error("ViewLoader","Error loading FXML view: " + fxmlFileName + e.getMessage());
            return null;
        }
    }

}
