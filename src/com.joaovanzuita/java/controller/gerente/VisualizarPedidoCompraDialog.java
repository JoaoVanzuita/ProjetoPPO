package controller.gerente;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import util.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.bean.ItemNFEntrada;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VisualizarPedidoCompraDialog implements Initializable {

    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###.00");
    private ArrayList<ItemNFEntrada> arrayListItensNFEntrada;
    private EfetuarCompra efetuarCompraController;
    private ItemNFEntrada itemSelecionado;
    private ObservableList<ItemNFEntrada> observableListItensNFEntrada;


    @FXML
    private ListView<ItemNFEntrada> listViewItensNFEntrada;
    @FXML
    private Label labelValorTotal;

    public VisualizarPedidoCompraDialog(ArrayList<ItemNFEntrada> arrayListItensNFEntrada, EfetuarCompra efetuarCompraController) {

        this.arrayListItensNFEntrada = arrayListItensNFEntrada;
        this.efetuarCompraController = efetuarCompraController;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        carregarItensNFEntrada();

        listViewItensNFEntrada.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarItem(newValue));

        atualizarValorTotal();

    }

    private void carregarItensNFEntrada(){

        observableListItensNFEntrada = FXCollections.observableList(this.arrayListItensNFEntrada);

        listViewItensNFEntrada.setItems(observableListItensNFEntrada);

    }

    private void selecionarItem(ItemNFEntrada item) {

        this.itemSelecionado = item;

    }


    public void excluirItem(ActionEvent actionEvent){

        if (itemSelecionado != null) {

            arrayListItensNFEntrada.remove(itemSelecionado);

            efetuarCompraController.removerItem(itemSelecionado);

            carregarItensNFEntrada();

            atualizarValorTotal();

            return;
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Nenhum item selecionado.");
        alert.setContentText("Por favor, selecione um registro na lista.");
        alert.showAndWait();

    }

    private void atualizarValorTotal(){

        Double valorTotal = 0.0;

        for (ItemNFEntrada itemNFEntrada : arrayListItensNFEntrada) {

            valorTotal += itemNFEntrada.getValorUnitario() * itemNFEntrada.getQuantidade();

        }

        this.labelValorTotal.setText("Valor total: R$ " + decimalFormat.format(valorTotal));

    }

    public void voltar(ActionEvent actionEvent){

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.close();

    }
}
