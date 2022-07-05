package model.dao;

import model.bean.Endereco;
import model.bean.Fornecedor;
import model.dao.interfaces.FornecedorDao;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FornecedorDaoImpl implements FornecedorDao {

    private static final String sqlCadastrar = "INSERT INTO fornecedores (nome, email, cep, complemento, numero, logradouro, cidade, uf) VALUES (?,?,?,?,?,?,?,?);";
    private static final String sqlDeletar = "DELETE FROM fornecedores WHERE IDFornecedor = ?;";
    private static final String sqlEditar = "UPDATE  fornecedores set nome = ?, email = ?, cep = ?, complemento = ?, numero = ?, logradouro = ?, cidade = ?, uf = ? where IDFornecedor = ?;";
    private static final String sqlConsultarPorNome = "SELECT * FROM fornecedores WHERE nome LIKE ? ;";
    private static final String sqlConsultarPorId = "SELECT * FROM fornecedores WHERE IDFornecedor = ?;";
    private static final String sqlListarTodos = "SELECT * FROM fornecedores;";
    private static FornecedorDaoImpl instance;

    private FornecedorDaoImpl() {
    }

    public static synchronized FornecedorDaoImpl getInstance(){

        if(instance == null){

            instance = new FornecedorDaoImpl();

        }

        return instance;

    }

    @Override
    public void cadastrar(Fornecedor fornecedor) throws SQLException{

        Connection connection = ConnectionFactory.getConnection();
        Endereco endereco = fornecedor.getEndereco();

            PreparedStatement statement = connection.prepareStatement(sqlCadastrar);

            statement.setString(1, fornecedor.getNome());
            statement.setString(2, fornecedor.getEmail());
            statement.setString(3, endereco.getCep());
            statement.setString(4, endereco.getComplemento());
            statement.setInt(5, endereco.getNumero());
            statement.setString(6, endereco.getLogradouro());
            statement.setString(7, endereco.getCidade());
            statement.setString(8, endereco.getUf());

            statement.execute();

            connection.close();
            statement.close();

    }

    @Override
    public void deletar(Fornecedor fornecedor) throws SQLException{

        Connection connection = ConnectionFactory.getConnection();

            PreparedStatement statement = connection.prepareStatement(sqlDeletar);

            statement.setInt(1, fornecedor.getIdFornecedor());

            statement.execute();

            connection.close();
            statement.close();

    }

    @Override
    public void editar(Fornecedor fornecedor) throws SQLException{

        Connection connection = ConnectionFactory.getConnection();
        Endereco endereco = fornecedor.getEndereco();

            PreparedStatement statement = connection.prepareStatement(sqlEditar);

            statement.setString(1, fornecedor.getNome());
            statement.setString(2, fornecedor.getEmail());
            statement.setString(3, endereco.getCep());
            statement.setString(4, endereco.getComplemento());
            statement.setInt(5, endereco.getNumero());
            statement.setString(6, endereco.getLogradouro());
            statement.setString(7, endereco.getCidade());
            statement.setString(8, endereco.getUf());
            statement.setInt(9, fornecedor.getIdFornecedor());

            statement.execute();

            connection.close();
            statement.close();

    }

    @Override
    public ArrayList<Fornecedor> consultarPorNome(String nomeFornecedor) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        ArrayList<Fornecedor> fornecedores = new ArrayList<>();

            PreparedStatement statement = connection.prepareStatement(sqlConsultarPorNome);
            statement.setString(1, "%" + nomeFornecedor + "%");

            ResultSet resultSet = statement.executeQuery();


            while(resultSet.next()){

                int idCliente = resultSet.getInt("IDFornecedor");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String cep = resultSet.getString("cep");
                String complemento = resultSet.getString("complemento");
                int numero = resultSet.getInt("numero");
                String logradouro = resultSet.getString("logradouro");
                String cidade = resultSet.getString("cidade");
                String uf = resultSet.getString("uf");

                Endereco endereco = new Endereco(uf, cidade, logradouro, complemento, cep, numero);
                Fornecedor fornecedor = new Fornecedor(nome, email, idCliente, endereco);

                fornecedores.add(fornecedor);

            }

            connection.close();
            resultSet.close();
            statement.close();

        return fornecedores;
    }

    @Override
    public Fornecedor consultarPorId(int idFornecedor) throws SQLException {

        Connection connection = ConnectionFactory.getConnection();

        Fornecedor fornecedor = null;

            PreparedStatement statement = connection.prepareStatement(sqlConsultarPorId);
            statement.setInt(1, idFornecedor);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){

                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String cep = resultSet.getString("cep");
                String complemento = resultSet.getString("complemento");
                int numero = resultSet.getInt("numero");
                String logradouro = resultSet.getString("logradouro");
                String cidade = resultSet.getString("cidade");
                String uf = resultSet.getString("uf");

                Endereco endereco = new Endereco(uf, cidade, logradouro, complemento, cep, numero);
                fornecedor = new Fornecedor(nome, email, idFornecedor, endereco);

            }

            connection.close();
            resultSet.close();
            statement.close();

        return fornecedor;

    }

    @Override
    public ArrayList<Fornecedor> listarTodos() throws SQLException {

        Connection connection = ConnectionFactory.getConnection();
        ArrayList<Fornecedor> fornecedores = new ArrayList<>();

            PreparedStatement statement = connection.prepareStatement(sqlListarTodos);

            ResultSet resultSet = statement.executeQuery();


            while(resultSet.next()){

                int idFornecedor = resultSet.getInt("IDFornecedor");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String cep = resultSet.getString("cep");
                String complemento = resultSet.getString("complemento");
                int numero = resultSet.getInt("numero");
                String logradouro = resultSet.getString("logradouro");
                String cidade = resultSet.getString("cidade");
                String uf = resultSet.getString("uf");

                Endereco endereco = new Endereco(uf, cidade, logradouro, complemento, cep, numero);
                Fornecedor fornecedor = new Fornecedor(nome, email, idFornecedor, endereco);

                fornecedores.add(fornecedor);

            }

            connection.close();
            resultSet.close();
            statement.close();

        return fornecedores;
    }
}
