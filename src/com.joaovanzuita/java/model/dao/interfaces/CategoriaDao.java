package model.dao.interfaces;

import model.bean.Categoria;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CategoriaDao {

    void cadastrar(Categoria categoria);
    void deletar(Categoria categoria);
    void editar(Categoria categoria);
    ArrayList<Categoria> consultarPorNome(String nome);
    Categoria consultarPorId(int idCategoria);
    ArrayList<Categoria> listarTodos();

}
