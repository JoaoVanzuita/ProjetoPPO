package model.dao;

import model.bean.Cliente;
import model.bean.NFSaida;
import model.bean.Usuario;
import model.dao.interfaces.NFSaidaDao;
import util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;

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
    public int cadastrar(NFSaida nfSaida) throws SQLException{

        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement statement = connection.prepareStatement(sqlCadastrar, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setDate(1, Date.valueOf(nfSaida.getData()));
        statement.setDouble(2, nfSaida.getValorTotal());
        statement.setInt(3, nfSaida.getCliente().getIdCliente());
        statement.setInt(4, nfSaida.getUsuario().getIdUsuario());

        statement.execute();

        ResultSet resultSet = statement.getGeneratedKeys();

        int idGerado = -1;

        if(resultSet.next()){

            idGerado = resultSet.getInt("IDNFSaida");

        }

        connection.close();
        statement.close();
        resultSet.close();

        return idGerado;
    }

    @Override
    public ArrayList<NFSaida> listarTodos() throws SQLException {

        ArrayList<NFSaida> nfSaidas = new ArrayList<>();

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlListarTodos);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){

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

        return nfSaidas;
    }

    @Override
    public void atualizarValorTotal(Double valorTotal, int idNFSaida) throws SQLException{

        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement statement = connection.prepareStatement(sqlAtualizarValor);
        statement.setDouble(1, valorTotal);
        statement.setInt(2, idNFSaida);

        statement.execute();

        statement.close();
        connection.close();

    }

    @Override
    public NFSaida consultarPorId(int idNFSaida) throws SQLException {

        NFSaida nfSaida = null;

        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement statement = connection.prepareStatement(sqlConsultarPorId);
        statement.setInt(1, idNFSaida);

        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){

            Cliente cliente = ClienteDaoImpl.getInstance().consultarPorId(resultSet.getInt("IDCliente"));
            Usuario usuario = UsuarioDaoImpl.getInstance().consultarPorId(resultSet.getInt("IDUsuario"));

            LocalDate data = resultSet.getDate("data").toLocalDate();
            Double valorTotal = resultSet.getDouble("valorTotal");

            nfSaida = new NFSaida(idNFSaida, cliente, usuario, data, valorTotal, null);

        }

        connection.close();
        statement.close();
        resultSet.close();

        return nfSaida;
    }

    @Override
    public ArrayList<NFSaida> consultarPorData(Date dataInicial, Date dataFinal) throws SQLException {

        ArrayList<NFSaida> nfSaidas = new ArrayList<>();

        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement statement = connection.prepareStatement(sqlConsultarPorData);
        statement.setDate(1, dataInicial);
        statement.setDate(2, dataFinal);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){

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

        return nfSaidas;
    }

    @Override
    public ArrayList<NFSaida> consultarPorFuncionario(int idUsuario) throws SQLException {

        ArrayList<NFSaida> nfSaidas = new ArrayList<>();

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlConsultarPorFuncionario);
        statement.setInt(1, idUsuario);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){

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

        return nfSaidas;
    }
}
