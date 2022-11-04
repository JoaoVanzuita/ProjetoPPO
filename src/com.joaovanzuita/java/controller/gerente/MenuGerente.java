package controller.gerente;

import controller.global.GerenciarCategorias;
import controller.global.GerenciarConta;
import controller.global.GerenciarEstoque;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import util.Alert;
import javafx.stage.Stage;
import model.bean.Usuario;
import util.AlertIOException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuGerente implements Initializable {

    private Usuario usuario;

    public MenuGerente(Usuario usuario) {

        this.usuario = usuario;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void gerenciarConta(ActionEvent actionEvent){

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root;

        try {

            URL urlGerenciarConta = getClass().getResource("/view/telasGlobais/GerenciarConta.fxml");

            FXMLLoader loader = new FXMLLoader(urlGerenciarConta);

            loader.setController(new GerenciarConta(this));

            root = loader.load();

            Scene gerenciarConta = new Scene(root);
            stage.setScene(gerenciarConta);

            stage.show();

        } catch (IOException e) {

            e.printStackTrace();
            Logger.getLogger(MenuGerente.class.getName()).log(Level.WARNING, e.getMessage(), e);

            AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

    }

    public void gerenciarEstoque(ActionEvent actionEvent){

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root;

        try {

            URL urlGerenciarEstoque = getClass().getResource("/view/telasGlobais/GerenciarEstoque.fxml");

            FXMLLoader loader = new FXMLLoader(urlGerenciarEstoque);
            loader.setController(new GerenciarEstoque(this.usuario));

            root = loader.load();

            Scene gerenciarCategorias = new Scene(root);
            stage.setScene(gerenciarCategorias);

            stage.show();

        } catch (IOException e) {

            e.printStackTrace();
            Logger.getLogger(MenuGerente.class.getName()).log(Level.WARNING, e.getMessage(), e);

            AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

    }

    public void gerenciarCategorias(ActionEvent actionEvent) {

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root;

        try {

            URL urlGerenciarCategorias = getClass().getResource("/view/telasGlobais/GerenciarCategorias.fxml");

            FXMLLoader loader = new FXMLLoader(urlGerenciarCategorias);
            loader.setController(new GerenciarCategorias(this.usuario));

            root = loader.load();

            Scene gerenciarCategorias = new Scene(root);
            stage.setScene(gerenciarCategorias);

            stage.show();

        } catch (IOException e) {

            e.printStackTrace();
            Logger.getLogger(MenuGerente.class.getName()).log(Level.WARNING, e.getMessage(), e);

            AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

    }

    public void emitirRelatorios(ActionEvent actionEvent){

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root;

        try {

            URL urlEmitirRelatorios = getClass().getResource("/view/telasGerente/EmitirRelatorios.fxml");

            FXMLLoader loader = new FXMLLoader(urlEmitirRelatorios);
            loader.setController(new EmitirRelatorios(this.usuario));

            root = loader.load();

            Scene emitirRelatorios = new Scene(root);
            stage.setScene(emitirRelatorios);

            stage.show();

        } catch (IOException e) {

            e.printStackTrace();
            Logger.getLogger(MenuGerente.class.getName()).log(Level.WARNING, e.getMessage(), e);

            AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

    }

    public void gerenciarFornecedores(ActionEvent actionEvent) {

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root;

        try {

            URL urlGerenciarFornecedores = getClass().getResource("/view/telasGerente/GerenciarFornecedores.fxml");

            FXMLLoader loader = new FXMLLoader(urlGerenciarFornecedores);
            loader.setController(new GerenciarFornecedores(this.usuario));

            root = loader.load();

            Scene gerenciarFornecedores = new Scene(root);
            stage.setScene(gerenciarFornecedores);

            stage.show();

        } catch (IOException e) {

            e.printStackTrace();
            Logger.getLogger(MenuGerente.class.getName()).log(Level.WARNING, e.getMessage(), e);

            AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

    }

    public void gerenciarFuncionarios(ActionEvent actionEvent) {

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root;

        try {

            URL urlGerenciarFuncionarios = getClass().getResource("/view/telasGerente/GerenciarFuncionarios.fxml");

            FXMLLoader loader = new FXMLLoader(urlGerenciarFuncionarios);
            loader.setController(new GerenciarFuncionarios(this.usuario));

            root = loader.load();

            Scene gerenciarFuncionarios = new Scene(root);
            stage.setScene(gerenciarFuncionarios);

            stage.show();

        } catch (IOException e) {

            e.printStackTrace();
            Logger.getLogger(MenuGerente.class.getName()).log(Level.WARNING, e.getMessage(), e);

            AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

    }

    public void efetuarCompra(ActionEvent actionEvent){

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root;

        try {

            URL urlEfetuarCompra = getClass().getResource("/view/telasGerente/EfetuarCompra.fxml");

            FXMLLoader loader = new FXMLLoader(urlEfetuarCompra);
            loader.setController(new EfetuarCompra(this.usuario));

            root = loader.load();

            Scene efetuarCompra = new Scene(root);
            stage.setScene(efetuarCompra);

            stage.show();

        } catch (IOException e) {

            e.printStackTrace();
            Logger.getLogger(MenuGerente.class.getName()).log(Level.WARNING, e.getMessage(), e);

            AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

    }

    public void sair(ActionEvent actionEvent) {

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root;

        try {

            URL urlLogin = getClass().getResource("/view/telasGlobais/Login.fxml");

            FXMLLoader loader = new FXMLLoader(urlLogin);

            root = loader.load();

            Scene gerenciarCategorias = new Scene(root);
            stage.setScene(gerenciarCategorias);

            stage.show();

        } catch (IOException e) {

            e.printStackTrace();
            Logger.getLogger(MenuGerente.class.getName()).log(Level.WARNING, e.getMessage(), e);

            AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

    }

    public Usuario getUsuario() {
        return usuario;
    }
}
