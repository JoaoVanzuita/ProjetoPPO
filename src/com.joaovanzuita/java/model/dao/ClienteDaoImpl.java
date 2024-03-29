package model.dao;

import javafx.scene.control.Alert;
import model.bean.Cliente;
import model.bean.Endereco;
import model.dao.interfaces.ClienteDao;
import model.service.CategoriaService;
import util.ConnectionFactory;
import util.AlertSQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteDaoImpl implements ClienteDao {

    private static final String sqlCadastrar = "INSERT INTO clientes (nome, email, cep, complemento, numero, logradouro, cidade, uf) VALUES (?,?,?,?,?,?,?,?);";
    private static final String sqlDeletar = "DELETE FROM clientes WHERE IDCliente = ?;";
    private static final String sqlEditar = "UPDATE  clientes set nome = ?, email = ?, cep = ?, complemento = ?, numero = ?, logradouro = ?, cidade = ?, uf = ? where IDCLiente = ?;";
    private static final String sqlConsultarPorNome = "SELECT * FROM clientes WHERE nome LIKE ? ;";
    private static final String sqlConsultarPorId = "SELECT * FROM clientes WHERE IDCLiente = ?;";
    private static final String sqlListarTodos = "SELECT * FROM clientes;";
    private static ClienteDaoImpl instance;

    private ClienteDaoImpl() {
    }

    public static synchronized ClienteDaoImpl getInstance(){

        if(instance == null){

            instance = new ClienteDaoImpl();

        }

        return instance;
    }

    public void cadastrar(Cliente cliente){

        Connection connection = ConnectionFactory.getConnection();

        try {

            Endereco endereco = cliente.getEndereco();

            PreparedStatement statement = connection.prepareStatement(sqlCadastrar);

            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getEmail());
            statement.setString(3, endereco.getCep());
            statement.setString(4, endereco.getComplemento());
            statement.setInt(5, endereco.getNumero());
            statement.setString(6, endereco.getLogradouro());
            statement.setString(7, endereco.getCidade());
            statement.setString(8, endereco.getUf());

            statement.execute();

            
            connection.close();
            statement.close();

        }catch (SQLException e){

            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
            Logger.getLogger(CategoriaService.class.getName()).log(Level.WARNING, e.getMessage(), e);

            Alert alert = new AlertSQLException(Alert.AlertType.ERROR, e);
            alert.showAndWait();

        }

    }

    @Override
    public void deletar(Cliente cliente){

        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlDeletar);

            statement.setInt(1, cliente.getIdCliente());

            statement.execute();

            
            connection.close();
            statement.close();

        }catch (SQLException e){

            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
            Logger.getLogger(CategoriaService.class.getName()).log(Level.WARNING, e.getMessage(), e);

            Alert alert = new AlertSQLException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

    }

    @Override
    public void editar(Cliente cliente){

        Connection connection = ConnectionFactory.getConnection();

        try {

            Endereco endereco = cliente.getEndereco();

            PreparedStatement statement = connection.prepareStatement(sqlEditar);

            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getEmail());
            statement.setString(3, endereco.getCep());
            statement.setString(4, endereco.getComplemento());
            statement.setInt(5, endereco.getNumero());
            statement.setString(6, endereco.getLogradouro());
            statement.setString(7, endereco.getCidade());
            statement.setString(8, endereco.getUf());
            statement.setInt(9, cliente.getIdCliente());

            statement.execute();

            
            connection.close();
            statement.close();

        }catch (SQLException e){

            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
            Logger.getLogger(CategoriaService.class.getName()).log(Level.WARNING, e.getMessage(), e);

            Alert alert = new AlertSQLException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

    }

    @Override
    public ArrayList<Cliente> consultarPorNome(String nomeCliente){

        ArrayList<Cliente> clientes = new ArrayList<>();
        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlConsultarPorNome);
            statement.setString(1, "%" + nomeCliente + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int idCliente = resultSet.getInt("IDCliente");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String cep = resultSet.getString("cep");
                String complemento = resultSet.getString("complemento");
                int numero = resultSet.getInt("numero");
                String logradouro = resultSet.getString("logradouro");
                String cidade = resultSet.getString("cidade");
                String uf = resultSet.getString("uf");

                Endereco endereco = new Endereco(uf, cidade, logradouro, complemento, cep, numero);
                Cliente cliente = new Cliente(nome, email, idCliente, endereco);

                clientes.add(cliente);

            }

            
            connection.close();
            resultSet.close();
            statement.close();

        }catch (SQLException e){

            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
            Logger.getLogger(CategoriaService.class.getName()).log(Level.WARNING, e.getMessage(), e);

            Alert alert = new AlertSQLException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

        return clientes;
    }

    @Override
    public Cliente consultarPorId(int idCliente){

        Cliente cliente = null;
        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlConsultarPorId);
            statement.setInt(1, idCliente);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String cep = resultSet.getString("cep");
                String complemento = resultSet.getString("complemento");
                int numero = resultSet.getInt("numero");
                String logradouro = resultSet.getString("logradouro");
                String cidade = resultSet.getString("cidade");
                String uf = resultSet.getString("uf");

                Endereco endereco = new Endereco(uf, cidade, logradouro, complemento, cep, numero);
                cliente = new Cliente(nome, email, idCliente, endereco);

            }

            
            connection.close();
            resultSet.close();
            statement.close();

        }catch (SQLException e){

            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
            Logger.getLogger(CategoriaService.class.getName()).log(Level.WARNING, e.getMessage(), e);

            Alert alert = new AlertSQLException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

        return cliente;
    }

    @Override
    public ArrayList<Cliente> listarTodos(){

        ArrayList<Cliente> clientes = new ArrayList<>();
        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlListarTodos);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int idCliente = resultSet.getInt("IDCliente");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String cep = resultSet.getString("cep");
                String complemento = resultSet.getString("complemento");
                int numero = resultSet.getInt("numero");
                String logradouro = resultSet.getString("logradouro");
                String cidade = resultSet.getString("cidade");
                String uf = resultSet.getString("uf");

                Endereco endereco = new Endereco(uf, cidade, logradouro, complemento, cep, numero);
                Cliente cliente = new Cliente(nome, email, idCliente, endereco);

                clientes.add(cliente);

            }

            
            connection.close();
            resultSet.close();
            statement.close();

        }catch (SQLException e){

            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
            Logger.getLogger(CategoriaService.class.getName()).log(Level.WARNING, e.getMessage(), e);

            Alert alert = new AlertSQLException(Alert.AlertType.ERROR, e);
            alert.showAndWait();
        }

        return clientes;
    }
}
