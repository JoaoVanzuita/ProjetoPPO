package model.service;

import model.bean.Endereco;
import model.bean.Usuario;
import model.dao.UsuarioDaoImpl;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioService {

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

        usuarioDao.cadastrar(usuario);

    }

    public void deletarUsuario(Usuario usuario){

        usuarioDao.deletar(usuario);

    }

    public void editarUsuario(Usuario usuario){

        usuarioDao.editar(usuario);

    }

    public Usuario consultarUsuarioPorId(int id){

        Usuario usuario = usuarioDao.consultarPorId(id);


        return usuario;
    }

    public ArrayList<Usuario> consultarusuarioPorNome(String nome){

        ArrayList<Usuario> usuarios = usuarioDao.consultarPorNome(nome);

        return usuarios;
    }

    public ArrayList<Usuario> listarTodosUsuarios(){

        ArrayList<Usuario>usuarios = usuarioDao.listarTodos();

        return usuarios;
    }

    public ArrayList<Usuario> listarTodosFuncionarios(){

        ArrayList<Usuario> usuarios = usuarioDao.listarTodosFuncionarios();

        return usuarios;
    }

    public Usuario verificarCadastro(String email, String senha){

        Usuario  usuario = usuarioDao.verificarCadastro(email, senha);

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
