package model.dao.interfaces;

import model.bean.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UsuarioDao {

    void cadastrar(Usuario usuario) throws SQLException;
    void deletar(Usuario usuario) throws SQLException;
    void editar(Usuario usuario) throws SQLException;
    ArrayList<Usuario> consultarPorNome(String nome) throws SQLException;
    Usuario consultarPorId(int idUsuario) throws SQLException;
    ArrayList<Usuario> listarTodos() throws SQLException;
    ArrayList<Usuario> listarTodosFuncionarios() throws SQLException;
    Usuario verificarCadastro(String nome, String senha) throws SQLException;

}
