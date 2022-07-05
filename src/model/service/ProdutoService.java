package model.service;

import model.bean.Categoria;
import model.bean.Fornecedor;
import model.bean.Produto;
import model.dao.ProdutoDaoImpl;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutoService {

    //TODO: tratamento de SQLException

    private static final ProdutoDaoImpl produtoDao = ProdutoDaoImpl.getInstance();
    private static ProdutoService instance;

    private ProdutoService() {
    }

    public static synchronized ProdutoService getInstance(){

        if(instance == null){

            instance = new ProdutoService();

        }

        return instance;
    }

    public void cadastrarProduto(Produto produto){

        try{

            produtoDao.cadastrar(produto);

        }catch (SQLException e){

            e.printStackTrace();

        }

    }

    public void editarProduto(Produto produto){

        try{

            produtoDao.editar(produto);

        }catch (SQLException e){

            e.printStackTrace();

        }

    }

    public void deletarProduto(Produto produto){

        try{

            produtoDao.deletar(produto);

        }catch (SQLException e){

            e.printStackTrace();

        }

    }

    public ArrayList<Produto> consultarProdutoPorNome(String nome){

        ArrayList<Produto> produtos = null;

        try{

            produtos = produtoDao.consultarPorNome(nome);

        }catch (SQLException e){

            e.printStackTrace();

        }

        return produtos;
    }

    public Produto consultarProdutoPorId(int id){

        Produto produto = null;

        try{

            produto = produtoDao.consultarPorId(id);

        }catch (SQLException e){

            e.printStackTrace();

        }

        return produto;
    }
    public ArrayList<Produto> listarTodosProdutos(){

        ArrayList<Produto> produtos = null;

        try{

            produtos = produtoDao.listarTodos();

        }catch (SQLException e){

            e.printStackTrace();

        }

        return produtos;
    }

    public void atualizarEstoque(Produto produto, int quantidade){

        produto.setQuantidade(produto.getQuantidade() + quantidade);

        editarProduto(produto);

    }

    public void atualizarValores(Produto produto, Double precoDeCusto){

        produto.setPrecoDeCusto(precoDeCusto);
        produto.setPrecoDeVenda(precoDeCusto * 1.15);

        editarProduto(produto);

    }

    //Método criado com o objetivo de deixar o código do Controller mais limpo
    public Produto criarProduto(String nome, int quantidade, Double precoDeCusto, Categoria categoria, Fornecedor fornecedor){

        Produto produto = new Produto();

        produto.setNome(nome);
        produto.setPrecoDeCusto(precoDeCusto);
        produto.setPrecoDeVenda(precoDeCusto * 1.15);
        produto.setFornecedor(fornecedor);
        produto.setCategoria(categoria);
        produto.setQuantidade(quantidade);

        return produto;
    }
}
