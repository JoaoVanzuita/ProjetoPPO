package controller.gerente;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import util.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.bean.Usuario;
import model.service.UsuarioService;
import util.AlertIOException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GerenciarFuncionarios implements Initializable {

    //O nome "funcionario" é mais interessante que "usuario" neste contexto
    private UsuarioService funcionarioService = UsuarioService.getInstance();
    private Usuario funcionario;
    private Usuario gerente;
    private ArrayList<Usuario> arrayListFuncionarios;
    private ObservableList<Usuario> observableListFuncionarios;

    @FXML
    private TextField tfPesquisarFuncionario;
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

    public GerenciarFuncionarios(Usuario gerente) {

        this.gerente = gerente;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        carregarListViewFuncionarios(null);

        listViewFuncionarios.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarFuncionario(newValue));

        tfPesquisarFuncionario.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {

                carregarListViewFuncionarios(tfPesquisarFuncionario.getText());

        });

    }

    private void carregarListViewFuncionarios(String nomeFuncionario) {

        if (nomeFuncionario == null){

            arrayListFuncionarios = funcionarioService.listarTodosFuncionarios();

        }else {

            arrayListFuncionarios = funcionarioService.consultarusuarioPorNome(nomeFuncionario);

        }


        observableListFuncionarios = FXCollections.observableList(arrayListFuncionarios);

        listViewFuncionarios.setItems(observableListFuncionarios);

    }

    private void selecionarFuncionario(Usuario funcionario) {

        labelNomeFuncionarioSelecionado.setText("Nome: ");
        labelEmailFuncionarioSelecionado.setText("Email: ");
        labelCidadeFuncionarioSelecionado.setText("Cidade: ");
        labelUfFuncionarioSelecionado.setText("UF: ");
        labelCepFuncionarioSelecionado.setText("CEP: ");

        if(funcionario != null) {

            this.funcionario = funcionario;

            labelNomeFuncionarioSelecionado.setText(labelNomeFuncionarioSelecionado.getText() + funcionario.getNome());
            labelEmailFuncionarioSelecionado.setText(labelEmailFuncionarioSelecionado.getText() + funcionario.getEmail());
            labelCidadeFuncionarioSelecionado.setText(labelCidadeFuncionarioSelecionado.getText() + funcionario.getEndereco().getCidade());
            labelUfFuncionarioSelecionado.setText(labelUfFuncionarioSelecionado.getText() + funcionario.getEndereco().getUf());
            labelCepFuncionarioSelecionado.setText(labelCepFuncionarioSelecionado.getText() + funcionario.getEndereco().getCep());

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

            carregarListViewFuncionarios(null);

        } catch (IOException e) {

            e.printStackTrace();
            Logger.getLogger(GerenciarFuncionarios.class.getName()).log(Level.WARNING, e.getMessage(), e);

            AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

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

                carregarListViewFuncionarios(null);

                this.funcionario = null;
                selecionarFuncionario(null);

            } catch (IOException e) {

                e.printStackTrace();
                Logger.getLogger(GerenciarFuncionarios.class.getName()).log(Level.WARNING, e.getMessage(), e);

                AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
                alert.showAndWait();
            }

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Nenhum funcionário selecionado.");
            alert.setContentText("Por favor, selecione um registro na lista.");
            alert.show();

        }

    }

    public void excluirFuncionario(ActionEvent actionEvent) {

        if(this.funcionario != null){

            funcionarioService.deletarUsuario(this.funcionario);

            carregarListViewFuncionarios(null );

            return;

        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Nenhum funcionário selecionado.");
        alert.setContentText("Por favor, selecione um registro na lista.");
        alert.show();

    }

    public void voltar(ActionEvent actionEvent) {

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root;

        try{

            URL urlMenuGerente = getClass().getResource("/view/telasGerente/MenuGerente.fxml");
            FXMLLoader loader = new FXMLLoader(urlMenuGerente);
            loader.setController(new MenuGerente(this.gerente));

            root = loader.load();

            Scene menuGerente = new Scene(root);
            stage.setScene(menuGerente);

            stage.show();

        }catch (IOException e){

            e.printStackTrace();
            Logger.getLogger(GerenciarFuncionarios.class.getName()).log(Level.WARNING, e.getMessage(), e);

            AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

    }
}
