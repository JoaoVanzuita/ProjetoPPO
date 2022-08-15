package controller.gerente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.bean.ItemNFEntrada;
import model.bean.Produto;
import model.service.NFEntradaService;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class AdicionarAoPedidoCompraDialog implements Initializable {

    private NFEntradaService nfEntradaService = NFEntradaService.getInstance();
    private DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
    private EfetuarCompra efetuarCompraController;
    private ItemNFEntrada itemNFEntrada;
    private Produto produtoRecebido;

    @FXML
    private TextField tfValorUnitario;
    @FXML
    private TextField tfQuantidade;
    @FXML
    private Label labelNomeProduto;
    @FXML
    private Label labelCategoriaProduto;
    @FXML
    private Label labelPrecoDeVendaProduto;
    @FXML
    private Label labelPrecoDeCustoProduto;
    @FXML
    private Label labelQuantidadeProduto;

    public AdicionarAoPedidoCompraDialog(EfetuarCompra efetuarCompraController, Produto produto) {

        this.efetuarCompraController = efetuarCompraController;
        this.produtoRecebido = produto;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        carregarInfoProduto();

    }

    private void carregarInfoProduto(){

        labelNomeProduto.setText(produtoRecebido.getNome());
        labelCategoriaProduto.setText(produtoRecebido.getCategoria().getNome());
        labelPrecoDeVendaProduto.setText("R$ " + decimalFormat.format(produtoRecebido.getPrecoDeVenda()));
        labelPrecoDeCustoProduto.setText("R$ " + decimalFormat.format(produtoRecebido.getPrecoDeCusto()));
        labelQuantidadeProduto.setText(String.valueOf(produtoRecebido.getQuantidade()));

    }

    public void adicionar(ActionEvent actionEvent){

        if (validarCampos()){

            Double valorUnitario = Double.parseDouble(tfValorUnitario.getText());
            int quantidade =  Integer.parseInt(tfQuantidade.getText());

            itemNFEntrada = nfEntradaService.criarItemNFEntrada(produtoRecebido, valorUnitario, quantidade);

            efetuarCompraController.adicionarItem(itemNFEntrada);

            voltar(actionEvent);

            return;

        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Valores inválidos inseridos.");
        alert.showAndWait();

    }

    private boolean validarCampos(){

        //TODO: validação com regex

        if(tfValorUnitario.getText().length() == 0 || Integer.parseInt(tfValorUnitario.getText()) <=0)
            return false;

        if(Integer.parseInt(tfQuantidade.getText()) <=0)
            return false;

        return true;
    }

    public void voltar(ActionEvent actionEvent) {

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.close();

    }

}
