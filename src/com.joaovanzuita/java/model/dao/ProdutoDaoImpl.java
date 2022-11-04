package model.dao;

import javafx.scene.control.Alert;
import model.bean.Categoria;
import model.bean.Fornecedor;
import model.bean.Produto;
import model.dao.interfaces.ProdutoDao;
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

public class ProdutoDaoImpl implements ProdutoDao {

    private static final String sqlCadastrar = "INSERT INTO produtos (nome, precoDeCusto, precoDeVenda, quantidade, IDCategoria, IDFornecedor) values(?,?,?,?,?,?);";
    private static final String sqldeletar = "DELETE FROM produtos WHERE IDProduto = ?;";
    private static final String sqlEditar = "UPDATE produtos set nome = ?, precoDeCusto = ?, precoDeVenda = ?, quantidade = ?, IDCategoria = ?, IDFornecedor = ? WHERE IDProduto = ?;";
    private static final String sqlConsultarPorNome = "SELECT * FROM produtos WHERE nome LIKE ? ;";
    private static final String sqlConsultarPorId = "SELECT * FROM produtos WHERE IDProduto = ? ;";
    private static final String sqlListarTodos = "SELECT * FROM produtos; ";

    private static ProdutoDaoImpl instance;

    private ProdutoDaoImpl() {
    }

    public static synchronized ProdutoDaoImpl getInstance(){

        if(instance == null){

            instance = new ProdutoDaoImpl();

        }
        return instance;
    }

    @Override
    public void cadastrar(Produto produto){

        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlCadastrar);
            statement.setString(1, produto.getNome());
            statement.setDouble(2, produto.getPrecoDeCusto());
            statement.setDouble(3, produto.getPrecoDeVenda());
            statement.setInt(4, produto.getQuantidade());
            statement.setInt(5, produto.getCategoria().getIdCategoria());
            statement.setInt(6, produto.getFornecedor().getIdFornecedor());

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
    public void deletar(Produto produto){

        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqldeletar);
            statement.setInt(1, produto.getIdProduto());

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
    public void editar(Produto produto){

        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlEditar);
            statement.setString(1, produto.getNome());
            statement.setDouble(2, produto.getPrecoDeCusto());
            statement.setDouble(3, produto.getPrecoDeVenda());
            statement.setInt(4, produto.getQuantidade());
            statement.setInt(5, produto.getCategoria().getIdCategoria());
            statement.setInt(6, produto.getFornecedor().getIdFornecedor());
            statement.setInt(7, produto.getIdProduto());

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
    public ArrayList<Produto> consultarPorNome(String nomeProduto){

        ArrayList<Produto> produtos = new ArrayList<>();
        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlConsultarPorNome);
            statement.setString(1, "%" + nomeProduto + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Categoria categoria = CategoriaDaoImpl.getInstance().consultarPorId(resultSet.getInt("IDCategoria"));
                Fornecedor fornecedor = FornecedorDaoImpl.getInstance().consultarPorId(resultSet.getInt("IDFornecedor"));

                String nome = resultSet.getString("nome");
                int idProduto = resultSet.getInt("IDProduto");
                Double precoDeCusto = resultSet.getDouble("precoDeCusto");
                Double precoDeVenda = resultSet.getDouble("precoDeVenda");
                int quantidade = resultSet.getInt("quantidade");

                Produto produto = new Produto(nome, idProduto, quantidade, categoria, fornecedor, precoDeCusto, precoDeVenda);

                produtos.add(produto);

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

        return produtos;
    }


    @Override
    public Produto consultarPorId(int idProduto){

        Produto produto = null;
        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlConsultarPorId);
            statement.setInt(1, idProduto);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                Categoria categoria = CategoriaDaoImpl.getInstance().consultarPorId(resultSet.getInt("IDCategoria"));
                Fornecedor fornecedor = FornecedorDaoImpl.getInstance().consultarPorId(resultSet.getInt("IDFornecedor"));

                String nome = resultSet.getString("nome");
                Double precoDeCusto = resultSet.getDouble("precoDeCusto");
                Double precoDeVenda = resultSet.getDouble("precoDeVenda");
                int quantidade = resultSet.getInt("quantidade");

                produto = new Produto(nome, idProduto, quantidade, categoria, fornecedor, precoDeCusto, precoDeVenda);

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

        return produto;
    }

    @Override
    public ArrayList<Produto> listarTodos(){

        ArrayList<Produto> produtos = new ArrayList<>();
        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlListarTodos);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Categoria categoria = CategoriaDaoImpl.getInstance().consultarPorId(resultSet.getInt("IDCategoria"));
                Fornecedor fornecedor = FornecedorDaoImpl.getInstance().consultarPorId(resultSet.getInt("IDFornecedor"));

                String nome = resultSet.getString("nome");
                int idProduto = resultSet.getInt("IDProduto");
                Double precoDeCusto = resultSet.getDouble("precoDeCusto");
                Double precoDeVenda = resultSet.getDouble("precoDeVenda");
                int quantidade = resultSet.getInt("quantidade");

                Produto produto = new Produto(nome, idProduto, quantidade, categoria, fornecedor, precoDeCusto, precoDeVenda);

                produtos.add(produto);

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

        return produtos;
    }

}