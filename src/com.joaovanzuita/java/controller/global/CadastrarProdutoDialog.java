package controller.global;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CadastrarProdutoDialog implements Initializable {

    private ProdutoService produtoService = ProdutoService.getInstance();
    private CategoriaService categoriaService = CategoriaService.getInstance();
    private FornecedorService fornecedorService = FornecedorService.getInstance();
    private DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
    private Produto produtoRecebido;
    private Categoria categoria;
    private Fornecedor fornecedor;
    private boolean cadastrarNovoProduto;
    private ArrayList<Categoria> arrayListCategorias;
    private ArrayList<Fornecedor> arrayListFornecedores;
    private ObservableList<Categoria> observableListCategorias;
    private ObservableList<Fornecedor> observableListFornecedores;

    @FXML
    private TextField tfNomeProduto;
    @FXML
    private TextField tfPrecoDeCustoProduto;
    @FXML
    private ListView<Categoria> listViewCategoriasProduto;
    @FXML
    private ListView<Fornecedor> listViewFornecedoresProduto;


    public CadastrarProdutoDialog(Produto produto, boolean cadastrarNovoProduto) {

        this.produtoRecebido = produto;
        this.cadastrarNovoProduto = cadastrarNovoProduto;


        System.out.println(produto);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listViewCategoriasProduto.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarCategoria(newValue));

        listViewFornecedoresProduto.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarFornecedor(newValue));

        carregarListViewCategorias();
        carregarListViewFornecedores();

        if(this.produtoRecebido != null){

            tfNomeProduto.setText(this.produtoRecebido.getNome());
            tfPrecoDeCustoProduto.setText(decimalFormat.format(produtoRecebido.getPrecoDeCusto()));

            this.categoria = produtoRecebido.getCategoria();
            this.fornecedor = produtoRecebido.getFornecedor();

            //TODO: pré-selecionar Categoria e Fornecedor nas ListView

        }

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

    private void selecionarCategoria(Categoria categoria) {

        this.categoria = categoria;

    }

    private void selecionarFornecedor(Fornecedor fornecedor) {

        this.fornecedor = fornecedor;

    }

    public void salvarProduto(ActionEvent actionEvent){

        if(validarCampos()) {

            String nome = tfNomeProduto.getText().trim();

            Double precoDeCusto = Double.valueOf(tfPrecoDeCustoProduto.getText().trim());

            Produto produto = produtoService.criarProduto(nome, 0, precoDeCusto, this.categoria, this.fornecedor);

            if (cadastrarNovoProduto) {

                produtoService.cadastrarProduto(produto);

            } else {

                produto.setIdProduto(this.produtoRecebido.getIdProduto());

                produtoService.editarProduto(produto);

            }

            voltar(actionEvent);

        }else{

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Insira valores válidos nos campos.");
            alert.showAndWait();

        }

    }

    private boolean validarCampos(){

        //TODO: validação com regex

        if(this.tfNomeProduto.getText().length() < 4)
            return false;

        if (this.tfPrecoDeCustoProduto.getText().length() < 2)
            return false;

        return true;
    }

    public void voltar(ActionEvent actionEvent){

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.close();

    }

}
