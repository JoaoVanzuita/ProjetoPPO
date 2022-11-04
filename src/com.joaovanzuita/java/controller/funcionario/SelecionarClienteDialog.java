package controller.funcionario;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import util.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.bean.Cliente;
import model.service.ClienteService;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SelecionarClienteDialog implements Initializable {

    private ClienteService clienteService = ClienteService.getInstance();
    private EfetuarVenda efetuarVendaController;
    private Cliente cliente;
    private ArrayList<Cliente> arrayListClientes;
    private ObservableList<Cliente> observableListClientes;

    @FXML
    private TextField tfPesquisarCliente;
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

    public SelecionarClienteDialog(EfetuarVenda efetuarVendaController) {

        this.efetuarVendaController = efetuarVendaController;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        carregarListViewClientes(null);

        listViewClientes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarCliente(newValue));

        tfPesquisarCliente.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {

            carregarListViewClientes(tfPesquisarCliente.getText());

        });

    }

    private void carregarListViewClientes(String nomeCliente) {

        if(nomeCliente == null){

            arrayListClientes = clienteService.listarTodosClientes();

        }else{

            arrayListClientes = clienteService.consultarClientePorNome(nomeCliente);

        }

        observableListClientes = FXCollections.observableList(arrayListClientes);

        listViewClientes.setItems(observableListClientes);

    }

    private void selecionarCliente(Cliente cliente) {

        if(cliente != null) {

            this.cliente = cliente;

            labelNomeClienteSelecionado.setText(labelNomeClienteSelecionado.getText() + cliente.getNome());
            labelEmailClienteSelecionado.setText(labelEmailClienteSelecionado.getText() + cliente.getEmail());
            labelCidadeClienteSelecionado.setText(labelCidadeClienteSelecionado.getText() + cliente.getEndereco().getCidade());
            labelUfClienteSelecionado.setText(labelUfClienteSelecionado.getText() + cliente.getEndereco().getUf());
            labelCepClienteSelecionado.setText(labelCepClienteSelecionado.getText() + cliente.getEndereco().getCep());

            return;

        }

        labelNomeClienteSelecionado.setText("Nome: ");
        labelEmailClienteSelecionado.setText("Email: ");
        labelCidadeClienteSelecionado.setText("Cidade: ");
        labelUfClienteSelecionado.setText("UF: ");
        labelCepClienteSelecionado.setText("CEP: ");
    }

    public void selecionar(ActionEvent actionEvent){

        if(cliente != null){

            efetuarVendaController.setCliente(cliente);

            voltar(actionEvent);

            return;

        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Nenhum cliente selecionado.");
        alert.setContentText("Selecione um registro da lista.");
        alert.showAndWait();

    }

    public void voltar(ActionEvent actionEvent){

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

    }

}
