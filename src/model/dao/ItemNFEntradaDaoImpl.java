package model.dao;

import model.bean.*;
import model.dao.interfaces.ITemNFEntradaDao;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemNFEntradaDaoImpl implements ITemNFEntradaDao {

    private static final String sqlCadastrar = "INSERT INTO itens_nf_entrada (quantidade, valorUnitario, IDNFEntrada, IDProduto) values (?,?,?,?);";
    private static final String sqlListar = "SELECT * FROM itens_nf_entrada WHERE IDNFEntrada = ? ;";
    private static final String sqlConsultarValorMedio = "SELECT ROUND( AVG(valorUnitario), 2) AS valorMedio FROM itens_nf_entrada WHERE IDProduto = ? ;";
    private static ItemNFEntradaDaoImpl instance;

    private ItemNFEntradaDaoImpl() {
    }

    public static synchronized ItemNFEntradaDaoImpl getInstance() {

        if (instance == null) {

            instance = new ItemNFEntradaDaoImpl();

        }

        return instance;

    }

    //Cadastro de item sempre é feito logo após o cadastro de uma nota fiscal de entrada
    //e precisa do IDNFEntrada da NFEntrada
    @Override
    public void cadastrar(ItemNFEntrada iTemNFEntrada) throws SQLException {

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlCadastrar);

        statement.setInt(1, iTemNFEntrada.getQuantidade());
        statement.setDouble(2, iTemNFEntrada.getValorUnitario());
        statement.setInt(3, iTemNFEntrada.getNfEntrada().getIdNFEntrada());
        statement.setInt(4, iTemNFEntrada.getProduto().getIdProduto());

        statement.execute();

        connection.close();
        statement.close();

    }

    @Override
    public ArrayList<ItemNFEntrada> listarPorIdNFEntrada(NFEntrada nfEntrada) throws SQLException {

        ArrayList<ItemNFEntrada> itensNFEntrada = new ArrayList<>();

        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement statement = connection.prepareStatement(sqlListar);
        statement.setInt(1, nfEntrada.getIdNFEntrada());

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){

            int idProduto = resultSet.getInt("IDProduto");
            Produto produto = ProdutoDaoImpl.getInstance().consultarPorId(idProduto);

            int quantidade = resultSet.getInt("quantidade");
            int idItemNFEntrada = resultSet.getInt("IDItemNFEntrada");
            Double valorUnitario = resultSet.getDouble("valorUnitario");

            ItemNFEntrada itemNFEntrada = new ItemNFEntrada(quantidade, idItemNFEntrada, produto, nfEntrada, valorUnitario);

            itensNFEntrada.add(itemNFEntrada);

        }

        connection.close();
        statement.close();
        resultSet.close();

        return itensNFEntrada;

    }

    @Override
    public Double consultarValorMedioPorProduto(Produto produto) throws SQLException {

        Connection connection = ConnectionFactory.getConnection();

        Double valorMedio = 0.0;

        PreparedStatement statement = connection.prepareStatement(sqlConsultarValorMedio);
        statement.setInt(1, produto.getIdProduto());

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()){

            valorMedio = resultSet.getDouble("valorMedio");

        }

        connection.close();
        statement.close();
        resultSet.close();

        return valorMedio;
    }

}