package model.dao.interfaces;

import model.bean.Cliente;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ClienteDao {

    void cadastrar(Cliente cliente);
    void deletar(Cliente cliente);
    void editar(Cliente cliente);
    ArrayList<Cliente> consultarPorNome(String nome);
    Cliente consultarPorId(int idCliente);
    ArrayList<Cliente> listarTodos();

}
