package controller.global;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.bean.Categoria;
import model.service.CategoriaService;

import java.net.URL;
import java.util.ResourceBundle;

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

    @FXML
    public void salvarCategoria(ActionEvent actionEvent) {

        if(validarCampos()){

            String nome = tfNomeCategoria.getText().trim();
            Categoria categoria = categoriaService.criarCategoria(nome);

            if(cadastrarNovaCategoria) {

                categoriaService.cadastrarCategoria(categoria);

            } else {

                this.categoriaRecebida.setNome(nome);
                categoriaService.editarCategoria(this.categoriaRecebida);

            }

        }

        voltar(actionEvent);

    }

    private boolean validarCampos(){

        //TODO: validação com regex

        return tfNomeCategoria.getText().length() >= 4;

    }

    public void voltar(ActionEvent actionEvent) {

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.close();

    }

}
