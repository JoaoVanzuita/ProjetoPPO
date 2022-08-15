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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.bean.Usuario;
import model.service.UsuarioService;

import java.io.IOException;
import java.net.URL;


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


        String email = tfEmail.getText().trim();
        String senha = pfSenha.getText().trim();

        if(email.equals("")){
            return;

        }

//        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(email);
//
//        if(!matcher.matches()){
//
//            return;
//
//        }

        UsuarioService usuarioService = UsuarioService.getInstance();

        Usuario usuario = usuarioService.verificarCadastro(email, senha);

        if (usuario == null){

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

            }

        }

    }
}
