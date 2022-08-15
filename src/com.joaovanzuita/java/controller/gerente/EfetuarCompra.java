package controller.gerente;

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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.bean.ItemNFEntrada;
import model.bean.NFEntrada;
import model.bean.Produto;
import model.bean.Usuario;
import model.service.NFEntradaService;
import model.service.ProdutoService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import util.ConnectionFactory;
import util.ReportFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class EfetuarCompra implements Initializable {

    private ProdutoService produtoService = ProdutoService.getInstance();
    private NFEntradaService nfEntradaService = NFEntradaService.getInstance();
    private ReportFactory reportFactory = ReportFactory.getInstance();
    private ArrayList<ItemNFEntrada> arraylistItensNFEntrada = new ArrayList<>();
    private DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
    private Usuario usuario;
    private Produto produto;
    private NFEntrada nfEntrada;
    private ArrayList<Produto> arrayListProdutos;
    private ObservableList<Produto> observableListProdutos;

    @FXML
    private TextField tfPesquisarProduto;
    @FXML
    private Label labelNomeProdutoSelecionado;
    @FXML
    private Label labelCategoriaProdutoSelecionado;
    @FXML
    private Label labelPrecoDeVendaProdutoSelecionado;
    @FXML
    private Label labelPrecoDeCustoProdutoSelecionado;
    @FXML
    private Label labelQuantidadeProdutoSelecionado;
    @FXML
    private ListView<Produto> listViewProdutos;

    public EfetuarCompra(Usuario usuario) {

        this.usuario = usuario;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        carregarListViewProdutos(null);

        listViewProdutos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarProduto(newValue));

        tfPesquisarProduto.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {

            carregarListViewProdutos(tfPesquisarProduto.getText());

        });

    }

    private void carregarListViewProdutos(String nomeProduto){

        if (nomeProduto == null){

            arrayListProdutos = produtoService.listarTodosProdutos();

        }else {

            arrayListProdutos = produtoService.consultarProdutoPorNome(nomeProduto);

        }

        observableListProdutos = FXCollections.observableList(arrayListProdutos);

        listViewProdutos.setItems(observableListProdutos);

    }

    private void selecionarProduto(Produto produto){

        if (produto != null) {

            this.produto = produto;

            labelNomeProdutoSelecionado.setText(produto.getNome());
            labelCategoriaProdutoSelecionado.setText(produto.getCategoria().getNome());
            labelPrecoDeVendaProdutoSelecionado.setText("R$ " + decimalFormat.format(produto.getPrecoDeVenda()));
            labelPrecoDeCustoProdutoSelecionado.setText("R$ " + decimalFormat.format(produto.getPrecoDeCusto()));
            labelQuantidadeProdutoSelecionado.setText(String.valueOf(produto.getQuantidade()));

            return;
        }

        labelNomeProdutoSelecionado.setText("");
        labelCategoriaProdutoSelecionado.setText("");
        labelPrecoDeVendaProdutoSelecionado.setText("");
        labelPrecoDeCustoProdutoSelecionado.setText("");
        labelQuantidadeProdutoSelecionado.setText("");

    }

    public void visualizarPedido(){

        if(!arraylistItensNFEntrada.isEmpty()){

            try {

                URL url = getClass().getResource("/view/telasGerente/VisualizarPedidoCompraDialog.fxml");

                FXMLLoader loader = new FXMLLoader(url);

                loader.setController(new VisualizarPedidoCompraDialog(arraylistItensNFEntrada, this));

                AnchorPane pane = loader.load();

                Scene scene = new Scene(pane);

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Visualizar Pedido");

                dialogStage.setScene(scene);

                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.showAndWait();

            } catch (IOException e) {

                e.printStackTrace();

            }

            return;

        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Não há nenhum item no pedido.");
        alert.showAndWait();

    }

    public void adicionarAoPedido(){

        if(produto != null){

            try {

                URL urlAdicionarAoPedido = getClass().getResource("/view/telasGerente/AdicionarAoPedidoCompraDialog.fxml");

                FXMLLoader loader = new FXMLLoader(urlAdicionarAoPedido);

                loader.setController(new AdicionarAoPedidoCompraDialog(this, produto));

                AnchorPane pane = loader.load();

                Scene scene = new Scene(pane);

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Adicionar ao Pedido");

                dialogStage.setScene(scene);

                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.showAndWait();

                carregarListViewProdutos(null);

            } catch (IOException e) {

                e.printStackTrace();

            }

            return;

        }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("por favor, selecione um registro na lista.");
            alert.showAndWait();

    }

    public void concluirPedido(ActionEvent actionEvent){

        if(!arraylistItensNFEntrada.isEmpty()){

            nfEntrada = nfEntradaService.criarNFEntrada(arraylistItensNFEntrada);

            int idNFEntrada = nfEntradaService.cadastrarNFEntrada(nfEntrada);

                JasperViewer viewer = reportFactory.criarNFEntrada(idNFEntrada);

                voltar(actionEvent);

                viewer.setVisible(true);

            return;

        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Não há nenhum item no pedido.");
        alert.showAndWait();

    }

    public void voltar(ActionEvent actionEvent) {

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root;

        try{

            URL urlMenuGerente = getClass().getResource("/view/telasGerente/MenuGerente.fxml");
            FXMLLoader loader = new FXMLLoader(urlMenuGerente);

            loader.setController(new MenuGerente(this.usuario));

            root = loader.load();

            Scene menuGerente = new Scene(root);
            stage.setScene(menuGerente);

            stage.show();

        }catch (IOException e){

            e.printStackTrace();

        }

    }

    public void adicionarItem(ItemNFEntrada itemNFEntrada){

        this.arraylistItensNFEntrada.add(itemNFEntrada);

    }

    public void removerItem(ItemNFEntrada itemNFEntrada){

        this.arraylistItensNFEntrada.remove(itemNFEntrada);

    }

}
