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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.bean.Produto;
import model.bean.Usuario;
import model.service.ProdutoService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GerenciarEstoque implements Initializable {

    private ProdutoService produtoService = ProdutoService.getInstance();

    private Usuario usuario;
    private Produto produto;

    @FXML
    private Label labelNomeProdutoSelecionado;

    @FXML
    private Label labelCategoriaProdutoSelecionado;

    @FXML
    private Label labelPrecoProdutoSelecionado;

    @FXML
    private Label labelQuantidadeProdutoSelecionado;

    @FXML
    private ListView<Produto> listViewProdutos;

    private ArrayList<Produto> arrayListProdutos;

    private ObservableList<Produto> observableListProdutos;

    public GerenciarEstoque(Usuario usuario) {

        this.usuario = usuario;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        carregarListViewProdutos();

        listViewProdutos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarProduto(newValue));

    }

    private void selecionarProduto(Produto produto) {

        if (produto != null) {

            this.produto = produto;

            labelNomeProdutoSelecionado.setText(produto.getNome());
            labelCategoriaProdutoSelecionado.setText(produto.getCategoria().getNome());
            labelPrecoProdutoSelecionado.setText("R$ " + produto.getPrecoDeVenda());
            labelQuantidadeProdutoSelecionado.setText(String.valueOf(produto.getQuantidade()));

            return;
        }

        labelNomeProdutoSelecionado.setText("");
        labelCategoriaProdutoSelecionado.setText("");
        labelPrecoProdutoSelecionado.setText("");
        labelQuantidadeProdutoSelecionado.setText("");

    }

    private void carregarListViewProdutos() {

        arrayListProdutos = produtoService.listarTodosProdutos();

        observableListProdutos = FXCollections.observableList(arrayListProdutos);

        listViewProdutos.setItems(observableListProdutos);

    }

    public void cadastrarProduto(ActionEvent actionEvent){

        try {

            URL url = getClass().getResource("/view/telasGlobais/CadastrarProdutoDialog.fxml");

            FXMLLoader loader = new FXMLLoader(url);

            loader.setController(new CadastrarProdutoDialog(null, true));

            AnchorPane pane = loader.load();

            Scene scene = new Scene(pane);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastrar Produto");

            dialogStage.setScene(scene);

            dialogStage.initModality(Modality.APPLICATION_MODAL);

            dialogStage.showAndWait();

            carregarListViewProdutos();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public void excluirProduto(ActionEvent actionEvent){

        if(this.produto != null){

            produtoService.deletarProduto(this.produto);

            carregarListViewProdutos();

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um registro na lista.");
            alert.show();

        }

    }

    public void editarProduto(ActionEvent actionEvent){

        if(this.produto != null){

            try{

                URL url = getClass().getResource("/view/telasGlobais/CadastrarProdutoDialog.fxml");

                FXMLLoader loader = new FXMLLoader(url);
                loader.setController(new CadastrarProdutoDialog(this.produto, false));

                Parent root = loader.load();

                Scene scene = new Scene(root);

                Stage dialogStage = new Stage();
                dialogStage.setScene(scene);
                dialogStage.setTitle("Editar Produto");
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.showAndWait();

                carregarListViewProdutos();

            }catch (IOException e) {

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
}
