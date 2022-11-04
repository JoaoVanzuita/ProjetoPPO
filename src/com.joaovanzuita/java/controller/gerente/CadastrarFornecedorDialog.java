package controller.gerente;

import controller.funcionario.MenuFuncionario;
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
import model.bean.Endereco;
import model.bean.Fornecedor;
import model.service.FornecedorService;
import util.AlertIOException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadastrarFornecedorDialog implements Initializable {

    private FornecedorService fornecedorService = FornecedorService.getInstance();
    private Fornecedor fornecedorRecebido;
    private Endereco endereco;
    private boolean cadastrarNovoFornecedor;

    @FXML
    private TextField tfNomeCadastrarFornecedor;
    @FXML
    private TextField tfEmailCadastrarFornecedor;


    public CadastrarFornecedorDialog(Fornecedor fornecedor, boolean cadastrarNovoFornecedor) {

        this.fornecedorRecebido = fornecedor;
        this.cadastrarNovoFornecedor = cadastrarNovoFornecedor;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Se receber um fornecedor no construtor, preenche os campos para editá-los
        if(fornecedorRecebido != null){

            tfNomeCadastrarFornecedor.setText(fornecedorRecebido.getNome());
            tfEmailCadastrarFornecedor.setText(fornecedorRecebido.getEmail());
            endereco = fornecedorRecebido.getEndereco();

        }

    }

    public void endereco(ActionEvent actionEvent) {

        try {

            URL url = getClass().getResource("/view/telasGlobais/CriarEnderecoDialog.fxml");

            FXMLLoader loader = new FXMLLoader(url);

            //Se endereço for nulo, vai criar um do zero, senão, vai possibilitar editar os campos
            loader.setController(new CriarEnderecoDialog(this.endereco, this));

            AnchorPane pane = loader.load();

            Scene scene = new Scene(pane);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Endereço");

            dialogStage.setScene(scene);

            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.showAndWait();

        } catch (IOException e) {

            e.printStackTrace();
            Logger.getLogger(CadastrarFornecedorDialog.class.getName()).log(Level.WARNING, e.getMessage(), e);

            AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

    }

    public void salvar(ActionEvent actionEvent) {

//        if (!validarCampos()) {
//
//            return;
//        }

        String nome = tfNomeCadastrarFornecedor.getText().trim();
        String email = tfEmailCadastrarFornecedor.getText().trim();

        Fornecedor fornecedor = fornecedorService.criarFornecedor(nome, email, this.endereco);

        if (cadastrarNovoFornecedor) {

            fornecedorService.cadastrarFornecedor(fornecedor);

        } else {

            //fornecedorRecebido possui o ID para editar o registro no banco de dados

            this.fornecedorRecebido.setNome(nome);
            this.fornecedorRecebido.setEmail(email);
            this.fornecedorRecebido.setEndereco(this.endereco);

            fornecedorService.editarFornecedor(fornecedorRecebido);

        }

        voltar(actionEvent);
    }

    private boolean validarCampos() {

        Pattern nome = Pattern.compile("^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ'\\s]*");
        Pattern email = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");

        Matcher matcherNome = nome.matcher(tfNomeCadastrarFornecedor.getText().trim());
        Matcher matcherEmail = email.matcher(tfEmailCadastrarFornecedor.getText().trim());

        if(!matcherNome.matches()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Nome deve ser composto apenas por letras.");
            alert.showAndWait();

            return false;
        }

        if(!matcherEmail.matches()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Email inválido.");
            alert.showAndWait();

            return false;
        }

        if(tfNomeCadastrarFornecedor.getText().trim().length() < 4){

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
