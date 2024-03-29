package controller.funcionario;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.bean.Cliente;
import model.bean.Usuario;
import model.service.ClienteService;
import util.AlertIOException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GerenciarClientes implements Initializable {

    private ClienteService clienteService = ClienteService.getInstance();
    private Cliente cliente;
    private Usuario usuario;
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

    public GerenciarClientes(Usuario usuario) {

        this.usuario = usuario;

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

        labelNomeClienteSelecionado.setText("Nome: ");
        labelEmailClienteSelecionado.setText("Email: ");
        labelCidadeClienteSelecionado.setText("Cidade: ");
        labelUfClienteSelecionado.setText("UF: ");
        labelCepClienteSelecionado.setText("CEP: ");

        if(cliente != null) {

            this.cliente = cliente;

            labelNomeClienteSelecionado.setText(labelNomeClienteSelecionado.getText() + cliente.getNome());
            labelEmailClienteSelecionado.setText(labelEmailClienteSelecionado.getText() + cliente.getEmail());
            labelCidadeClienteSelecionado.setText(labelCidadeClienteSelecionado.getText() + cliente.getEndereco().getCidade());
            labelUfClienteSelecionado.setText(labelUfClienteSelecionado.getText() + cliente.getEndereco().getUf());
            labelCepClienteSelecionado.setText(labelCepClienteSelecionado.getText() + cliente.getEndereco().getCep());

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

            carregarListViewClientes(null);

        } catch (IOException e) {

            e.printStackTrace();
            Logger.getLogger(GerenciarClientes.class.getName()).log(Level.WARNING, e.getMessage(), e);

            AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

    }

    public void editarCliente(ActionEvent actionEvent){

        if (cliente != null) {

            try {

                URL url = getClass().getResource("/view/telasFuncionario/CadastrarClienteDialog.fxml");

                FXMLLoader loader = new FXMLLoader(url);
                loader.setController(new CadastrarClienteDialog(cliente, false));

                Parent root = loader.load();

                Scene scene = new Scene(root);

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Editar Cliente");

                dialogStage.setScene(scene);

                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.showAndWait();

                carregarListViewClientes(null);

                this.cliente = null;
                selecionarCliente(null);

            } catch (IOException e) {

                e.printStackTrace();
                Logger.getLogger(GerenciarClientes.class.getName()).log(Level.WARNING, e.getMessage(), e);

                AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
                alert.showAndWait();
            }

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Nenhum cliente selecionado.");
            alert.setContentText("Por favor, selecione um registro na lista.");
            alert.show();

        }

    }

    public void excluirCliente(ActionEvent actionEvent){

        if(cliente != null){

            clienteService.deletarCliente(cliente);

            carregarListViewClientes(null);

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Nenhum cliente selecionado.");
            alert.setContentText("Por favor, selecione um registro na lista.");

            alert.show();

        }

    }

    public void voltar(ActionEvent actionEvent) {

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root;

        try{

            URL urlMenuFuncionario = getClass().getResource("/view/telasFuncionario/MenuFuncionario.fxml");
            FXMLLoader loader = new FXMLLoader(urlMenuFuncionario);
            loader.setController(new MenuFuncionario(this.usuario));

            root = loader.load();

            Scene menuFuncionario = new Scene(root);
            stage.setScene(menuFuncionario);

            stage.show();


        }catch (IOException e){

            e.printStackTrace();
            Logger.getLogger(GerenciarClientes.class.getName()).log(Level.WARNING, e.getMessage(), e);

            AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

    }

}
