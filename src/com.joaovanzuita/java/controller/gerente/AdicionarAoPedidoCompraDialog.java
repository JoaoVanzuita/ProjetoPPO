package controller.gerente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import util.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.bean.ItemNFEntrada;
import model.bean.Produto;
import model.service.NFEntradaService;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdicionarAoPedidoCompraDialog implements Initializable {

    private NFEntradaService nfEntradaService = NFEntradaService.getInstance();
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###.00");
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

        labelNomeProduto.setText(labelNomeProduto.getText() + produtoRecebido.getNome());
        labelCategoriaProduto.setText(labelCategoriaProduto.getText() + produtoRecebido.getCategoria().getNome());
        labelPrecoDeVendaProduto.setText(labelPrecoDeVendaProduto.getText() + "R$ " + decimalFormat.format(produtoRecebido.getPrecoDeVenda()));
        labelPrecoDeCustoProduto.setText(labelPrecoDeCustoProduto.getText() + "R$ " + decimalFormat.format(produtoRecebido.getPrecoDeCusto()));
        labelQuantidadeProduto.setText(labelQuantidadeProduto.getText() + produtoRecebido.getQuantidade());

    }

    public void adicionar(ActionEvent actionEvent){

        if (!validarCampos()) {

            return;
        }

        Double valorUnitario = Double.parseDouble(tfValorUnitario.getText().replace(".", "s").replace(",", "."));
        int quantidade =  Integer.parseInt(tfQuantidade.getText());

        itemNFEntrada = nfEntradaService.criarItemNFEntrada(produtoRecebido, valorUnitario, quantidade);

        efetuarCompraController.adicionarItem(itemNFEntrada);

        voltar(actionEvent);
    }

    private boolean validarCampos(){

        Pattern valorMonetario = Pattern.compile("\\d{1,3}(\\.\\d{3})*,\\d{2}");
        Pattern quantidade = Pattern.compile("[1-9][0-9]*");

        Matcher matcherMonetario = valorMonetario.matcher(tfValorUnitario.getText());
        Matcher matcherQuantidade = quantidade.matcher(tfQuantidade.getText());

        if(!matcherMonetario.matches()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Valor para preço de custo inválido (padrão #.###,##).");
            alert.showAndWait();

            return false;
        }

        if(!matcherQuantidade.matches()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Valor para quantidade inválido (deve ser um número inteiro positivo).");
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
