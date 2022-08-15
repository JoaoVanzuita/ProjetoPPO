module ProjetoPPO {
    requires javafx.fxml;
    requires javafx.controls;
    requires jasperreports;
    requires java.sql;

    opens util to javafx.fxml;
    exports util;


    exports controller.funcionario;
    opens controller.funcionario to javafx.fxml;
    exports controller.global;
    opens controller.global to javafx.fxml;
    exports controller.gerente;
    opens controller.gerente to javafx.fxml;
}