package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.bean.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuGerente implements Initializable {

    private Usuario usuario;

    public MenuGerente(Usuario usuario) {

        this.usuario = usuario;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

        }

    }

    public void gerenciarFornecedores(ActionEvent actionEvent) {

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root;

        try {

            URL urlGerenciarFornecedores = getClass().getResource("/view/telasGerente/GerenciarFornecedores.fxml");

            FXMLLoader loader = new FXMLLoader(urlGerenciarFornecedores);

            root = loader.load();

            Scene gerenciarFornecedores = new Scene(root);
            stage.setScene(gerenciarFornecedores);

            stage.show();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public void gerenciarFuncionarios(ActionEvent actionEvent) {

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root;

        try {

            URL urlGerenciarFuncionarios = getClass().getResource("/view/telasGerente/GerenciarFuncionarios.fxml");

            FXMLLoader loader = new FXMLLoader(urlGerenciarFuncionarios);

            root = loader.load();

            Scene gerenciarFuncionarios = new Scene(root);
            stage.setScene(gerenciarFuncionarios);

            stage.show();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}
