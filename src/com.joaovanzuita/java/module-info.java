module ProjetoPPO {
    requires javafx.fxml;
    requires javafx.controls;
    requires jasperreports;
    requires java.sql;

    exports util;
    exports controller.funcionario;
    exports controller.global;
    exports controller.gerente;

    opens util to javafx.fxml;
    opens controller.funcionario to javafx.fxml;
    opens controller.global to javafx.fxml;
    opens controller.gerente to javafx.fxml;
}