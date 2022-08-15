package controller.gerente;

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
import model.bean.Endereco;
import model.bean.Fornecedor;
import model.service.FornecedorService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

        }

    }

    public void salvar(ActionEvent actionEvent) {

        //Inicialmente valida os campos
        //Em seguida cria um cliente e verifica se deve cadastrar um novo ou editar um existente

        if (validarCampos()) {

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

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Insira valores válidos nos campos.");
            alert.showAndWait();

        }

        voltar(actionEvent);

    }

    private boolean validarCampos() {

        //TODO: validação com regex

        if (tfNomeCadastrarFornecedor.getText().length() < 4)
            return false;

        if (tfEmailCadastrarFornecedor.getText().length() < 5)
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
