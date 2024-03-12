package org.apollo.template.Controller;

import javafx.scene.layout.BorderPane;
import org.apollo.template.Service.Debugger.DebugMessage;

public class OperationsTabController {

    private static OperationsTabController INSTANCE = new OperationsTabController();

    private BorderPane borderPane;


    private OperationsTabController(){
        if (INSTANCE == null){
            DebugMessage.info(this, "Creating an instance of " + this);
        }
    }

    public static OperationsTabController getInstance(){

        if (INSTANCE == null){
            INSTANCE = new OperationsTabController();
        }

        return INSTANCE;
    }


    //Choice box that displays all restaurants and their address
    // Need to get restaurant name and address from database

    private void populateChoiceBox(){

    }


}


