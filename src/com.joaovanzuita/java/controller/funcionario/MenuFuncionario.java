package controller.funcionario;

import controller.global.GerenciarCategorias;
import controller.global.GerenciarConta;
import controller.global.GerenciarEstoque;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.bean.Usuario;
import util.AlertIOException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuFuncionario implements Initializable {

    private Usuario usuario;

    public MenuFuncionario(Usuario usuario) {

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
            Logger.getLogger(MenuFuncionario.class.getName()).log(Level.WARNING, e.getMessage(), e);

            Alert alert = new AlertIOException(Alert.AlertType.ERROR, e);
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
            loader.setController(new GerenciarEstoque(usuario));

            root = loader.load();

            Scene gerenciarEstoque = new Scene(root);
            stage.setScene(gerenciarEstoque);

            stage.show();

        } catch (IOException e) {

            e.printStackTrace();
            Logger.getLogger(MenuFuncionario.class.getName()).log(Level.WARNING, e.getMessage(), e);

            Alert alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

    }

    public void gerenciarClientes(ActionEvent actionEvent) {

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root;

        try {

            URL urlGerenciarClientes = getClass().getResource("/view/telasFuncionario/GerenciarClientes.fxml");

            FXMLLoader loader = new FXMLLoader(urlGerenciarClientes);
            loader.setController(new GerenciarClientes(usuario));

            root = loader.load();

            Scene gerenciarClientes = new Scene(root);
            stage.setScene(gerenciarClientes);

            stage.show();

        } catch (IOException e) {

            e.printStackTrace();
            Logger.getLogger(MenuFuncionario.class.getName()).log(Level.WARNING, e.getMessage(), e);

            Alert alert = new AlertIOException(Alert.AlertType.ERROR, e);
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

            loader.setController(new GerenciarCategorias(usuario));

            root = loader.load();

            Scene gerenciarCategorias = new Scene(root);
            stage.setScene(gerenciarCategorias);

            stage.show();

        } catch (IOException e) {

            e.printStackTrace();
            Logger.getLogger(MenuFuncionario.class.getName()).log(Level.WARNING, e.getMessage(), e);

            Alert alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

    }

    public void efetuarVenda(ActionEvent actionEvent){

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root;

        try {

            URL urlEfetuarVenda = getClass().getResource("/view/telasFuncionario/EfetuarVenda.fxml");

            FXMLLoader loader = new FXMLLoader(urlEfetuarVenda);
            loader.setController(new EfetuarVenda(usuario));

            root = loader.load();

            Scene efetuarVenda = new Scene(root);
            stage.setScene(efetuarVenda);

            stage.show();

        } catch (IOException e) {

            e.printStackTrace();
            Logger.getLogger(MenuFuncionario.class.getName()).log(Level.WARNING, e.getMessage(), e);

            Alert alert = new AlertIOException(Alert.AlertType.ERROR, e);
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
            Logger.getLogger(MenuFuncionario.class.getName()).log(Level.WARNING, e.getMessage(), e);

            Alert alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

    }

    public Usuario getUsuario() {

        return usuario;

    }

}
