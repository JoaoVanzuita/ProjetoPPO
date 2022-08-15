package controller.funcionario;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.bean.Cliente;
import model.bean.ItemNFSaida;
import model.bean.NFSaida;
import model.bean.Usuario;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ExibirNFSaidaDialog implements Initializable {

    private ArrayList<ItemNFSaida> arrayListItensNFSaida;
    private ObservableList<ItemNFSaida> observableListItensNFSaida;
    private Usuario funcionario;
    private Cliente cliente;
    private NFSaida nfSaida;
    private DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

    @FXML
    private Label tituloNFEntrada;

    @FXML
    private Label labelNomeVendedor;

    @FXML
    private Label labelNomeCliente;

    @FXML
    private Label labelDataNFSaida;

    @FXML
    private Label labelValorTotalNFSaida;

    @FXML
    private ListView<ItemNFSaida> listViewItensNFSaida;

    public ExibirNFSaidaDialog(NFSaida nfSaida) {

        this.nfSaida = nfSaida;
        this.arrayListItensNFSaida = nfSaida.getItensNFSaida();
        this.funcionario = nfSaida.getUsuario();
        this.cliente = nfSaida.getCliente();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        observableListItensNFSaida = FXCollections.observableList(arrayListItensNFSaida);

        listViewItensNFSaida.setItems(observableListItensNFSaida);
        listViewItensNFSaida.setEditable(false);

        tituloNFEntrada.setText(tituloNFEntrada.getText() + nfSaida.getIdNFSaida());
        labelNomeVendedor.setText(nfSaida.getUsuario().getNome());
        labelNomeCliente.setText(nfSaida.getCliente().getNome());
        labelDataNFSaida.setText(String.valueOf(nfSaida.getData()));
        labelValorTotalNFSaida.setText("R$ " + decimalFormat.format(nfSaida.getValorTotal()));

    }

    public void gerarPdf(ActionEvent actionEvent){

        //TODO: implementar relatório em PDF

        System.out.println("A implementar...");

    }

    public void voltar(ActionEvent actionEvent){

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.close();

    }


}
