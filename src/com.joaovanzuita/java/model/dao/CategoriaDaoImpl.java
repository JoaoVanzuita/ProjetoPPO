package model.dao;

import javafx.scene.control.Alert;
import model.bean.Categoria;
import model.dao.interfaces.CategoriaDao;
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

public class CategoriaDaoImpl implements CategoriaDao {

    private static final String sqlCadastrar = "INSERT INTO categorias(nome) VALUES(?);";
    private static final String sqlDeletar = "DELETE FROM categorias WHERE IDCategoria = ?;";
    private static final String sqlEditar = "UPDATE categorias SET nome = ? WHERE IDCategoria = ?";
    private static final String sqlConsultarPorNome = "SELECT * FROM categorias WHERE nome LIKE ? ;";
    private static final String sqlConsultarPorID = "SELECT * FROM categorias WHERE IDCategoria = ?;";
    private static final String sqlListarTodos = "SELECT * FROM categorias;";
    private static CategoriaDaoImpl instance;

    private CategoriaDaoImpl(){
    }

    public static synchronized CategoriaDaoImpl getInstance(){

        if(instance == null){

            instance = new CategoriaDaoImpl();

        }

        return instance;

    }

    @Override
    public void cadastrar(Categoria categoria){

        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlCadastrar);
            statement.setString(1, categoria.getNome());

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
    public void deletar(Categoria categoria){

        Connection connection = ConnectionFactory.getConnection();

        try{

            PreparedStatement statement = connection.prepareStatement(sqlDeletar);
            statement.setInt(1, categoria.getIdCategoria());

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
    public void editar(Categoria categoria){

        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlEditar);
            statement.setString(1, categoria.getNome());
            statement.setInt(2, categoria.getIdCategoria());

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
    public ArrayList<Categoria> consultarPorNome(String nomeCategoria){

        ArrayList<Categoria> categorias = new ArrayList<>();
        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlConsultarPorNome);
            statement.setString(1, "%" + nomeCategoria + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int idCategoria = resultSet.getInt("IDCategoria");
                String nome = resultSet.getString("nome");

                Categoria categoria = new Categoria(nome, idCategoria);

                categorias.add(categoria);

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

        return categorias;

    }

    @Override
    public Categoria consultarPorId(int idCategoria){

        Categoria categoria = null;
        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlConsultarPorID);
            statement.setInt(1, idCategoria);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String nome = resultSet.getString("nome");

                categoria = new Categoria(nome, idCategoria);

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

        return categoria;
    }

    @Override
    public ArrayList<Categoria> listarTodos(){

        ArrayList<Categoria> categorias = new ArrayList<>();
        Connection connection = ConnectionFactory.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(sqlListarTodos);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int idCategoria = resultSet.getInt("IDCategoria");
                String nome = resultSet.getString("nome");

                Categoria categoria = new Categoria(nome, idCategoria);

                categorias.add(categoria);

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

        return categorias;
    }
}
