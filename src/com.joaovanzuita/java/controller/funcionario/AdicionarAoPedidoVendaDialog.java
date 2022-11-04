package controller.funcionario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.bean.ItemNFSaida;
import model.bean.Produto;
import model.service.NFSaidaService;
import util.Alert;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdicionarAoPedidoVendaDialog implements Initializable {

    private NFSaidaService nfSaidaService = NFSaidaService.getInstance();
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###.00");
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

        labelNomeProduto.setText(labelNomeProduto.getText() + produtoRecebido.getNome());
        labelCategoriaProduto.setText(labelCategoriaProduto.getText() + produtoRecebido.getCategoria().getNome());
        labelPrecoDeVendaProduto.setText(labelPrecoDeVendaProduto.getText() + "R$ " + decimalFormat.format(produtoRecebido.getPrecoDeVenda()));
        labelQuantidadeProduto.setText(labelQuantidadeProduto.getText() + produtoRecebido.getQuantidade());
    }

    public void adicionar(ActionEvent actionEvent) {

        if (!validarCampos()) {

            return;
        }

        int quantidade = Integer.parseInt(tfQuantidade.getText());

        itemNFSaida = nfSaidaService.criarItemNFSaida(produtoRecebido, quantidade);

        efetuarVendaController.adicionarItem(itemNFSaida);

        voltar(actionEvent);
    }

    private boolean validarCampos(){

        Pattern quantidade = Pattern.compile("[1-9][0-9]*");

        Matcher matcherQuantidade = quantidade.matcher(tfQuantidade.getText());

        if(!matcherQuantidade.matches()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Valor para quantidade inválido (deve ser um número inteiro positivo).");

            alert.showAndWait();

            return false;
        }

        if(Integer.parseInt(tfQuantidade.getText()) > this.produtoRecebido.getQuantidade()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Quantidade a ser comprada não pode ser maior que a quantidade disponível em estoque.");

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
