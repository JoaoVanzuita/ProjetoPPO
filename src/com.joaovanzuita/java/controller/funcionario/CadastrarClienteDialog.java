package controller.funcionario;

import controller.global.CriarEnderecoDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.bean.Cliente;
import model.bean.Endereco;
import model.service.ClienteService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CadastrarClienteDialog implements Initializable {

    private ClienteService clienteService = ClienteService.getInstance();
    private Endereco endereco = null;
    private Cliente clienteRecebido;
    private boolean cadastrarNovocliente;

    @FXML
    private TextField tfNomeCadastrarCliente;
    @FXML
    private TextField tfEmailCadastrarCliente;

    public CadastrarClienteDialog(Cliente cliente, boolean cadastrarNovocliente) {

        this.clienteRecebido = cliente;
        this.cadastrarNovocliente = cadastrarNovocliente;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        //Se receber um cliente no construtor, preenche os campos para editá-los
        if (clienteRecebido != null) {

            tfNomeCadastrarCliente.setText(clienteRecebido.getNome());
            tfEmailCadastrarCliente.setText(clienteRecebido.getEmail());
            endereco = clienteRecebido.getEndereco();

        }

    }

    public void endereco(ActionEvent actionEvent) {

        try {

            URL url = getClass().getResource("/view/telasGlobais/CriarEnderecoDialog.fxml");

            FXMLLoader loader = new FXMLLoader(url);

            //Se endereço for nulo, vai criar um do zero, senão, vai possibilitar editar os campos
            loader.setController(new CriarEnderecoDialog(endereco, this));

            AnchorPane pane = loader.load();

            Scene scene = new Scene(pane);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Endereço");

            dialogStage.setScene(scene);

            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.showAndWait();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public void salvar(ActionEvent actionEvent) {

        //Inicialmente valida os campos
        //Em seguida cria um cliente e verifica se deve cadastrar um novo ou editar um existente

        if (validarCampos()) {

            String nome = tfNomeCadastrarCliente.getText().trim();
            String email = tfEmailCadastrarCliente.getText().trim();

            Cliente cliente = clienteService.criarCliente(nome, email, endereco);

            if (cadastrarNovocliente) {

                clienteService.cadastrarCliente(cliente);

            } else {

                //O cliente recebido possui o ID para editar o registro no banco de dados
                clienteRecebido.setNome(nome);
                clienteRecebido.setEmail(email);
                clienteRecebido.setEndereco(endereco);

                clienteService.editarCliente(clienteRecebido);

            }

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Insira valores válidos nos campos.");
            alert.showAndWait();

        }

        voltar(actionEvent);

    }

    private boolean validarCampos() {

        //TODO: validação com regex

        if (tfNomeCadastrarCliente.getText().length() < 4)
            return false;

        if (tfEmailCadastrarCliente.getText().length() < 5)
            return false;

        if (this.endereco == null)
            return false;

        return true;
    }

    public void voltar(ActionEvent actionEvent) {

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.close();

    }

    //Método chamado no controller CriarEndereco
    public void setEndereco(Endereco endereco) {

        this.endereco = endereco;

    }
}
