package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.bean.Usuario;
import model.service.UsuarioService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GerenciarFuncionarios implements Initializable {

    //O nome "funcionario" é mais interessante que "usuario" neste contexto

    private UsuarioService funcionarioService = UsuarioService.getInstance();

    private Usuario funcionario;

    @FXML
    private Label labelNomeFuncionarioSelecionado;

    @FXML
    private Label labelEmailFuncionarioSelecionado;

    @FXML
    private Label labelCidadeFuncionarioSelecionado;

    @FXML
    private Label labelUfFuncionarioSelecionado;

    @FXML
    private Label labelCepFuncionarioSelecionado;

    @FXML
    private ListView<Usuario> listViewFuncionarios;

    private ArrayList<Usuario> arrayListFuncionarios;

    private ObservableList<Usuario> observableListFuncionarios;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        carregarListViewFuncionarios();

        listViewFuncionarios.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarFuncionario(newValue));

    }

    private void selecionarFuncionario(Usuario funcionario) {

        if(funcionario != null) {

            this.funcionario = funcionario;

            labelNomeFuncionarioSelecionado.setText(funcionario.getNome());
            labelEmailFuncionarioSelecionado.setText(funcionario.getEmail());
            labelCidadeFuncionarioSelecionado.setText(funcionario.getEndereco().getCidade());
            labelUfFuncionarioSelecionado.setText(funcionario.getEndereco().getUf());
            labelCepFuncionarioSelecionado.setText(funcionario.getEndereco().getCep());

            return;

        }

        labelNomeFuncionarioSelecionado.setText("");
        labelEmailFuncionarioSelecionado.setText("");
        labelCidadeFuncionarioSelecionado.setText("");
        labelUfFuncionarioSelecionado.setText("");
        labelCepFuncionarioSelecionado.setText("");


    }

    private void carregarListViewFuncionarios() {

        arrayListFuncionarios = funcionarioService.listarTodosFuncionarios();

        observableListFuncionarios = FXCollections.observableList(arrayListFuncionarios);

        listViewFuncionarios.setItems(observableListFuncionarios);

    }

    public void voltar(ActionEvent actionEvent) {

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root;

        try{

            URL urlMenuGerente = getClass().getResource("/view/telasGerente/MenuGerente.fxml");
            FXMLLoader loader = new FXMLLoader(urlMenuGerente);

            root = loader.load();

            Scene menuGerente = new Scene(root);
            stage.setScene(menuGerente);

            stage.show();

        }catch (IOException e){

            e.printStackTrace();

        }

    }

    public void cadastrarFuncionario(ActionEvent actionEvent) {

        try {

            URL url = getClass().getResource("/view/telasGerente/CadastrarFuncionarioDialog.fxml");

            FXMLLoader loader = new FXMLLoader(url);

            loader.setController(new CadastrarFuncionarioDialog(null, true));

            AnchorPane pane = loader.load();

            Scene scene = new Scene(pane);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastrar Funcionário");

            dialogStage.setScene(scene);

            dialogStage.initModality(Modality.APPLICATION_MODAL);

            dialogStage.showAndWait();

            carregarListViewFuncionarios();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public void excluirFuncionario(ActionEvent actionEvent) {

        if(this.funcionario != null){

            funcionarioService.deletarUsuario(this.funcionario);

            carregarListViewFuncionarios();

            return;

        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Por favor, selecione um registro na lista.");
        alert.show();

    }

    public void editarFuncionario(ActionEvent actionEvent) {

        if (this.funcionario != null) {

            try {

                URL url = getClass().getResource("/view/telasGerente/CadastrarFuncionarioDialog.fxml");

                FXMLLoader loader = new FXMLLoader(url);

                loader.setController(new CadastrarFuncionarioDialog(this.funcionario, false));

                AnchorPane pane = loader.load();

                Scene scene = new Scene(pane);

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Editar Funcionario");

                dialogStage.setScene(scene);

                dialogStage.initModality(Modality.APPLICATION_MODAL);

                dialogStage.showAndWait();

                carregarListViewFuncionarios();

            } catch (IOException e) {

                e.printStackTrace();

            }

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um registro na lista.");
            alert.show();

        }


    }
}
