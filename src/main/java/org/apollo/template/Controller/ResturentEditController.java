package org.apollo.template.Controller;

import org.apollo.template.Service.Debugger.DebugMessage;
import org.apollo.template.View.ViewList;

public class ResturentEditController {
    private static ResturentEditController INSTANCE = new ResturentEditController();



    private ResturentEditController() {
        if (INSTANCE == null) {
            DebugMessage.info(this, "Creating an instance of " + this);
        }
    }

    public static ResturentEditController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ResturentEditController();
        }
        return INSTANCE;
    }

    public void onBtnCancel(){
        MainController.getInstance().changeView(ViewList.RESTURENT);
    }

}
