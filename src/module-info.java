module ProjetoPPO {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;

    opens util to javafx.fxml;
    exports util;

    opens controller to javafx.fxml;
    exports controller;
}