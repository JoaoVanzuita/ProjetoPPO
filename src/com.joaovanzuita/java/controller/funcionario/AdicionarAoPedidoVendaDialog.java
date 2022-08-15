package controller.funcionario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.bean.ItemNFSaida;
import model.bean.Produto;
import model.service.NFSaidaService;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class AdicionarAoPedidoVendaDialog implements Initializable {

    private NFSaidaService nfSaidaService = NFSaidaService.getInstance();
    private DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
    private EfetuarVenda efetuarVendaController;
    private ItemNFSaida itemNFSaida;
    private Produto produtoRecebido;

    @FXML
    private TextField tfQuantidade;
    @FXML
    private Label labelNomeProduto;
    @FXML
    private Label labelCategoriaProduto;
    @FXML
    private Label labelPrecoDeVendaProduto;
    @FXML
    private Label labelQuantidadeProduto;

    public AdicionarAoPedidoVendaDialog(EfetuarVenda efetuarVendaController, Produto produtoRecebido) {

        this.efetuarVendaController = efetuarVendaController;
        this.produtoRecebido = produtoRecebido;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        carregarInfoProduto();

    }

    private void carregarInfoProduto() {

        labelNomeProduto.setText(produtoRecebido.getNome());
        labelCategoriaProduto.setText(produtoRecebido.getCategoria().getNome());
        labelPrecoDeVendaProduto.setText("R$ " + decimalFormat.format(produtoRecebido.getPrecoDeVenda()));
        labelQuantidadeProduto.setText(String.valueOf(produtoRecebido.getQuantidade()));

    }

    public void adicionar(ActionEvent actionEvent) {

        if (validarCampos()) {

            int quantidade = Integer.parseInt(tfQuantidade.getText());

            itemNFSaida = nfSaidaService.criarItemNFSaida(produtoRecebido, quantidade);

            efetuarVendaController.adicionarItem(itemNFSaida);

            voltar(actionEvent);

            return;

        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Valor inválido inserido.");
        alert.showAndWait();

    }

    private boolean validarCampos() {

        //TODO: validação com regex

        return Integer.parseInt(tfQuantidade.getText()) > 0 && Integer.parseInt(tfQuantidade.getText()) <= produtoRecebido.getQuantidade();
    }

    public void voltar(ActionEvent actionEvent) {

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.close();

    }

}
