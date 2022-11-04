package util;

import javafx.scene.control.Alert;

import java.sql.SQLException;

public class AlertSQLException extends Alert {

    public AlertSQLException(AlertType alertType, SQLException e) {
        super(alertType);

        this.setContentText("Ocorreu uma exceção durante a operação. Tente novamente. Caso o erro persista, " +
                "reinicie o sistema. Se isso não resolver o problema, entre em contato com o administrador do sistema. \n" +
                "\nCódigo do erro: " + e.getSQLState() + "\n"+
                "\nMensagem: " + e.getMessage());

        if(e.getSQLState().equals("42601")){

            this.setHeaderText("Erro de sintaxe na operação SQL.");

        }else if(e.getSQLState().equals("08001")){

            this.setHeaderText("Não foi possível conectar ao banco de dados.");

        }else if(e.getSQLState().equals("23503")){

            this.setHeaderText("Não é possível excluir esse item.");

        }else if(e.getSQLState().equals("08003")){

            this.setHeaderText("Coonexão com o banco de dados inexistente.");

        }

        this.getDialogPane().getStylesheets().add(getClass().getResource("/css/Application.css").toString());
        this.getDialogPane().getStyleClass().add("dialog");

    }
}
