package util;

public class Alert extends javafx.scene.control.Alert {

    public Alert(AlertType alertType) {
        super(alertType);

        this.getDialogPane().getStylesheets().add(getClass().getResource("/css/Application.css").toString());
        this.getDialogPane().getStyleClass().add("dialog");
    }
}
