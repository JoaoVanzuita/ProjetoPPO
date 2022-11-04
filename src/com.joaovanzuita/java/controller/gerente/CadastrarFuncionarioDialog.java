package controller.gerente;

import controller.funcionario.MenuFuncionario;
import controller.global.CriarEnderecoDialog;
import javafx.css.Match;
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
import model.bean.Usuario;
import model.service.UsuarioService;
import util.AlertIOException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            Logger.getLogger(CadastrarFuncionarioDialog.class.getName()).log(Level.WARNING, e.getMessage(), e);

            AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }
    }

    public void salvar(ActionEvent actionEvent) {

//        if (!validarCampos()) {
//
//            return;
//        }

        String nome = tfNomeCadastrarFuncionario.getText().trim();
        String email = tfEmailCadastrarFuncionario.getText().trim();

        Usuario funcionario = funcionarioService.criarUsuario(nome, email, this.endereco);

        if (cadastrarNovoFuncionario) {

            funcionarioService.cadastrarUsuario(funcionario);

        } else {

            //funcionarioRecebido possui o ID para editar o registro no banco de dados

            this.funcionarioRecebido.setNome(nome);
            this.funcionarioRecebido.setEmail(email);
            this.funcionarioRecebido.setEndereco(this.endereco);

            funcionarioService.editarUsuario(this.funcionarioRecebido);

        }

        voltar(actionEvent);
    }

    private boolean validarCampos() {

        Pattern nome = Pattern.compile("^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ'\\s]*");
        Pattern email = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");

        Matcher matcherNome = nome.matcher(tfNomeCadastrarFuncionario.getText().trim());
        Matcher matcherEmail = email.matcher(tfEmailCadastrarFuncionario.getText().trim());

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

        if(tfNomeCadastrarFuncionario.getText().trim().length() < 4){

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

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
