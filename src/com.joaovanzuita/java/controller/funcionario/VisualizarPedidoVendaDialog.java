package controller.funcionario;

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
import model.bean.Cliente;
import model.bean.ItemNFSaida;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VisualizarPedidoVendaDialog implements Initializable {

    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###.00");
    private EfetuarVenda efetuarVendaController;
    private ItemNFSaida itemSelecionado;
    private Cliente cliente;
    private ArrayList<ItemNFSaida> arrayListItensNFSaida;
    private ObservableList<ItemNFSaida> observableListItensNFSaida;

    @FXML
    private ListView<ItemNFSaida> listViewItensNFSaida;
    @FXML
    private Label labelNomeCliente;
    @FXML
    private Label labelValorTotal;

    public VisualizarPedidoVendaDialog(ArrayList<ItemNFSaida> arrayListItensNFEntrada, EfetuarVenda efetuarVendaController, Cliente cliente) {

        this.arrayListItensNFSaida = arrayListItensNFEntrada;
        this.efetuarVendaController = efetuarVendaController;
        this.cliente = cliente;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        labelNomeCliente.setText(labelNomeCliente.getText() + cliente.getNome());

        carregarItensNFSaida();

        listViewItensNFSaida.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarItem(newValue));

        atualizarValorTotal();

    }

    private void carregarItensNFSaida() {

        observableListItensNFSaida = FXCollections.observableList(this.arrayListItensNFSaida);

        listViewItensNFSaida.setItems(observableListItensNFSaida);

    }

    private void selecionarItem(ItemNFSaida item) {

        this.itemSelecionado = item;

    }


    public void excluirItem(ActionEvent actionEvent) {

        if (itemSelecionado != null) {

            arrayListItensNFSaida.remove(itemSelecionado);

            efetuarVendaController.removerItem(itemSelecionado);

            carregarItensNFSaida();

            atualizarValorTotal();

            return;
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Nenhum item selecionado.");
        alert.setContentText("Por favor, selecione um registro na lista.");
        alert.showAndWait();

    }

    private void atualizarValorTotal() {

        Double valorTotal = 0.00;

        for (ItemNFSaida itemNFSaida : arrayListItensNFSaida) {

            valorTotal += itemNFSaida.getValorUnitario() * itemNFSaida.getQuantidade();

        }

        this.labelValorTotal.setText("Valor total: R$ " + decimalFormat.format(valorTotal));

    }

    public void voltar(ActionEvent actionEvent) {

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.close();

    }
}
