package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.bean.Categoria;
import model.bean.Usuario;
import model.service.CategoriaService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GerenciarCategorias implements Initializable {

    private CategoriaService categoriaService = CategoriaService.getInstance();

    private Categoria categoria;

    private Usuario usuario;

    @FXML
    private ListView<Categoria> listViewCategorias;

    private ArrayList<Categoria> arrayListCategorias;

    ObservableList<Categoria> observableListCategorias;

    public GerenciarCategorias(Usuario usuario) {

        this.usuario = usuario;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        carregarListViewCategorias();

        //Atualiza a categoria selecionada
        listViewCategorias.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selecionarCategoria(newValue);

        });

    }

    public void excluirCategoria(ActionEvent actionEvent) {

        if(this.categoria != null){

            categoriaService.deletarCategoria(this.categoria);
            carregarListViewCategorias();

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um registro na lista.");
            alert.show();

        }

    }

    public void editarCategoria(ActionEvent actionEvent) {

        if (this.categoria != null) {

            try {

                URL url = getClass().getResource("/view/telasGlobais/CadastrarCategoriaDialog.fxml");

                FXMLLoader loader = new FXMLLoader(url);
                loader.setController(new CadastrarCategoriaDialog(this.categoria, false));

                AnchorPane pane = loader.load();

                Scene scene = new Scene(pane);

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Editar Categoria");

                dialogStage.setScene(scene);

                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.showAndWait();

                carregarListViewCategorias();

            } catch (IOException e) {

                e.printStackTrace();

            }

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um registro na lista.");
            alert.show();

        }

    }

    public void voltar(ActionEvent actionEvent) {

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

    public void cadastrarCategoria(ActionEvent actionEvent) {

        try {

            URL url = getClass().getResource("/view/telasGlobais/CadastrarCategoriaDialog.fxml");

            FXMLLoader loader = new FXMLLoader(url);
            loader.setController(new CadastrarCategoriaDialog(null, true));

            AnchorPane pane = loader.load();

            Scene scene = new Scene(pane);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastrar Categoria");

            dialogStage.setScene(scene);

            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.showAndWait();

            carregarListViewCategorias();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    private void selecionarCategoria(Categoria categoria){

        this.categoria = categoria;

    }

    private void carregarListViewCategorias(){

        arrayListCategorias = categoriaService.listarTodasCategorias();

        observableListCategorias = FXCollections.observableList(arrayListCategorias);

        listViewCategorias.setItems(observableListCategorias);

    }
}
