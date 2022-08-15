package model.dao;

import model.bean.Endereco;
import model.bean.Usuario;
import model.dao.interfaces.UsuarioDao;
import util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioDaoImpl implements UsuarioDao {

    private static final String sqlCadastrar = "INSERT INTO usuarios (nome, email, senha, tipoDeUsuario, cep, complemento, numero, logradouro, cidade, uf) VALUES (?,?,?,?,?,?,?,?,?,?);";
    private static final String sqlDeletar = "DELETE FROM usuarios WHERE IDUsuario = ? ;";
    private static final String sqlEditar = "UPDATE usuarios set nome = ?, email = ?,senha = ?, cep = ?, complemento = ?, numero = ?, logradouro = ?, cidade = ?, uf = ? where IDUsuario = ?;";
    private static final String sqlConsultarPorNome = "SELECT * FROM usuarios WHERE nome LIKE ? ;";
    private static final String sqlConsultarPorId = "SELECT * FROM usuarios WHERE IDUsuario = ? ;";
    private static final String sqlVerificarCadastro = "SELECT * FROM usuarios WHERE email = ? AND senha = ?;" ;
    private static final String sqlListarTodos = "SELECT * FROM usuarios;";
    private static  final String sqlListarTodosFuncionarios = "SELECT * FROM usuarios WHERE tipoDeUsuario = 0;";
    private static UsuarioDaoImpl instance;

    private UsuarioDaoImpl() {
    }

    public static synchronized UsuarioDaoImpl getInstance(){

        if(instance == null){

            instance = new UsuarioDaoImpl();

        }

        return instance;
    }

    @Override
    public void deletar(Usuario usuario) throws SQLException{

        Connection connection = ConnectionFactory.getConnection();

            PreparedStatement statement = connection.prepareStatement(sqlDeletar);
            statement.setInt(1, usuario.getIdUsuario());

            statement.execute();

            connection.close();
            statement.close();

    }

    @Override
    public void cadastrar(Usuario usuario) throws SQLException{

        Connection connection = ConnectionFactory.getConnection();
        Endereco endereco = usuario.getEndereco();

            PreparedStatement statement = connection.prepareStatement(sqlCadastrar);
            statement.setString(1,usuario.getNome());
            statement.setString(2,usuario.getEmail());
            statement.setString(3,"");
            statement.setInt(4,usuario.getTipoDeUsuario());
            statement.setString(5,endereco.getCep());
            statement.setString(6,endereco.getComplemento());
            statement.setInt(7,endereco.getNumero());
            statement.setString(8,endereco.getLogradouro());
            statement.setString(9,endereco.getCidade());
            statement.setString(10,endereco.getUf());

            statement.execute();

            connection.close();
            statement.close();

    }

    @Override
    public void editar(Usuario usuario) throws SQLException{

        Connection connection = ConnectionFactory.getConnection();
        Endereco endereco = usuario.getEndereco();

            PreparedStatement statement = connection.prepareStatement(sqlEditar);

            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getEmail());
            statement.setString(3, usuario.getSenha());
            statement.setString(4, endereco.getCep());
            statement.setString(5, endereco.getComplemento());
            statement.setInt(6, endereco.getNumero());
            statement.setString(7, endereco.getLogradouro());
            statement.setString(8, endereco.getCidade());
            statement.setString(9, endereco.getUf());
            statement.setInt(10, usuario.getIdUsuario());

            statement.execute();

            connection.close();
            statement.close();

    }

    @Override
    public ArrayList<Usuario> consultarPorNome(String nomeUsuario) throws SQLException{

        Connection connection = ConnectionFactory.getConnection();

        ArrayList<Usuario> usuarios = new ArrayList<>();

            PreparedStatement statement = connection.prepareStatement(sqlConsultarPorNome);
            statement.setString(1, "%" + nomeUsuario + "%");

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){

                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                int idUsuario = resultSet.getInt("IDUsuario");
                int tipoDeUsuario = resultSet.getInt("tipoDeUsuario");
                String cep = resultSet.getString("cep");
                String complemento = resultSet.getString("complemento");
                int numero = resultSet.getInt("numero");
                String logradouro = resultSet.getString("logradouro");
                String cidade = resultSet.getString("cidade");
                String uf = resultSet.getString("uf");

                Endereco endereco = new Endereco(uf, cidade, logradouro, complemento, cep, numero);
                Usuario usuario = new Usuario(nome, email, senha, idUsuario, tipoDeUsuario, endereco);

                usuarios.add(usuario);

            }

            connection.close();
            resultSet.close();
            statement.close();

        return usuarios;
    }

    @Override
    public Usuario consultarPorId(int id) throws SQLException{

        Connection connection = ConnectionFactory.getConnection();

        Usuario usuario = null;

        PreparedStatement statement = connection.prepareStatement(sqlConsultarPorId);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){

                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                int tipoDeUsuario = resultSet.getInt("tipoDeUsuario");
                String cep = resultSet.getString("cep");
                String complemento = resultSet.getString("complemento");
                int numero = resultSet.getInt("numero");
                String logradouro = resultSet.getString("logradouro");
                String cidade = resultSet.getString("cidade");
                String uf = resultSet.getString("uf");
                int idUsuario = resultSet.getInt("IDUsuario");

                Endereco endereco = new Endereco(uf, cidade, logradouro, complemento, cep, numero);
                usuario = new Usuario(nome,email,senha,idUsuario,tipoDeUsuario,endereco);

            }

            connection.close();
            resultSet.close();
            statement.close();

        return usuario;
    }

    @Override
    public ArrayList<Usuario> listarTodos() throws SQLException{

        Connection connection = ConnectionFactory.getConnection();
        ArrayList<Usuario> usuarios = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sqlListarTodos);
        ResultSet resultSet = statement.executeQuery();


        while(resultSet.next()){

            int idUsuario = resultSet.getInt("IDUsuario");
            String nome = resultSet.getString("nome");
            String email = resultSet.getString("email");
            String senha = resultSet.getString("senha");
            int tipoDeUsuario = resultSet.getInt("tipoDeUsuario");
            String cep = resultSet.getString("cep");
            String complemento = resultSet.getString("complemento");
            int numero = resultSet.getInt("numero");
            String logradouro = resultSet.getString("logradouro");
            String cidade = resultSet.getString("cidade");
            String uf = resultSet.getString("uf");

            Endereco endereco = new Endereco(uf, cidade, logradouro, complemento, cep, numero);
            Usuario usuario = new Usuario(nome, email, senha, idUsuario, tipoDeUsuario, endereco);

            usuarios.add(usuario);

        }

        connection.close();
        resultSet.close();
        statement.close();

        return usuarios;
    }

    @Override
    public ArrayList<Usuario> listarTodosFuncionarios() throws SQLException {

        Connection connection = ConnectionFactory.getConnection();
        ArrayList<Usuario> usuarios = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sqlListarTodosFuncionarios);
        ResultSet resultSet = statement.executeQuery();


        while(resultSet.next()){

            int idUsuario = resultSet.getInt("IDUsuario");
            String nome = resultSet.getString("nome");
            String email = resultSet.getString("email");
            String senha = resultSet.getString("senha");
            int tipoDeUsuario = resultSet.getInt("tipoDeUsuario");
            String cep = resultSet.getString("cep");
            String complemento = resultSet.getString("complemento");
            int numero = resultSet.getInt("numero");
            String logradouro = resultSet.getString("logradouro");
            String cidade = resultSet.getString("cidade");
            String uf = resultSet.getString("uf");

            Endereco endereco = new Endereco(uf, cidade, logradouro, complemento, cep, numero);
            Usuario usuario = new Usuario(nome, email, senha, idUsuario, tipoDeUsuario, endereco);

            usuarios.add(usuario);

        }

        connection.close();
        resultSet.close();
        statement.close();

        return usuarios;
    }

    @Override
    public Usuario verificarCadastro(String email, String senha) throws SQLException {

        Connection connection = ConnectionFactory.getConnection();

        Usuario usuario = null;

        PreparedStatement statement = connection.prepareStatement(sqlVerificarCadastro);
        statement.setString(1, email);
        statement.setString(2, senha);

        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){

            String nome = resultSet.getString("nome");
            int tipoDeUsuario = resultSet.getInt("tipoDeUsuario");
            String cep = resultSet.getString("cep");
            String complemento = resultSet.getString("complemento");
            int numero = resultSet.getInt("numero");
            String logradouro = resultSet.getString("logradouro");
            String cidade = resultSet.getString("cidade");
            String uf = resultSet.getString("uf");
            int idUsuario = resultSet.getInt("IDUsuario");

            Endereco endereco = new Endereco(uf, cidade, logradouro, complemento, cep, numero);
            usuario = new Usuario(nome,email,senha,idUsuario,tipoDeUsuario,endereco);

        }

        connection.close();
        resultSet.close();
        statement.close();

        return usuario;
    }
}
