package controller.funcionario;

import controller.global.CriarEnderecoDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import util.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.bean.Cliente;
import model.bean.Endereco;
import model.service.ClienteService;
import util.AlertIOException;
import util.AlertSQLException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            Logger.getLogger(CadastrarClienteDialog.class.getName()).log(Level.WARNING, e.getMessage(), e);

            AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();

        }

    }

    public void salvar(ActionEvent actionEvent) {

//        if (!validarCampos()) {
//
//            return;
//        }

        String nome = tfNomeCadastrarCliente.getText().trim();
        String email = tfEmailCadastrarCliente.getText().trim();

        Cliente cliente = clienteService.criarCliente(nome, email, endereco);

        if (cadastrarNovocliente) {

            clienteService.cadastrarCliente(cliente);

        } else {

            //clienteRecebido possui o ID para editar o registro no banco de dados
            clienteRecebido.setNome(nome);
            clienteRecebido.setEmail(email);
            clienteRecebido.setEndereco(endereco);

            clienteService.editarCliente(clienteRecebido);

        }

        voltar(actionEvent);

    }

    private boolean validarCampos() {

        Pattern nome = Pattern.compile("^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ'\\s]+$\n");
        Pattern email = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");

        Matcher matcherNome = nome.matcher(tfNomeCadastrarCliente.getText().trim());
        Matcher matcherEmail = email.matcher(tfEmailCadastrarCliente.getText().trim());

        if(!matcherNome.matches()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Nome deve ser composto apenas por letras.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/css/Application.css").toString());
            alert.getDialogPane().getStyleClass().add("dialog");
            alert.showAndWait();

            return false;
        }

        if(!matcherEmail.matches()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Email inválido.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/css/Application.css").toString());
            alert.getDialogPane().getStyleClass().add("dialog");
            alert.showAndWait();

            return false;
        }

        if(tfNomeCadastrarCliente.getText().trim().length() < 4){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Nome deve ter ao menos 4 caracteres.");

            alert.showAndWait();

            return false;
        }

        if (this.endereco == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Endereço não pode estar vazio.");

            alert.showAndWait();

            return false;
        }

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
