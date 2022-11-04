package model.dao;

import javafx.scene.control.Alert;
import model.bean.ItemNFSaida;
import model.bean.NFSaida;
import model.bean.Produto;
import model.dao.interfaces.ItemNFSaidaDao;
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

public class ItemNFSaidaDaoImpl implements ItemNFSaidaDao {

    private static final String sqlCadastrar = "INSERT INTO itens_nf_saida(quantidade, valorUnitario, IDNFSaida, IDProduto) values (?, ?, ?, ?);";
    private static final String sqlListar = "SELECT * FROM itens_nf_saida WHERE IDNFSaida = ? ;";
    private static ItemNFSaidaDaoImpl instance;

    private ItemNFSaidaDaoImpl() {
    }

    public static synchronized ItemNFSaidaDaoImpl getInstance(){

        if(instance == null){

            instance = new ItemNFSaidaDaoImpl();

        }

        return instance;

    }

    @Override
    public void cadastrar(ItemNFSaida itemNFSaida){

        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlCadastrar);

            statement.setInt(1, itemNFSaida.getQuantidade());
            statement.setDouble(2, itemNFSaida.getValorUnitario());
            statement.setInt(3, itemNFSaida.getNfSaida().getIdNFSaida());
            statement.setInt(4, itemNFSaida.getProduto().getIdProduto());

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
    public ArrayList<ItemNFSaida> listarPorIdNFSaida(NFSaida nfSaida){

        ArrayList<ItemNFSaida> itensNFSaida = new ArrayList<>();
        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlListar);
            statement.setInt(1, nfSaida.getIdNFSaida());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int idProduto = resultSet.getInt("IDProduto");
                Produto produto = ProdutoDaoImpl.getInstance().consultarPorId(idProduto);

                int quantidade = resultSet.getInt("quantidade");
                int idItemNFsaida = resultSet.getInt("IDItemNFSaida");
                Double valorUnitario = resultSet.getDouble("valorUnitario");

                ItemNFSaida itemNFSaida = new ItemNFSaida(quantidade, idItemNFsaida, nfSaida, produto, valorUnitario);

                itensNFSaida.add(itemNFSaida);

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

        return itensNFSaida;
    }
}
