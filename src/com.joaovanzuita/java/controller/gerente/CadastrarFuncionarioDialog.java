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
import model.bean.Usuario;
import model.service.UsuarioService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CadastrarFuncionarioDialog implements Initializable {

    //O nome "funcionario" é mais interessante que "usuario" neste contexto
    private UsuarioService funcionarioService = UsuarioService.getInstance();
    private Usuario funcionarioRecebido;
    private Endereco endereco;
    private boolean cadastrarNovoFuncionario;

    @FXML
    private TextField tfNomeCadastrarFuncionario;
    @FXML
    private TextField tfEmailCadastrarFuncionario;

    public CadastrarFuncionarioDialog(Usuario funcionario, boolean cadastrarNovoFuncionario) {

        this.funcionarioRecebido = funcionario;
        this.cadastrarNovoFuncionario = cadastrarNovoFuncionario;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Se receber um Usuario funcionario no construtor, preenche os campos para editá-los
        if (funcionarioRecebido != null) {

            tfNomeCadastrarFuncionario.setText(funcionarioRecebido.getNome());
            tfEmailCadastrarFuncionario.setText(funcionarioRecebido.getEmail());
            endereco = funcionarioRecebido.getEndereco();

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

            String nome = tfNomeCadastrarFuncionario.getText().trim();
            String email = tfEmailCadastrarFuncionario.getText().trim();

            Usuario funcionario = funcionarioService.criarUsuario(nome, email, this.endereco);

            if (cadastrarNovoFuncionario) {

                funcionarioService.cadastrarUsuario(funcionario);

            } else {

                //fornecedorRecebido possui o ID para editar o registro no banco de dados

                this.funcionarioRecebido.setNome(nome);
                this.funcionarioRecebido.setEmail(email);
                this.funcionarioRecebido.setEndereco(this.endereco);

                funcionarioService.editarUsuario(this.funcionarioRecebido);

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

        if (tfNomeCadastrarFuncionario.getText().length() < 4)
            return false;

        if (tfEmailCadastrarFuncionario.getText().length() < 5)
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

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
