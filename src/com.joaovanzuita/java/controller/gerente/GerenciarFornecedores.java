package controller.gerente;

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
import model.bean.Fornecedor;
import model.bean.Usuario;
import model.service.FornecedorService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GerenciarFornecedores implements Initializable {

    private FornecedorService fornecedorService = FornecedorService.getInstance();
    private Fornecedor fornecedor;
    private Usuario usuario;
    private ArrayList<Fornecedor> arrayListFornecedores;
    private ObservableList<Fornecedor> observableListFornecedores;

    @FXML
    private TextField tfPesquisarFornecedor;
    @FXML
    private Label labelNomeFornecedorSelecionado;
    @FXML
    private Label labelEmailFornecedorSelecionado;
    @FXML
    private Label labelCidadeFornecedorSelecionado;
    @FXML
    private Label labelUfFornecedorSelecionado;
    @FXML
    private Label labelCepFornecedorSelecionado;
    @FXML
    private ListView<Fornecedor> listViewFornecedores;

    public GerenciarFornecedores(Usuario usuario) {

        this.usuario = usuario;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        carregarListViewFornecedores(null);

        listViewFornecedores.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarFornecedor(newValue));

        tfPesquisarFornecedor.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent ->{

            carregarListViewFornecedores(tfPesquisarFornecedor.getText());

        });

    }

    private void carregarListViewFornecedores(String nomeFornecedor) {

        if (nomeFornecedor == null){

            arrayListFornecedores = fornecedorService.listarTodosFornecedores();

        }else{

            arrayListFornecedores = fornecedorService.consultarFornecedorPorNome(nomeFornecedor);

        }


        observableListFornecedores = FXCollections.observableList(arrayListFornecedores);

        listViewFornecedores.setItems(observableListFornecedores);

    }

    private void selecionarFornecedor(Fornecedor fornecedor) {

        if(fornecedor != null) {

            this.fornecedor = fornecedor;

            labelNomeFornecedorSelecionado.setText(fornecedor.getNome());
            labelEmailFornecedorSelecionado.setText(fornecedor.getEmail());
            labelCidadeFornecedorSelecionado.setText(fornecedor.getEndereco().getCidade());
            labelUfFornecedorSelecionado.setText(fornecedor.getEndereco().getUf());
            labelCepFornecedorSelecionado.setText(fornecedor.getEndereco().getCep());

            return;

        }

        labelNomeFornecedorSelecionado.setText("");
        labelEmailFornecedorSelecionado.setText("");
        labelCidadeFornecedorSelecionado.setText("");
        labelUfFornecedorSelecionado.setText("");
        labelCepFornecedorSelecionado.setText("");

    }

    public void cadastrarFornecedor(ActionEvent actionEvent) {

        try {

            URL url = getClass().getResource("/view/telasGerente/CadastrarFornecedorDialog.fxml");

            FXMLLoader loader = new FXMLLoader(url);

            loader.setController(new CadastrarFornecedorDialog(null, true));

            AnchorPane pane = loader.load();

            Scene scene = new Scene(pane);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastrar Fornecedor");

            dialogStage.setScene(scene);

            dialogStage.initModality(Modality.APPLICATION_MODAL);

            dialogStage.showAndWait();

            carregarListViewFornecedores(null);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public void editarFornecedor(ActionEvent actionEvent) {

        if (this.fornecedor != null) {

            try {

                URL url = getClass().getResource("/view/telasGerente/CadastrarFornecedorDialog.fxml");

                FXMLLoader loader = new FXMLLoader(url);

                loader.setController(new CadastrarFornecedorDialog(this.fornecedor, false));

                AnchorPane pane = loader.load();

                Scene scene = new Scene(pane);

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Editar Fornecedor");

                dialogStage.setScene(scene);

                dialogStage.initModality(Modality.APPLICATION_MODAL);

                dialogStage.showAndWait();

                carregarListViewFornecedores(null);

            } catch (IOException e) {

                e.printStackTrace();

            }

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um registro na lista.");
            alert.show();

        }

    }

    public void excluirFornecedor(ActionEvent actionEvent) {

        if(this.fornecedor != null){

            fornecedorService.deletarFornecedor(this.fornecedor);

            carregarListViewFornecedores(null);

            return;

        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Por favor, selecione um registro na lista.");
        alert.showAndWait();

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

        }

    }
}
