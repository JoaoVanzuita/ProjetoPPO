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
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
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

        }

    }

    public void salvar(ActionEvent actionEvent){

        if(validarCampos()){

            String nome = tfNomeUsuario.getText().trim();
            String email = tfEmailUsuario.getText().trim();
            String senha = pfSenhaUsuario.getText().trim();

            this.usuario.setNome(nome);
            this.usuario.setEmail(email);
            this.usuario.setSenha(senha);
            this.usuario.setEndereco(this.endereco);

            usuarioService.editarUsuario(this.usuario);

            voltar(actionEvent);

        }else{

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Insira valores válidos nos campos.");
            alert.showAndWait();

        }

    }

    private boolean validarCampos(){

        //TODO: validação com regex

        if(this.tfNomeUsuario.getText().length() < 4)
            return false;

        if(this.tfEmailUsuario.getText().length() < 5)
            return false;

        if(this.pfSenhaUsuario.getText().length() < 8)
            return false;

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

        }

    }


    public void setEndereco(Endereco endereco) {

        this.endereco = endereco;

    }
}
