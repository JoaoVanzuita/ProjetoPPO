package model.dao.interfaces;

import model.bean.Categoria;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CategoriaDao {

    void cadastrar(Categoria categoria) throws SQLException;
    void deletar(Categoria categoria) throws SQLException;
    void editar(Categoria categoria) throws SQLException;
    ArrayList<Categoria> consultarPorNome(String nome) throws SQLException;
    Categoria consultarPorId(int idCategoria) throws SQLException;
    ArrayList<Categoria> listarTodos() throws SQLException;

}
