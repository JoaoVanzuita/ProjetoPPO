package model.service;
import model.bean.Endereco;
import model.bean.Fornecedor;
import model.dao.FornecedorDaoImpl;
import java.sql.SQLException;
import java.util.ArrayList;

public class FornecedorService {

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

        fornecedorDao.cadastrar(fornecedor);

    }

    public void deletarFornecedor(Fornecedor fornecedor){

        fornecedorDao.deletar(fornecedor);
    }

    public void editarFornecedor(Fornecedor fornecedor){

        fornecedorDao.editar(fornecedor);

    }

    public Fornecedor consultarFornecedorPorId(int id){

        Fornecedor fornecedores = fornecedorDao.consultarPorId(id);

        return fornecedores;
    }

    public ArrayList<Fornecedor> consultarFornecedorPorNome(String nome){

        ArrayList<Fornecedor> fornecedores = fornecedorDao.consultarPorNome(nome);

        return fornecedores;
    }

    public ArrayList<Fornecedor> listarTodosFornecedores(){

        ArrayList<Fornecedor> fornecedores = fornecedorDao.listarTodos();

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
