package controller.global;

import controller.gerente.CadastrarFornecedorDialog;
import controller.gerente.CadastrarFuncionarioDialog;
import controller.funcionario.CadastrarClienteDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.bean.Endereco;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CriarEnderecoDialog implements Initializable {

    private Endereco endereco;
    private CadastrarClienteDialog cadastrarClienteDialog;
    private CadastrarFornecedorDialog cadastrarFornecedorDialog;
    private CadastrarFuncionarioDialog cadastrarFuncionarioDialog;
    private GerenciarConta gerenciarConta;
    //0 = Cliente, 1 = Fornecedor, 2 = Funcionário, 3 = GerenciarConta
    private final int controllerRecebido;

    @FXML
    private TextField tfCidade;
    @FXML
    private TextField tfLogradouro;
    @FXML
    private ComboBox<String> cbEstado;
    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfComplemento;
    @FXML
    private TextField tfCep;

    public CriarEnderecoDialog(Endereco endereco, CadastrarClienteDialog cadastrarClienteDialog) {

        this.endereco = endereco;
        this.cadastrarClienteDialog = cadastrarClienteDialog;
        this.controllerRecebido = 0;

    }

    public CriarEnderecoDialog(Endereco endereco, CadastrarFornecedorDialog cadastrarFornecedorDialog) {

        this.endereco = endereco;
        this.cadastrarFornecedorDialog = cadastrarFornecedorDialog;
        this.controllerRecebido = 1;

    }

    public CriarEnderecoDialog(Endereco endereco, CadastrarFuncionarioDialog cadastrarFuncionarioDialog) {

        this.endereco = endereco;
        this.cadastrarFuncionarioDialog = cadastrarFuncionarioDialog;
        this.controllerRecebido = 2;

    }

    public CriarEnderecoDialog(Endereco endereco, GerenciarConta gerenciarConta) {

        this.gerenciarConta = gerenciarConta;
        this.endereco = endereco;
        this.controllerRecebido = 3;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cbEstado.getItems().addAll(
                "AC",
                "AL",
                "AP",
                "AM",
                "BA",
                "CE",
                "DF",
                "ES",
                "GO",
                "MA",
                "MT",
                "MS",
                "MG",
                "PA",
                "PB",
                "PR",
                "PE",
                "PI",
                "RJ",
                "RN",
                "RS",
                "RO",
                "RR",
                "SC",
                "SP",
                "SE",
                "TO"
        );

        //Verifica se recebeu um endereço para editar
        if (endereco != null){

            tfCidade.setText(this.endereco.getCidade());
            tfLogradouro.setText(this.endereco.getLogradouro());
            tfComplemento.setText(this.endereco.getComplemento());
            cbEstado.setValue(this.endereco.getUf());
            tfNumero.setText(String.valueOf(this.endereco.getNumero()));
            tfCep.setText(this.endereco.getCep());

        }

    }

    public void criarEndereco(ActionEvent actionEvent) {

        if (!validarCampos()) {

            return;

        }

        String cidade = tfCidade.getText().trim();
        String logradouro = tfLogradouro.getText().trim();
        String estado = cbEstado.getValue();
        String complemento = tfComplemento.getText().trim();
        String cep = tfCep.getText().trim();
        int numero = Integer.parseInt(tfNumero.getText());

        endereco = new Endereco(estado, cidade, logradouro, complemento, cep, numero);

        //Verifica qual o controller que deve receber o Endereço criado/editado
        if (controllerRecebido == 0) {

            cadastrarClienteDialog.setEndereco(endereco);
        }
        if(controllerRecebido == 1){

            cadastrarFornecedorDialog.setEndereco(endereco);
        }

       if(controllerRecebido == 2){

            cadastrarFuncionarioDialog.setEndereco(endereco);

        }
       if(controllerRecebido == 3){

           gerenciarConta.setEndereco(endereco);

       }

        voltar(actionEvent);
    }

    private boolean validarCampos() {

        Pattern nome = Pattern.compile("^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ'\\s]+$");
        Pattern alfaNumericos = Pattern.compile("^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ0-9'\\s]+$");
        Pattern numero = Pattern.compile("[1-9][0-9]*");
        Pattern cep = Pattern.compile("^[0-9]+$");

        Matcher matcherNome = nome.matcher(tfCidade.getText().trim());
        Matcher matcherLogradouro = alfaNumericos.matcher(tfLogradouro.getText().trim());
        Matcher matcherComplemento = alfaNumericos.matcher(tfComplemento.getText().trim());
        Matcher matcherNumero = numero.matcher(tfNumero.getText().trim());
        Matcher matcherCep = cep.matcher(tfCep.getText().trim());


        if(!matcherNome.matches()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Nome da cidade deve ser composto apenas por letras.");
            alert.showAndWait();

            return false;
        }

        if(!matcherLogradouro.matches()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Logradouro deve ter somente caracteres alfanuméricos (de A a Z e 0 a 9).");
            alert.showAndWait();

            return false;
        }

        if(!matcherComplemento.matches()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Complemento deve ter somente caracteres alfanuméricos (de A a Z e 0 a 9).");
            alert.showAndWait();

            return false;
        }

        if(!matcherNumero.matches()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Número deve ser composto somente por caracteres numéricos.");
            alert.showAndWait();

            return false;
        }

        if(!matcherCep.matches()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("CEP deve ser composto somente por caracteres numéricos.");
            alert.showAndWait();

            return false;
        }

        if(tfCidade.getText().trim().length() < 4){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Nome da cidade deve ter ao menos 4 caracteres.");
            alert.showAndWait();

            return false;
        }

        if(tfLogradouro.getText().trim().length() < 5){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Logradouro deve ter ao menos 5 caracteres.");
            alert.showAndWait();

            return false;
        }

        if(tfComplemento.getText().trim().length() < 4){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Complemento deve ter ao menos 5 caracteres.");
            alert.showAndWait();

            return false;
        }

        if(tfNumero.getText().trim().length() < 1){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("Número não pode estar vazio.");
            alert.showAndWait();

            return false;
        }

        if(tfCep.getText().trim().length() != 8){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada inválida.");
            alert.setContentText("CEP deve ter 8 caracteres.");
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
