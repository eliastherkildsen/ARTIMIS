module org.apollo.template {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens org.apollo.template to javafx.fxml;
    exports org.apollo.template;
    exports org.apollo.template.Controller;
    opens org.apollo.template.Controller to javafx.fxml;
    exports org.apollo.template.Controller.Resturent;
    opens org.apollo.template.Controller.Resturent to javafx.fxml;
}