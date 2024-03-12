package org.apollo.template.Controller;

import org.apollo.template.Service.Debugger.DebugMessage;
import org.apollo.template.View.ViewList;

public class ResturentController {
    private static ResturentController INSTANCE = new ResturentController();



    private ResturentController() {
        if (INSTANCE == null) {
            DebugMessage.info(this, "Creating an instance of " + this);
        }
    }

    public static ResturentController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ResturentController();
        }
        return INSTANCE;
    }

    public void onBtnCreateNewClick(){
        MainController.getInstance().changeView(ViewList.RESTURENT_EDIT);
    }

}
