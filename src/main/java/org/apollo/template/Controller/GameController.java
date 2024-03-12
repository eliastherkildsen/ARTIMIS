package org.apollo.template.Controller;

import org.apollo.template.Service.Debugger.DebugMessage;

public class GameController {
    private static GameController INSTANCE = new GameController();



    private GameController() {
        if (INSTANCE == null) {
            DebugMessage.info(this, "Creating an instance of " + this);
        }
    }

    public static GameController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameController();
        }
        return INSTANCE;
    }

}
