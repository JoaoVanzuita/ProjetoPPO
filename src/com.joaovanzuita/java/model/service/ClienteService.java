package model.service;

import model.bean.Cliente;
import model.bean.Endereco;
import model.dao.ClienteDaoImpl;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteService {

    private static final ClienteDaoImpl clienteDao = ClienteDaoImpl.getInstance();
    private static ClienteService instance;

    private ClienteService() {
    }

    public static synchronized ClienteService getInstance(){

        if(instance == null){

            instance = new ClienteService();

        }

        return instance;
    }

    public void cadastrarCliente(Cliente cliente){

        clienteDao.cadastrar(cliente);

    }

    public void deletarCliente(Cliente cliente){

        clienteDao.deletar(cliente);

    }

    public void editarCliente(Cliente cliente){

        clienteDao.editar(cliente);

    }

    public Cliente consultarClientePorId(int idCliente){

        Cliente cliente = clienteDao.consultarPorId(idCliente);

        return cliente;
    }

    public ArrayList<Cliente> consultarClientePorNome(String nomeCliente){

        ArrayList<Cliente> clientes = clienteDao.consultarPorNome(nomeCliente);

        return clientes;
    }

    public ArrayList<Cliente> listarTodosClientes(){

        ArrayList<Cliente> clientes = clienteDao.listarTodos();

        return clientes;
    }

    //Método criado com o objetivo de deixar o código do Controller mais limpo
    public Cliente criarCliente(String nome, String email, Endereco endereco){

        Cliente cliente = new Cliente();

        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setEndereco(endereco);

        return cliente;
    }

}