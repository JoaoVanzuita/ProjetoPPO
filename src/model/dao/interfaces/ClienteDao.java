package model.dao.interfaces;

import model.bean.Cliente;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ClienteDao {

    void cadastrar(Cliente cliente) throws SQLException;
    void deletar(Cliente cliente) throws SQLException;
    void editar(Cliente cliente) throws SQLException;
    ArrayList<Cliente> consultarPorNome(String nome) throws SQLException;
    Cliente consultarPorId(int idCliente) throws SQLException;
    ArrayList<Cliente> listarTodos() throws SQLException;

}
