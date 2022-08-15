package model.dao;

import model.bean.NFEntrada;
import model.dao.interfaces.NFEntradaDao;
import util.ConnectionFactory;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

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
    public int cadastrar(NFEntrada nfEntrada) throws SQLException {

        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement statement = connection.prepareStatement(sqlCadastrar, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setDate(1, Date.valueOf(nfEntrada.getData()));
        statement.setDouble(2, nfEntrada.getValorTotal());

        statement.execute();

        ResultSet resultSet = statement.getGeneratedKeys();

        int idGerado = -1;

        if (resultSet.next()){

            idGerado = resultSet.getInt("IDNFEntrada");

        }

        connection.close();
        statement.close();
        resultSet.close();

        return idGerado;
    }

    @Override
    public ArrayList<NFEntrada> listarTodos() throws SQLException {

        ArrayList<NFEntrada> nfEntradas = new ArrayList<>();

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlListarTodos);

        ResultSet resultSet = statement.executeQuery();

        while ((resultSet.next())){

            int idNFEntrada = resultSet.getInt("IDNFEntrada");
            LocalDate data = resultSet.getDate("data").toLocalDate();
            Double valorTotal = resultSet.getDouble("valorTotal");

            NFEntrada nfEntrada = new NFEntrada(idNFEntrada, data, valorTotal, null);

            nfEntradas.add(nfEntrada);

        }

        connection.close();
        statement.close();
        resultSet.close();

        return nfEntradas;
    }

    @Override
    public void atualizarValorTotal(Double valorTotal, int idNFEntrada) throws SQLException {

        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement statement = connection.prepareStatement(sqlAtualizarValor);
        statement.setDouble(1, valorTotal);
        statement.setInt(2, idNFEntrada);

        statement.execute();

        statement.close();
        connection.close();
    }

    @Override
    public NFEntrada consultarPorId(int idNFEntrada) throws SQLException {

        NFEntrada nfEntrada = null;

        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement statement = connection.prepareStatement(sqlConsultarPorId);
        statement.setInt(1, idNFEntrada);

        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){

         LocalDate data = resultSet.getDate("data").toLocalDate();
            Double valorTotal = resultSet.getDouble("valorTotal");

         nfEntrada = new NFEntrada(idNFEntrada, data, valorTotal, null);

        }

        connection.close();
        statement.close();
        resultSet.close();

        return nfEntrada;
    }

    @Override
    public ArrayList<NFEntrada> consultarPorData(Date dataInicial, Date dataFinal) throws SQLException {

        ArrayList<NFEntrada> nfEntradas = new ArrayList<>();

        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement statement = connection.prepareStatement(sqlConsultarPorData);
        statement.setDate(1, dataInicial);
        statement.setDate(2, dataFinal);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){

            int idNFEntrada = resultSet.getInt("IDNFEntrada");
            LocalDate data = resultSet.getDate("data").toLocalDate();
            Double valorTotal = resultSet.getDouble("valorTotal");

            NFEntrada nfEntrada = new NFEntrada(idNFEntrada, data, valorTotal, null);

            nfEntradas.add(nfEntrada);

        }

        connection.close();
        statement.close();
        resultSet.close();

        return nfEntradas;
    }
}
