package model.service;
import model.bean.Endereco;
import model.bean.Fornecedor;
import model.dao.FornecedorDaoImpl;
import java.sql.SQLException;
import java.util.ArrayList;

public class FornecedorService {

    //TODO: tratamento de SQLException

    private static final FornecedorDaoImpl fornecedorDao = FornecedorDaoImpl.getInstance();
    private static FornecedorService instance;

    private FornecedorService() {
    }

    public static synchronized FornecedorService getInstance(){

        if(instance == null){

            instance = new FornecedorService();

        }

        return instance;
    }

    public void cadastrarFornecedor(Fornecedor fornecedor){


        try {

            fornecedorDao.cadastrar(fornecedor);

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public void deletarFornecedor(Fornecedor fornecedor){

        try {

            fornecedorDao.deletar(fornecedor);

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public void editarFornecedor(Fornecedor fornecedor){

        try {

            fornecedorDao.editar(fornecedor);

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public Fornecedor consultarFornecedorPorId(int id){

        Fornecedor fornecedores = null;

        try {

            fornecedores = fornecedorDao.consultarPorId(id);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return fornecedores;
    }

    public ArrayList<Fornecedor> consultarFornecedorPorNome(String nome){

        ArrayList<Fornecedor> fornecedores = null;

        try {

            fornecedores = fornecedorDao.consultarPorNome(nome);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return fornecedores;
    }

    public ArrayList<Fornecedor> listarTodosFornecedores(){

        ArrayList<Fornecedor> fornecedores = null;

        try {

            fornecedores = fornecedorDao.listarTodos();

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return fornecedores;
    }

    //Método criado com o objetivo de deixar o código do Controller mais limpo
    public Fornecedor criarFornecedor(String nome, String email, Endereco endereco){

        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setNome(nome);
        fornecedor.setEmail(email);
        fornecedor.setEndereco(endereco);

        return fornecedor;
    }

}
