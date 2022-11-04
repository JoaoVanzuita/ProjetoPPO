package model.dao.interfaces;

import model.bean.Produto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProdutoDao {

    void cadastrar(Produto produto);
    void deletar(Produto produto);
    void editar(Produto produto);
    ArrayList<Produto> consultarPorNome(String nome);
    Produto consultarPorId(int idProduto);
    ArrayList<Produto> listarTodos();


}
