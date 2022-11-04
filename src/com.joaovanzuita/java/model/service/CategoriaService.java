package model.service;

import model.bean.Categoria;
import model.dao.CategoriaDaoImpl;

import java.util.ArrayList;

public class CategoriaService {

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

        categoriaDao.cadastrar(categoria);

    }

    public void deletarCategoria(Categoria categoria){

        categoriaDao.deletar(categoria);

    }

    public void editarCategoria(Categoria categoria){

        categoriaDao.editar(categoria);

    }

    public ArrayList<Categoria> consultarCategoriaPorNome(String nome){

        ArrayList<Categoria> categorias = categoriaDao.consultarPorNome(nome);

        return categorias;
    }

    public Categoria consultarCategoriaPorId(int id){

        Categoria categoria = categoriaDao.consultarPorId(id);

        return categoria;
    }

    public ArrayList<Categoria> listarTodasCategorias(){

        ArrayList<Categoria> categorias = categoriaDao.listarTodos();

        return categorias;
    }

    //Método criado com o objetivo de deixar o código do Controller mais limpo
    public Categoria criarCategoria(String nome){

        Categoria categoria = new Categoria();
        categoria.setNome(nome);

        return categoria;
    }
}
