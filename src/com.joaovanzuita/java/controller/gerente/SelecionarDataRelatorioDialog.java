package controller.gerente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import util.Alert;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import net.sf.jasperreports.view.JasperViewer;
import util.ReportFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SelecionarDataRelatorioDialog implements Initializable {

    @FXML
    private DatePicker dpDataInicial;

    @FXML
    private DatePicker dpDataFinal;

    private ReportFactory reportFactory = ReportFactory.getInstance();

    //0 = Movimentações financeiras, 1 = Vendas por funcionário, 2 = Produtos mais vendidos
    private int tipoRelatorio;

    public SelecionarDataRelatorioDialog(int tipoRelatorio) {

        this.tipoRelatorio = tipoRelatorio;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void gerarRelatorio(ActionEvent actionEvent){

        if(!validarCampos()){

            return;

        }

        LocalDate dataInicial = dpDataInicial.getValue();
        LocalDate dataFinal = dpDataFinal.getValue();

        JasperViewer viewer = null;

        if(tipoRelatorio == 0){

            viewer = reportFactory.criarRelatorioMovimentacoesFinanceiras(dataInicial, dataFinal);

        }else if(tipoRelatorio == 1){

            viewer = reportFactory.criarRelatorioVendasPorFuncionario(dataInicial, dataFinal);

        }else if(tipoRelatorio == 2){

            viewer = reportFactory.criarRelatorioProdutosMaisVendidos(dataInicial, dataFinal);

        }

        voltar(actionEvent);

        viewer.setVisible(true);

    }

    public boolean validarCampos(){

        if(dpDataInicial.getValue() == null){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Por favor, selecione uma data inicial.");
            alert.showAndWait();

            return false;
        }

        if(dpDataFinal.getValue() == null){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Por favor, selecione uma data final.");
            alert.showAndWait();

            return false;
        }

        if(dpDataFinal.getValue().isBefore(dpDataInicial.getValue())){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Data final deve ser posterior à data inicial.");
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
