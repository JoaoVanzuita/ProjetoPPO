package controller.global;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import util.Alert;
import javafx.scene.control.Label;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadastrarProdutoDialog implements Initializable {

    private ProdutoService produtoService = ProdutoService.getInstance();
    private CategoriaService categoriaService = CategoriaService.getInstance();
    private FornecedorService fornecedorService = FornecedorService.getInstance();
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###.00");
    private Produto produtoRecebido;
    private Categoria categoria;
    private Fornecedor fornecedor;
    private boolean cadastrarNovoProduto;
    private ArrayList<Categoria> arrayListCategorias;
    private ArrayList<Fornecedor> arrayListFornecedores;
    private ObservableList<Categoria> observableListCategorias;
    private ObservableList<Fornecedor> observableListFornecedores;

    @FXML
    private Label labelCategoria;

    @FXML
    private Label labelFornecedor;

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

            listViewCategoriasProduto.getSelectionModel().select(this.categoria);
            listViewFornecedoresProduto.getSelectionModel().select(this.fornecedor);

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
        labelCategoria.setText("Categoria: "  + categoria.getNome());

    }

    private void selecionarFornecedor(Fornecedor fornecedor) {

        this.fornecedor = fornecedor;
        labelFornecedor.setText("Fornecedor: " + fornecedor.getNome());

    }

    public void salvarProduto(ActionEvent actionEvent){

        if(!validarCampos()) {

            return;
        }

        String nome = tfNomeProduto.getText().trim();

        Double precoDeCusto = Double.valueOf(tfPrecoDeCustoProduto.getText().replace(".", "").replace(",",".").trim());

        Produto produto = produtoService.criarProduto(nome, 0, precoDeCusto, this.categoria, this.fornecedor);

        if (cadastrarNovoProduto) {

            produtoService.cadastrarProduto(produto);

        } else {

            produto.setIdProduto(this.produtoRecebido.getIdProduto());

            produtoService.editarProduto(produto);

        }

        voltar(actionEvent);
    }

    private boolean validarCampos(){

        Pattern nome = Pattern.compile("^^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ'\\s]*");
        Pattern precoDeCusto = Pattern.compile("\\d{1,3}(\\.\\d{3})*,\\d{2}");

        Matcher matcherNome = nome.matcher(tfNomeProduto.getText().trim());
        Matcher matcherPrecoDeCusto = precoDeCusto.matcher(tfPrecoDeCustoProduto.getText().trim());

        if(!matcherNome.matches()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Nome deve ser composto apenas por letras.");
            alert.showAndWait();

            return false;
        }

        if(!matcherPrecoDeCusto.matches()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Valor para preço de custo inválido (padrão #.###,##).");
            alert.showAndWait();

            return false;
        }

        if(tfNomeProduto.getText().trim().length() < 4){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Nome deve ter ao menos 4 caracteres.");
            alert.showAndWait();

            return false;
        }

        if(this.categoria == null){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Nenhum item selecionado.");
            alert.setContentText("Selecione uma categoria para o produto.");
            alert.showAndWait();

            return  false;
        }

        if(this.fornecedor == null){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Nenhum item selecionado.");
            alert.setContentText("Selecione um fornecedor para o produto.");
            alert.showAndWait();

            return  false;
        }

        return true;
    }

    public void voltar(ActionEvent actionEvent){

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.close();
    }
}
