package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.bean.Endereco;

import java.net.URL;
import java.util.ResourceBundle;

public class CriarEnderecoDialog implements Initializable {

    private Endereco endereco;
    private CadastrarClienteDialog cadastrarClienteDialog;
    private CadastrarFornecedorDialog cadastrarFornecedorDialog;
    private CadastrarFuncionarioDialog cadastrarFuncionarioDialog;

    //0 = Cliente, 1 = Fornecedor, 2 = Funcionário
    private int controllerRecebido;

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

    @FXML
    private TextField tfCidade;
    @FXML
    private TextField tfLogradouro;
    @FXML
    private TextField tfEstado;
    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfComplemento;
    @FXML
    private TextField tfCep;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Verifica se recebeu um endereço para editar

        if (endereco != null){

            tfCidade.setText(this.endereco.getCidade());
            tfLogradouro.setText(this.endereco.getLogradouro());
            tfComplemento.setText(this.endereco.getComplemento());
            tfEstado.setText(this.endereco.getUf());
            tfNumero.setText(String.valueOf(this.endereco.getNumero()));
            tfCep.setText(this.endereco.getCep());

        }

    }

    public void criarEndereco(ActionEvent actionEvent) {

        if (validarCampos()) {

            String cidade = tfCidade.getText().trim();

            String logradouro = tfLogradouro.getText().trim();

            String estado = tfEstado.getText().trim();

            String complemento = tfComplemento.getText().trim();

            int intCep;
            int numero;
            String cep;

            try {

                intCep = Integer.parseInt(tfCep.getText().trim());

                cep = Integer.toString(intCep);

                numero = Integer.parseInt(tfNumero.getText());

            } catch (NumberFormatException e) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Insira valores numéricos para Número e Cep.");
                alert.showAndWait();

                e.printStackTrace();

                return;
            }

            this.endereco = new Endereco(estado, cidade, logradouro, complemento, cep, numero);

            //Verifica qual o controller que deve receber o Endereço criado/editado

            if (controllerRecebido == 0) {

                this.cadastrarClienteDialog.setEndereco(this.endereco);
            }

            if(controllerRecebido == 1){

                this.cadastrarFornecedorDialog.setEndereco(this.endereco);
            }

           if(controllerRecebido == 2){

                this.cadastrarFuncionarioDialog.setEndereco(this.endereco);

            }


        }else{

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Insira valores válidos nos campos.");
            alert.showAndWait();

        }

        voltar(actionEvent);

    }

    public void voltar(ActionEvent actionEvent) {

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.close();

    }

    private boolean validarCampos() {

        if(tfCidade.getText().equals(""))
            return false;

        if(tfLogradouro.getText().equals(""))
            return false;

        if(tfEstado.getText().length() != 2)
            return false;

        if(tfNumero.getText().equals(""))
            return false;

        if(tfComplemento.getText().equals(""))
            return false;

        if(tfCep.getText().equals(""))
            return false;

        return true;

    }

}
