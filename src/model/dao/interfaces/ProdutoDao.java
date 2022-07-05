package model.dao.interfaces;

import model.bean.Produto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProdutoDao {

    void cadastrar(Produto produto) throws SQLException;
    void deletar(Produto produto) throws SQLException;
    void editar(Produto produto) throws SQLException;
    ArrayList<Produto> consultarPorNome(String nome) throws SQLException;
    Produto consultarPorId(int idProduto) throws SQLException;
    ArrayList<Produto> listarTodos() throws SQLException;


}
