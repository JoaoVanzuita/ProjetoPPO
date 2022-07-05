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
import model.bean.Cliente;
import model.service.ClienteService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GerenciarClientes implements Initializable {

    private ClienteService clienteService = ClienteService.getInstance();

    private Cliente cliente;

    @FXML
    private Label labelNomeClienteSelecionado;

    @FXML
    private Label labelEmailClienteSelecionado;

    @FXML
    private Label labelCidadeClienteSelecionado;

    @FXML
    private Label labelUfClienteSelecionado;

    @FXML
    private Label labelCepClienteSelecionado;

    @FXML
    private ListView<Cliente> listViewClientes;

    private ArrayList<Cliente> arrayListClientes;

    private ObservableList<Cliente> observableListClientes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        carregarListViewClientes();

        listViewClientes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarCliente(newValue));

    }

    private void selecionarCliente(Cliente cliente) {

        if(cliente != null) {

            this.cliente = cliente;

            labelNomeClienteSelecionado.setText(cliente.getNome());
            labelEmailClienteSelecionado.setText(cliente.getEmail());
            labelCidadeClienteSelecionado.setText(cliente.getEndereco().getCidade());
            labelUfClienteSelecionado.setText(cliente.getEndereco().getUf());
            labelCepClienteSelecionado.setText(cliente.getEndereco().getCep());

            return;

        }

            labelNomeClienteSelecionado.setText("");
            labelEmailClienteSelecionado.setText("");
            labelCidadeClienteSelecionado.setText("");
            labelUfClienteSelecionado.setText("");
            labelCepClienteSelecionado.setText("");

    }

    private void carregarListViewClientes() {

        arrayListClientes = clienteService.listarTodosClientes();

        observableListClientes = FXCollections.observableList(arrayListClientes);

        listViewClientes.setItems(observableListClientes);

    }

    public void voltar(ActionEvent actionEvent) {

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root;

        try{

            URL urlMenuFuncionario = getClass().getResource("/view/telasFuncionario/MenuFuncionario.fxml");
            FXMLLoader loader = new FXMLLoader(urlMenuFuncionario);

            root = loader.load();

            Scene menuFuncionario = new Scene(root);
            stage.setScene(menuFuncionario);

            stage.show();


        }catch (IOException e){

            e.printStackTrace();

        }

    }

    public void cadastrarCliente(ActionEvent actionEvent) {

        try {

            URL url = getClass().getResource("/view/telasFuncionario/CadastrarClienteDialog.fxml");

            FXMLLoader loader = new FXMLLoader(url);

            loader.setController(new CadastrarClienteDialog(null, true));

            AnchorPane pane = loader.load();

            Scene scene = new Scene(pane);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastrar Cliente");

            dialogStage.setScene(scene);

            dialogStage.initModality(Modality.APPLICATION_MODAL);

            dialogStage.showAndWait();

            carregarListViewClientes();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public void excluirCliente(ActionEvent actionEvent){

        if(this.cliente != null){

            clienteService.deletarCliente(this.cliente);

            carregarListViewClientes();

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um registro na lista.");
            alert.show();

        }

    }

    public void editarCliente(ActionEvent actionEvent){

        if (this.cliente != null) {

            try {

                URL url = getClass().getResource("/view/telasFuncionario/CadastrarClienteDialog.fxml");

                FXMLLoader loader = new FXMLLoader(url);
                loader.setController(new CadastrarClienteDialog(this.cliente, false));

                Parent root = loader.load();

                Scene scene = new Scene(root);

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Editar Cliente");

                dialogStage.setScene(scene);

                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.showAndWait();

                carregarListViewClientes();

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
