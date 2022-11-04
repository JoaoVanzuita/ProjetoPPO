package controller.global;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import util.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.bean.Categoria;
import model.service.CategoriaService;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadastrarCategoriaDialog implements Initializable {

    private CategoriaService categoriaService = CategoriaService.getInstance();
    private Categoria categoriaRecebida;
    //Define se o botão de salvar irá cadastrar uma nova categoria ou editar uma existente
    private boolean cadastrarNovaCategoria;

    @FXML
    private TextField tfNomeCategoria;

    public CadastrarCategoriaDialog(Categoria categoria, boolean cadastrarNovaCategoria) {

        this.categoriaRecebida = categoria;
        this.cadastrarNovaCategoria = cadastrarNovaCategoria;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(categoriaRecebida != null){

            tfNomeCategoria.setText(categoriaRecebida.getNome());

        }

    }

    public void salvarCategoria(ActionEvent actionEvent) {

        if(!validarCampos()) {

            return;
        }

        String nome = tfNomeCategoria.getText().trim();

        Categoria categoria = categoriaService.criarCategoria(nome);

        if(cadastrarNovaCategoria) {

            categoriaService.cadastrarCategoria(categoria);

        } else {

            this.categoriaRecebida.setNome(nome);
            categoriaService.editarCategoria(this.categoriaRecebida);

        }

        voltar(actionEvent);

    }

    private boolean validarCampos(){

        Pattern nome = Pattern.compile("^^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ'\\s]*");

        Matcher matcherNome = nome.matcher(tfNomeCategoria.getText().trim());

        if(!matcherNome.matches()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Nome deve ser composto apenas por letras.");
            alert.showAndWait();

            return false;
        }

        if(tfNomeCategoria.getText().trim().length() < 4){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Nome deve ter ao menos 4 caracteres.");
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

}
