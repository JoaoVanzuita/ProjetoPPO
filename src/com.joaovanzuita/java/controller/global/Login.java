package controller.global;

import controller.gerente.MenuGerente;
import controller.funcionario.MenuFuncionario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import util.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.bean.Usuario;
import model.service.UsuarioService;
import util.AlertIOException;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Login{

    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField pfSenha;

    @FXML
    public void entrar(ActionEvent actionEvent){

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root;

//        if (!validarCampos()){
//
//            return;
//
//        }

        String email = tfEmail.getText().trim();
        String senha = pfSenha.getText().trim();

        UsuarioService usuarioService = UsuarioService.getInstance();

        Usuario usuario = usuarioService.verificarCadastro(email, senha);

        if (usuario == null){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Login inválido.");
            alert.setContentText("Usuário não encontrado.");
            alert.showAndWait();

            return;

        }

        if(usuario.getTipoDeUsuario() == 0){

            try{

                URL urlMenuFuncionario = getClass().getResource("/view/telasFuncionario/MenuFuncionario.fxml");
                FXMLLoader loader = new FXMLLoader(urlMenuFuncionario);

                loader.setController(new MenuFuncionario(usuario));

                root = loader.load();

                Scene menuFuncionario = new Scene(root);
                stage.setScene(menuFuncionario);

                stage.show();


            }catch (IOException e){

                e.printStackTrace();
                Logger.getLogger(Login.class.getName()).log(Level.WARNING, e.getMessage(), e);

                AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
                alert.showAndWait();
            }

        }else if(usuario.getTipoDeUsuario() == 1){

            try{

                URL urlMenuGerente = getClass().getResource("/view/telasGerente/MenuGerente.fxml");
                FXMLLoader loader = new FXMLLoader(urlMenuGerente);

                loader.setController(new MenuGerente(usuario));

                root = loader.load();

                Scene menuGerente = new Scene(root);
                stage.setScene(menuGerente);

                stage.show();


            }catch (IOException e){

                e.printStackTrace();
                Logger.getLogger(Login.class.getName()).log(Level.WARNING, e.getMessage(), e);

                AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
                alert.showAndWait();
            }

        }

    }

    private boolean validarCampos(){

        Pattern email = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
        Pattern senha = Pattern.compile("[^\\W_]*");

        Matcher matcherEmail = email.matcher(tfEmail.getText().trim());
        Matcher matcherSenha = senha.matcher(pfSenha.getText().trim());

        if(!matcherEmail.matches()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Entrada inválida.");
            alert.setContentText("Email inválido.");
            alert.showAndWait();

            return false;
        }

        if (pfSenha.getText().trim().length() != 8 && pfSenha.getText().trim().length() != 0){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Entrada inválida.");
            alert.setContentText("Senha deve ter 0 (ainda não criada) ou 8 caracteres.");
            alert.showAndWait();

            return false;
        }

        if(!matcherSenha.matches()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Entrada inválida.");
            alert.setContentText("Senha deve ter somente caracteres alfanuméricos (de A a Z e 0 a 9).");
            alert.showAndWait();

            return false;
        }

        return true;
    }
}
