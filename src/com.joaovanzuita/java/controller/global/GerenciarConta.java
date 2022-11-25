package controller.global;

import controller.gerente.MenuGerente;
import controller.funcionario.MenuFuncionario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import util.Alert;
import javafx.scene.control.PasswordField;
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

public class GerenciarConta implements Initializable {

    private UsuarioService usuarioService = UsuarioService.getInstance();
    private Usuario usuario;
    private MenuGerente menuGerente;
    private MenuFuncionario menuFuncionario;
    private Endereco endereco;

    @FXML
    private TextField tfNomeUsuario;
    @FXML
    private TextField tfEmailUsuario;
    @FXML
    private PasswordField pfSenhaUsuario;

    public GerenciarConta(MenuGerente menuGerente) {

        this.menuGerente = menuGerente;
        this.usuario = menuGerente.getUsuario();
        this.endereco = this.usuario.getEndereco();

    }

    public GerenciarConta(MenuFuncionario menuFuncionario) {

        this.menuFuncionario = menuFuncionario;
        this.usuario = menuFuncionario.getUsuario();
        this.endereco = this.usuario.getEndereco();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.tfNomeUsuario.setText(this.usuario.getNome());
        this.tfEmailUsuario.setText(this.usuario.getEmail());
        this.pfSenhaUsuario.setText(this.usuario.getSenha());

    }

    public void endereco(ActionEvent actionEvent){

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
            Logger.getLogger(GerenciarConta.class.getName()).log(Level.WARNING, e.getMessage(), e);

            AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

    }

    public void salvar(ActionEvent actionEvent){

        if(!validarCampos()){

            return;

        }

        String nome = tfNomeUsuario.getText().trim();
        String email = tfEmailUsuario.getText().trim();
        String senha = pfSenhaUsuario.getText().trim();

        this.usuario.setNome(nome);
        this.usuario.setEmail(email);
        this.usuario.setSenha(senha);
        this.usuario.setEndereco(this.endereco);

        usuarioService.editarUsuario(this.usuario);

        voltar(actionEvent);
    }

    private boolean validarCampos(){

        Pattern email = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
        Pattern senha = Pattern.compile("[^\\W_]*");
        Pattern nome = Pattern.compile("^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ'\\s]*");

        Matcher matcherEmail = email.matcher(tfEmailUsuario.getText().trim());
        Matcher matcherSenha = senha.matcher(pfSenhaUsuario.getText().trim());
        Matcher matcherNome = nome.matcher(tfNomeUsuario.getText().trim());

        if(!matcherEmail.matches()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Email inválido.");
            alert.showAndWait();

            return false;
        }

        if(!matcherSenha.matches()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Senha deve ter somente caracteres alfanuméricos (de A a Z e 0 a 9).");
            alert.showAndWait();

            return false;
        }

        if(!matcherNome.matches()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Nome deve ser composto apenas por letras.");
            alert.showAndWait();

            return false;
        }

        if (pfSenhaUsuario.getText().trim().length() < 8){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Senha deve ter pelo menos 8 caracteres.");
            alert.showAndWait();

            return false;
        }

        if (tfNomeUsuario.getText().trim().length() < 4){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Nome deve ter ao menos 4 caracteres.");
            alert.showAndWait();

            return false;
        }

        return true;
    }

    public void voltar(ActionEvent actionEvent){

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root;

        try{

            URL url;
            FXMLLoader loader;
            Object controller;

            if(this.usuario.getTipoDeUsuario() == 0) {

                url = getClass().getResource("/view/telasFuncionario/MenuFuncionario.fxml");
                controller = new MenuFuncionario(this.usuario);

            }else{

                url = getClass().getResource("/view/telasGerente/MenuGerente.fxml");
                controller = new MenuGerente(this.usuario);

            }

            loader = new FXMLLoader(url);
            loader.setController(controller);

            root = loader.load();

            Scene menu = new Scene(root);
            stage.setScene(menu);

            stage.show();


        }catch (IOException e){

            e.printStackTrace();
            Logger.getLogger(GerenciarConta.class.getName()).log(Level.WARNING, e.getMessage(), e);

            AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

    }


    public void setEndereco(Endereco endereco) {

        this.endereco = endereco;

    }
}
