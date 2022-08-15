package controller.funcionario;

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
import model.bean.*;
import model.service.NFSaidaService;
import model.service.ProdutoService;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EfetuarVenda implements Initializable {

    private ProdutoService produtoService = ProdutoService.getInstance();
    private NFSaidaService nfSaidaService = NFSaidaService.getInstance();
    private ArrayList<ItemNFSaida> arrayListItensNFSaida = new ArrayList<>();
    private DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
    private Usuario funcionario;
    private Cliente cliente;
    private NFSaida nfSaida;
    private Produto produto;
    private ArrayList<Produto> arrayListProdutos;
    private ObservableList<Produto> observableListProdutos;

    @FXML
    private TextField tfPesquisarProduto;
    @FXML
    private Label labelNomeCliente;
    @FXML
    private Label labelNomeProdutoSelecionado;
    @FXML
    private Label labelCategoriaProdutoSelecionado;
    @FXML
    private Label labelPrecoDeVendaProdutoSelecionado;
    @FXML
    private Label labelQuantidadeProdutoSelecionado;
    @FXML
    private ListView<Produto> listViewProdutos;

    public EfetuarVenda(Usuario funcionario) {

        this.funcionario = funcionario;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        carregarListViewProdutos(null);

        listViewProdutos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarProduto(newValue));

        tfPesquisarProduto.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {

            carregarListViewProdutos(tfPesquisarProduto.getText());

        });

    }

    private void carregarListViewProdutos(String nomeProduto) {

        if (nomeProduto == null){

            arrayListProdutos = produtoService.listarTodosProdutos();

        }else{

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
            labelQuantidadeProdutoSelecionado.setText(String.valueOf(produto.getQuantidade()));

            return;
        }

        labelNomeProdutoSelecionado.setText("");
        labelCategoriaProdutoSelecionado.setText("");
        labelPrecoDeVendaProdutoSelecionado.setText("");
        labelQuantidadeProdutoSelecionado.setText("");

    }

    public void selecionarCliente(){

        try {

            URL urlSelecionarCliente = getClass().getResource("/view/telasFuncionario/SelecionarClienteDialog.fxml");

            FXMLLoader loader = new FXMLLoader(urlSelecionarCliente);

            loader.setController(new SelecionarClienteDialog(this));

            AnchorPane pane = loader.load();

            Scene scene = new Scene(pane);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Selecionar cliente");

            dialogStage.setScene(scene);

            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.showAndWait();

            if (labelNomeCliente.getText().length() > 0) {

                labelNomeCliente.setText(cliente.getNome());

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public void visualizarPedido(){

        if(!arrayListItensNFSaida.isEmpty()){

            try {

                URL url = getClass().getResource("/view/telasFuncionario/VisualizarPedidoVendaDialog.fxml");

                FXMLLoader loader = new FXMLLoader(url);

                loader.setController(new VisualizarPedidoVendaDialog(arrayListItensNFSaida, this, cliente));

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

                URL urlAdicionarAoPedido = getClass().getResource("/view/telasFuncionario/AdicionarAoPedidoVendaDialog.fxml");

                FXMLLoader loader = new FXMLLoader(urlAdicionarAoPedido);

                loader.setController(new AdicionarAoPedidoVendaDialog(this, produto));

                AnchorPane pane = loader.load();

                Scene scene = new Scene(pane);

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Adicionar ao Pedido");

                dialogStage.setScene(scene);

                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.showAndWait();

            } catch (IOException e) {

                e.printStackTrace();

            }

            return;

        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Selecione um registro na lista.");
        alert.showAndWait();

    }

    public void concluirPedido(ActionEvent actionEvent){

        if(!arrayListItensNFSaida.isEmpty()){

            nfSaida = nfSaidaService.criarNFSaida(funcionario, cliente, arrayListItensNFSaida);

            int idNFSaida = nfSaidaService.cadastrarNFSaida(nfSaida);

            //consulta no banco porque o valor total só é setado durante o cadastro
            nfSaida = nfSaidaService.consultarNFSaidaPorId(idNFSaida);

            try{

                URL url = getClass().getResource("/view/telasFuncionario/ExibirNFSaidaDialog.fxml");

                FXMLLoader loader = new FXMLLoader(url);

                loader.setController(new ExibirNFSaidaDialog(nfSaida));

                AnchorPane pane = loader.load();

                Scene scene = new Scene(pane);

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Nota fiscal de saída número " + nfSaida.getIdNFSaida());

                dialogStage.setScene(scene);

                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.showAndWait();

                voltar(actionEvent);

            }catch (IOException e){

                e.printStackTrace();

            }

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

            URL urlMenuFuncionario = getClass().getResource("/view/telasFuncionario/MenuFuncionario.fxml");
            FXMLLoader loader = new FXMLLoader(urlMenuFuncionario);

            loader.setController(new MenuFuncionario(funcionario));

            root = loader.load();

            Scene menuFuncionario = new Scene(root);
            stage.setScene(menuFuncionario);

            stage.show();

        }catch (IOException e){

            e.printStackTrace();

        }

    }

    public void adicionarItem(ItemNFSaida itemNFSaida){

        arrayListItensNFSaida.add(itemNFSaida);

    }

    public void removerItem(ItemNFSaida itemNFSaida){

        arrayListItensNFSaida.remove(itemNFSaida);

    }

    public void setCliente(Cliente cliente) {

        this.cliente = cliente;

    }
}
