package model.dao;

import javafx.scene.control.Alert;
import model.bean.NFEntrada;
import model.dao.interfaces.NFEntradaDao;
import model.service.CategoriaService;
import util.ConnectionFactory;
import util.AlertSQLException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NFEntradaDaoImpl implements NFEntradaDao {

    private static final String sqlCadastrar = "INSERT INTO nfs_entrada (data, valorTotal) values ( ?, ? );";
    private static final String sqlListarTodos = "SELECT * FROM nfs_entrada;";
    private static final String sqlAtualizarValor = "UPDATE nfs_entrada SET valorTotal = ? WHERE IDNFEntrada = ? ;";
    private static final String sqlConsultarPorId = "SELECT * FROM nfs_entrada WHERE IDNFEntrada = ? ;";
    private static final String sqlConsultarPorData = "SELECT * FROM nfs_entrada WHERE data BETWEEN ? AND ? ORDER BY data; ";
    private  static  NFEntradaDaoImpl instance;

    private NFEntradaDaoImpl() {
    }

    public static synchronized  NFEntradaDaoImpl getInstance(){

        if(instance == null){

            instance = new NFEntradaDaoImpl();

        }

        return instance;

    }

    //Retorna o idNFEntrada para em seguida poder cadastrar ItemNFEntrada
    @Override
    public int cadastrar(NFEntrada nfEntrada){

        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlCadastrar, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setDate(1, Date.valueOf(nfEntrada.getData()));
            statement.setDouble(2, nfEntrada.getValorTotal());

            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();

            int idGerado = -1;

            if (resultSet.next()) {

                idGerado = resultSet.getInt("IDNFEntrada");

            }


            connection.close();
            statement.close();
            resultSet.close();

            return idGerado;

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

        return -1;
    }

    @Override
    public ArrayList<NFEntrada> listarTodos(){

        ArrayList<NFEntrada> nfEntradas = new ArrayList<>();
        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlListarTodos);

            ResultSet resultSet = statement.executeQuery();

            while ((resultSet.next())) {

                int idNFEntrada = resultSet.getInt("IDNFEntrada");
                LocalDate data = resultSet.getDate("data").toLocalDate();
                Double valorTotal = resultSet.getDouble("valorTotal");

                NFEntrada nfEntrada = new NFEntrada(idNFEntrada, data, valorTotal, null);

                nfEntradas.add(nfEntrada);

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

        return nfEntradas;
    }

    @Override
    public void atualizarValorTotal(Double valorTotal, int idNFEntrada){

        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlAtualizarValor);
            statement.setDouble(1, valorTotal);
            statement.setInt(2, idNFEntrada);

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
    public NFEntrada consultarPorId(int idNFEntrada){

        NFEntrada nfEntrada = null;
        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlConsultarPorId);
            statement.setInt(1, idNFEntrada);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                LocalDate data = resultSet.getDate("data").toLocalDate();
                Double valorTotal = resultSet.getDouble("valorTotal");

                nfEntrada = new NFEntrada(idNFEntrada, data, valorTotal, null);

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

        return nfEntrada;
    }

    @Override
    public ArrayList<NFEntrada> consultarPorData(Date dataInicial, Date dataFinal){

        ArrayList<NFEntrada> nfEntradas = new ArrayList<>();
        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlConsultarPorData);
            statement.setDate(1, dataInicial);
            statement.setDate(2, dataFinal);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int idNFEntrada = resultSet.getInt("IDNFEntrada");
                LocalDate data = resultSet.getDate("data").toLocalDate();
                Double valorTotal = resultSet.getDouble("valorTotal");

                NFEntrada nfEntrada = new NFEntrada(idNFEntrada, data, valorTotal, null);

                nfEntradas.add(nfEntrada);

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

        return nfEntradas;
    }
}
