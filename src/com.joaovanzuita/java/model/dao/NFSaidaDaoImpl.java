package model.dao;

import javafx.scene.control.Alert;
import model.bean.Cliente;
import model.bean.NFSaida;
import model.bean.Usuario;
import model.dao.interfaces.NFSaidaDao;
import model.service.CategoriaService;
import util.AlertSQLException;
import util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NFSaidaDaoImpl implements NFSaidaDao {

    private static final String sqlCadastrar = "INSERT INTO nfs_saida(data, valorTotal, IDCLiente, IDUsuario) values(?,?,?,?);";
    private static final String sqlListarTodos = "SELECT * FROM nfs_saida;";
    private static final String sqlAtualizarValor = "UPDATE nfs_saida set valorTotal = ? WHERE IDNFSaida = ?; ";
    private static final String sqlConsultarPorId = "SELECT * FROM nfs_saida WHERE IDNFSaida = ?;";
    private static final String sqlConsultarPorData = "SELECT * FROM nfs_saida WHERE data BETWEEN ? AND ? ORDER BY data;";
    private static final String sqlConsultarPorFuncionario = "SELECT * FROM nfs_saida WHERE IDUsuario = ?;";
    private static NFSaidaDaoImpl instance;

    private NFSaidaDaoImpl() {
    }

    public static synchronized NFSaidaDaoImpl getInstance(){

        if(instance == null){

            instance = new NFSaidaDaoImpl();

        }

        return instance;

    }

    //Retorna o idNFSaida para em seguida poder cadastrar ItemNFSaida
    @Override
    public int cadastrar(NFSaida nfSaida){

        int idGerado = -1;
        Connection connection = ConnectionFactory.getConnection();

        try{

            PreparedStatement statement = connection.prepareStatement(sqlCadastrar, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setDate(1, Date.valueOf(nfSaida.getData()));
            statement.setDouble(2, nfSaida.getValorTotal());
            statement.setInt(3, nfSaida.getCliente().getIdCliente());
            statement.setInt(4, nfSaida.getUsuario().getIdUsuario());

            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {

                idGerado = resultSet.getInt("IDNFSaida");

            }

            
            connection.close();
            statement.close();
            resultSet.close();

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

        return idGerado;
    }

    @Override
    public ArrayList<NFSaida> listarTodos(){

        ArrayList<NFSaida> nfSaidas = new ArrayList<>();
        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlListarTodos);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Cliente cliente = ClienteDaoImpl.getInstance().consultarPorId(resultSet.getInt("IDCliente"));
                Usuario usuario = UsuarioDaoImpl.getInstance().consultarPorId(resultSet.getInt("IDUsuario"));

                int idNFSaida = resultSet.getInt("idNFSaida");
                LocalDate data = resultSet.getDate("data").toLocalDate();
                Double valorTotal = resultSet.getDouble("valorTotal");

                NFSaida nfSaida = new NFSaida(idNFSaida, cliente, usuario, data, valorTotal, null);

                nfSaidas.add(nfSaida);

            }

            
            connection.close();
            statement.close();
            resultSet.close();

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

        return nfSaidas;
    }

    @Override
    public void atualizarValorTotal(Double valorTotal, int idNFSaida){

        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlAtualizarValor);
            statement.setDouble(1, valorTotal);
            statement.setInt(2, idNFSaida);

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
    public NFSaida consultarPorId(int idNFSaida){

        NFSaida nfSaida = null;
        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlConsultarPorId);
            statement.setInt(1, idNFSaida);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                Cliente cliente = ClienteDaoImpl.getInstance().consultarPorId(resultSet.getInt("IDCliente"));
                Usuario usuario = UsuarioDaoImpl.getInstance().consultarPorId(resultSet.getInt("IDUsuario"));

                LocalDate data = resultSet.getDate("data").toLocalDate();
                Double valorTotal = resultSet.getDouble("valorTotal");

                nfSaida = new NFSaida(idNFSaida, cliente, usuario, data, valorTotal, null);

            }

            
            connection.close();
            statement.close();
            resultSet.close();

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

        return nfSaida;
    }

    @Override
    public ArrayList<NFSaida> consultarPorData(Date dataInicial, Date dataFinal){

        ArrayList<NFSaida> nfSaidas = new ArrayList<>();
        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlConsultarPorData);
            statement.setDate(1, dataInicial);
            statement.setDate(2, dataFinal);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Cliente cliente = ClienteDaoImpl.getInstance().consultarPorId(resultSet.getInt("IDCliente"));
                Usuario usuario = UsuarioDaoImpl.getInstance().consultarPorId(resultSet.getInt("IDUsuario"));

                int idNFSaida = resultSet.getInt("IDNFSaida");
                LocalDate data = resultSet.getDate("data").toLocalDate();
                Double valorTotal = resultSet.getDouble("valorTotal");

                NFSaida nfSaida = new NFSaida(idNFSaida, cliente, usuario, data, valorTotal, null);

                nfSaidas.add(nfSaida);

            }

            
            connection.close();
            statement.close();
            resultSet.close();

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

        return nfSaidas;
    }

    @Override
    public ArrayList<NFSaida> consultarPorFuncionario(int idUsuario){

        ArrayList<NFSaida> nfSaidas = new ArrayList<>();
        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlConsultarPorFuncionario);
            statement.setInt(1, idUsuario);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Cliente cliente = ClienteDaoImpl.getInstance().consultarPorId(resultSet.getInt("IDCliente"));
                Usuario usuario = UsuarioDaoImpl.getInstance().consultarPorId(resultSet.getInt("IDUsuario"));

                int idNFSaida = resultSet.getInt("IDNFSaida");
                LocalDate data = resultSet.getDate("data").toLocalDate();
                Double valorTotal = resultSet.getDouble("valorTotal");

                NFSaida nfSaida = new NFSaida(idNFSaida, cliente, usuario, data, valorTotal, null);

                nfSaidas.add(nfSaida);

            }

            
            connection.close();
            statement.close();
            resultSet.close();

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

        return nfSaidas;
    }
}
