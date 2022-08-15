package model.service;

import model.bean.Cliente;
import model.bean.Endereco;
import model.dao.ClienteDaoImpl;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteService {

    //TODO: tratamento de SQLException

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


        try {

            clienteDao.cadastrar(cliente);

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public void deletarCliente(Cliente cliente){

        try {

            clienteDao.deletar(cliente);

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public void editarCliente(Cliente cliente){

        try {

            clienteDao.editar(cliente);

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public Cliente consultarClientePorId(int idCliente){

        Cliente cliente = null;

        try {

            cliente = clienteDao.consultarPorId(idCliente);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return cliente;
    }

    public ArrayList<Cliente> consultarClientePorNome(String nomeCliente){

        ArrayList<Cliente> clientes = null;

        try {

            clientes = clienteDao.consultarPorNome(nomeCliente);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return clientes;
    }

    public ArrayList<Cliente> listarTodosClientes(){

        ArrayList<Cliente> clientes = null;

        try {

            clientes = clienteDao.listarTodos();

        } catch (SQLException e) {

            e.printStackTrace();

        }

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