package model.service;

import model.bean.Categoria;
import model.dao.CategoriaDaoImpl;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoriaService {

    //TODO: tratamento de SQLException

    private static final CategoriaDaoImpl categoriaDao = CategoriaDaoImpl.getInstance();
    private static CategoriaService instance;

    private CategoriaService() {
    }

    public static synchronized CategoriaService getInstance(){

        if(instance == null){

            instance = new CategoriaService();

        }

        return instance;
    }

    public void cadastrarCategoria(Categoria categoria){

        try{

            categoriaDao.cadastrar(categoria);

        }catch (SQLException e){

            e.printStackTrace();

        }

    }

    public void deletarCategoria(Categoria categoria){

        try {

            categoriaDao.deletar(categoria);

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public void editarCategoria(Categoria categoria){

        try {

            categoriaDao.editar(categoria);

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public ArrayList<Categoria> consultarCategoriaPorNome(String nome){

        ArrayList<Categoria> categorias = null;

        try {

            categorias = categoriaDao.consultarPorNome(nome);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return categorias;
    }

    public Categoria consultarCategoriaPorId(int id){

        Categoria categoria = null;

        try {

            categoria = categoriaDao.consultarPorId(id);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return categoria;
    }

    public ArrayList<Categoria> listarTodasCategorias(){

        ArrayList<Categoria> categorias = null;

        try {

            categorias = categoriaDao.listarTodos();

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return categorias;
    }

    //Método criado com o objetivo de deixar o código do Controller mais limpo
    public Categoria criarCategoria(String nome){

            Categoria categoria = new Categoria();
            categoria.setNome(nome);

        return categoria;
    }
}
