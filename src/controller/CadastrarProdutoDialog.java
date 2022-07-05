package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.bean.Categoria;
import model.bean.Fornecedor;
import model.bean.Produto;
import model.service.CategoriaService;
import model.service.FornecedorService;
import model.service.ProdutoService;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CadastrarProdutoDialog implements Initializable {

    private ProdutoService produtoService = ProdutoService.getInstance();

    private CategoriaService categoriaService = CategoriaService.getInstance();

    private FornecedorService fornecedorService = FornecedorService.getInstance();

    private Produto produtoRecebido;

    private Categoria categoria;

    private Fornecedor fornecedor;

    private boolean cadastrarNovoProduto;

    @FXML
    private TextField tfNomeProduto;

    @FXML
    private TextField tfPrecoDeCustoProduto;

    @FXML
    private ListView<Categoria> listViewCategoriasProduto;

    @FXML
    private ListView<Fornecedor> listViewFornecedoresProduto;

    private ArrayList<Categoria> arrayListCategorias;

    private ArrayList<Fornecedor> arrayListFornecedores;

    private ObservableList<Categoria> observableListCategorias;

    private ObservableList<Fornecedor> observableListFornecedores;

    public CadastrarProdutoDialog(Produto produto, boolean cadastrarNovoProduto) {

        this.produtoRecebido = produto;
        this.cadastrarNovoProduto = cadastrarNovoProduto;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listViewCategoriasProduto.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarCategoria(newValue));

        listViewFornecedoresProduto.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarFornecedor(newValue));

        carregarListViewCategorias();
        carregarListViewFornecedores();

        if(this.produtoRecebido != null){

            tfNomeProduto.setText(this.produtoRecebido.getNome());
            tfPrecoDeCustoProduto.setText(String.valueOf(this.produtoRecebido.getPrecoDeCusto()));

            //TODO: pré-selecionar Categoria e Fornecedor nas ListView

        }

    }

    private void selecionarCategoria(Categoria categoria) {

        this.categoria = categoria;

    }

    private void selecionarFornecedor(Fornecedor fornecedor) {

        this.fornecedor = fornecedor;

    }

    private void carregarListViewCategorias() {

        arrayListCategorias = categoriaService.listarTodasCategorias();
        observableListCategorias = FXCollections.observableList(arrayListCategorias);

        listViewCategoriasProduto.setItems(observableListCategorias);

    }

    private void carregarListViewFornecedores() {

        arrayListFornecedores = fornecedorService.listarTodosFornecedores();
        observableListFornecedores = FXCollections.observableList(arrayListFornecedores);

        listViewFornecedoresProduto.setItems(observableListFornecedores);

    }

    public void salvarProduto(ActionEvent actionEvent){

        String nome = tfNomeProduto.getText().trim();
        Double precoDeCusto = Double.valueOf(tfPrecoDeCustoProduto.getText());

        Produto produto = produtoService.criarProduto(nome, 0, precoDeCusto, this.categoria, this.fornecedor);

        if(cadastrarNovoProduto){

            produtoService.cadastrarProduto(produto);

        }else{

            produto.setIdProduto(this.produtoRecebido.getIdProduto());

            produtoService.editarProduto(produto);

        }

        voltar(actionEvent);

    }

    public void voltar(ActionEvent actionEvent){

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.close();

    }

}
