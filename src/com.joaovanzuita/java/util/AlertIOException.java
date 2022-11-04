package util;

import javafx.scene.control.Alert;
import java.io.IOException;

public class AlertIOException extends Alert {

    public AlertIOException(AlertType alertType, IOException e) {
        super(alertType);

        this.setContentText("Ocorreu um erro ao carregar o arquivo" + "\n" +
                "Mensagem: " + e.getMessage());

        this.getDialogPane().getStylesheets().add(getClass().getResource("/css/Application.css").toString());
        this.getDialogPane().getStyleClass().add("dialog");
    }
}
