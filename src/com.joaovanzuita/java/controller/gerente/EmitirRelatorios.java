package controller.gerente;

import controller.funcionario.MenuFuncionario;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import util.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.bean.Usuario;
import net.sf.jasperreports.view.JasperViewer;
import util.AlertIOException;
import util.ReportFactory;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmitirRelatorios implements Initializable {

    private Usuario usuario;
    private ReportFactory reportFactory = ReportFactory.getInstance();

    public EmitirRelatorios(Usuario usuario) {

        this.usuario = usuario;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void relatorioVendasPorFuncionario(){

        try {

            URL url = getClass().getResource("/view/telasGerente/SelecionarDataRelatorioDialog.fxml");

            FXMLLoader loader = new FXMLLoader(url);

            loader.setController(new SelecionarDataRelatorioDialog(1));

            AnchorPane pane = loader.load();

            Scene scene = new Scene(pane);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Relatório de vendas por funcionário");

            dialogStage.setScene(scene);

            dialogStage.initModality(Modality.APPLICATION_MODAL);

            dialogStage.showAndWait();

        } catch (IOException e) {

            e.printStackTrace();
            Logger.getLogger(EmitirRelatorios.class.getName()).log(Level.WARNING, e.getMessage(), e);

            AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

    }

    public void relatorioMovimentacoesFinanceiras(){

        try {

            URL url = getClass().getResource("/view/telasGerente/SelecionarDataRelatorioDialog.fxml");

            FXMLLoader loader = new FXMLLoader(url);

            loader.setController(new SelecionarDataRelatorioDialog(0));

            AnchorPane pane = loader.load();

            Scene scene = new Scene(pane);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Relatório de movimentações financeiras");

            dialogStage.setScene(scene);

            dialogStage.initModality(Modality.APPLICATION_MODAL);

            dialogStage.showAndWait();

        } catch (IOException e) {

            e.printStackTrace();
            Logger.getLogger(EmitirRelatorios.class.getName()).log(Level.WARNING, e.getMessage(), e);

            AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();

        }

    }

    public void relatorioEstoque(){

        JasperViewer viewer = reportFactory.criarRelatorioEstoque();

        viewer.setVisible(true);

    }

    public void relatorioProdutosMaisVendidos(){

        try {

            URL url = getClass().getResource("/view/telasGerente/SelecionarDataRelatorioDialog.fxml");

            FXMLLoader loader = new FXMLLoader(url);

            loader.setController(new SelecionarDataRelatorioDialog(2));

            AnchorPane pane = loader.load();

            Scene scene = new Scene(pane);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Relatório de produtos mais vendidos");

            dialogStage.setScene(scene);

            dialogStage.initModality(Modality.APPLICATION_MODAL);

            dialogStage.showAndWait();

        } catch (IOException e) {

            e.printStackTrace();
            Logger.getLogger(EmitirRelatorios.class.getName()).log(Level.WARNING, e.getMessage(), e);

            AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();

        }

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
            Logger.getLogger(EmitirRelatorios.class.getName()).log(Level.WARNING, e.getMessage(), e);

            AlertIOException alert = new AlertIOException(Alert.AlertType.ERROR, e);
            alert.showAndWait();

        }

    }
}