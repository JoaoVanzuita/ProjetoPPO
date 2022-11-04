package model.dao.interfaces;

import model.bean.Fornecedor;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FornecedorDao {

    void cadastrar(Fornecedor fornecedor) throws SQLException;
    void deletar(Fornecedor fornecedor) throws SQLException;
    void editar(Fornecedor fornecedor) throws SQLException;
    ArrayList<Fornecedor> consultarPorNome(String nome) throws SQLException;
    Fornecedor consultarPorId(int idFornecedor) throws SQLException;
    ArrayList<Fornecedor> listarTodos() throws SQLException;

}
