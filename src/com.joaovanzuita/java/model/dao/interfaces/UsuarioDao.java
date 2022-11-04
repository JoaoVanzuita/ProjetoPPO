package model.dao.interfaces;

import model.bean.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UsuarioDao {

    void cadastrar(Usuario usuario);
    void deletar(Usuario usuario);
    void editar(Usuario usuario);
    ArrayList<Usuario> consultarPorNome(String nome);
    Usuario consultarPorId(int idUsuario);
    ArrayList<Usuario> listarTodos();
    ArrayList<Usuario> listarTodosFuncionarios();
    Usuario verificarCadastro(String nome, String senha);

}
