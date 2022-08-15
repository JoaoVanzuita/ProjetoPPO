package model.service;

import model.bean.Endereco;
import model.bean.Usuario;
import model.dao.UsuarioDaoImpl;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioService {

    //TODO: tratamento de SQLException

    private static final UsuarioDaoImpl usuarioDao = UsuarioDaoImpl.getInstance();
    private static UsuarioService instance;

    private UsuarioService() {
    }

    public static synchronized UsuarioService getInstance(){

        if(instance == null){

            instance = new UsuarioService();

        }

        return instance;
    }

    public void cadastrarUsuario(Usuario usuario){


        try {

            usuarioDao.cadastrar(usuario);

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public void deletarUsuario(Usuario usuario){

        try {

            usuarioDao.deletar(usuario);

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public void editarUsuario(Usuario usuario){

        try {

            usuarioDao.editar(usuario);

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public Usuario consultarUsuarioPorId(int id){

        Usuario usuarios = null;

        try {

            usuarios = usuarioDao.consultarPorId(id);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return usuarios;
    }

    public ArrayList<Usuario> consultarusuarioPorNome(String nome){

        ArrayList<Usuario> usuarios = null;

        try {

            usuarios = usuarioDao.consultarPorNome(nome);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return usuarios;
    }

    public ArrayList<Usuario> listarTodosUsuarios(){

        ArrayList<Usuario> usuarios = null;

        try {

            usuarios = usuarioDao.listarTodos();

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return usuarios;
    }

    public ArrayList<Usuario> listarTodosFuncionarios(){

        ArrayList<Usuario> usuarios = null;

        try {

            usuarios = usuarioDao.listarTodosFuncionarios();

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return usuarios;

    }

    public Usuario verificarCadastro(String email, String senha){
        Usuario usuario = null;

        try {

            usuario = usuarioDao.verificarCadastro(email, senha);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return usuario;
    }


    //Método criado com o objetivo de deixar o código do Controller mais limpo
    public Usuario criarUsuario(String nome, String email, Endereco endereco){

        Usuario usuario = new Usuario();

        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setTipoDeUsuario(0);
        usuario.setSenha("");
        usuario.setEndereco(endereco);

        return usuario;
    }

}
