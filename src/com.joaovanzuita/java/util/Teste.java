package util;

import javafx.animation.PathTransition;
import javafx.css.Match;
import model.bean.*;
import model.service.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public  class Teste {

    public static void cadastrarUsuario() {

        UsuarioService usuarioService = UsuarioService.getInstance();

        Endereco endereco = new Endereco("uf", "cidade", "logradouro", "complemento", "cep", 1);

        Usuario funcionario = usuarioService.criarUsuario("funcionario", "emailFuncionario", endereco);
        Usuario gerente = usuarioService.criarUsuario("gerente", "emailGerente", endereco);

        usuarioService.cadastrarUsuario(funcionario);
        usuarioService.cadastrarUsuario(gerente);

    }

    public static void cadastrarCliente() {

        ClienteService clienteService = ClienteService.getInstance();

        Endereco endereco = new Endereco("uf", "cidade", "logradouro", "complemento", "cep", 1);


        Cliente cliente1 = clienteService.criarCliente("cliente1", "emailCliente1", endereco);
        Cliente cliente2 = clienteService.criarCliente("cliente2", "emailCliente2", endereco);

        clienteService.cadastrarCliente(cliente1);
        clienteService.cadastrarCliente(cliente2);

    }

    public static void cadastrarCategoria() {

        CategoriaService categoriaService = CategoriaService.getInstance();

        Categoria categoria1 = categoriaService.criarCategoria("categoria1");
        Categoria categoria2 = categoriaService.criarCategoria("categoria2");

        categoriaService.cadastrarCategoria(categoria1);
        categoriaService.cadastrarCategoria(categoria2);

    }

    public static void cadastrarFornecedor() {

        FornecedorService fornecedorService = FornecedorService.getInstance();

        Endereco endereco = new Endereco("uf", "cidade", "logradouro", "complemento", "cep", 1);

        Fornecedor fornecedor1 = fornecedorService.criarFornecedor("fornecedor1", "emailFornecedor1", endereco);
        Fornecedor fornecedor2 = fornecedorService.criarFornecedor("fornecedor2", "emailFornecedor2", endereco);

        fornecedorService.cadastrarFornecedor(fornecedor1);
        fornecedorService.cadastrarFornecedor(fornecedor2);

    }

    public static void cadastrarProduto() {

        ProdutoService produtoService = ProdutoService.getInstance();
        CategoriaService categoriaService = CategoriaService.getInstance();
        FornecedorService fornecedorService = FornecedorService.getInstance();

        Categoria categoria1 = categoriaService.consultarCategoriaPorId(1);
        Categoria categoria2 = categoriaService.consultarCategoriaPorId(2);

        Fornecedor fornecedor1 = fornecedorService.consultarFornecedorPorId(1);
        Fornecedor fornecedor2 = fornecedorService.consultarFornecedorPorId(2);

        Produto produto1 = produtoService.criarProduto("produto1", 0, 10.0, categoria1, fornecedor1);
        Produto produto2 = produtoService.criarProduto("produto2", 0, 20.0, categoria2, fornecedor2);

        produtoService.cadastrarProduto(produto1);
        produtoService.cadastrarProduto(produto2);

    }

    public static void cadastrarNFEntrada() {

        ProdutoService produtoService = ProdutoService.getInstance();
        NFEntradaService nfEntradaService = NFEntradaService.getInstance();

        Produto produto1 = produtoService.consultarProdutoPorId(1);
        Produto produto2 = produtoService.consultarProdutoPorId(2);

        ItemNFEntrada item1 = nfEntradaService.criarItemNFEntrada(produto1, 20.0, 10);
        ItemNFEntrada item2 = nfEntradaService.criarItemNFEntrada(produto2, 40.0, 10);

        ArrayList<ItemNFEntrada> itens = new ArrayList<>();
        itens.add(item1);
        itens.add(item2);

        NFEntrada nfEntrada = nfEntradaService.criarNFEntrada(itens);

        //Itens são cadastrados junto com a NFEntrada
        nfEntradaService.cadastrarNFEntrada(nfEntrada);

    }

    public static void cadastrarNFSaida() {

        ProdutoService produtoService = ProdutoService.getInstance();
        UsuarioService usuarioService = UsuarioService.getInstance();
        ClienteService clienteService = ClienteService.getInstance();
        NFSaidaService nfSaidaService = NFSaidaService.getInstance();

        Produto produto1 = produtoService.consultarProdutoPorId(1);
        Produto produto2 = produtoService.consultarProdutoPorId(2);

        Cliente cliente1 = clienteService.consultarClientePorId(1);
        Usuario funcionario = usuarioService.consultarUsuarioPorId(1);

        ItemNFSaida item1 = nfSaidaService.criarItemNFSaida(produto1, 10);
        ItemNFSaida item2 = nfSaidaService.criarItemNFSaida(produto2, 10);

        ArrayList<ItemNFSaida> itens = new ArrayList<>();
        itens.add(item1);
        itens.add(item2);

        NFSaida nfSaida = nfSaidaService.criarNFSaida(funcionario, cliente1, itens);

        //Itens são cadastrados junto com a NFSaida
        nfSaidaService.cadastrarNFSaida(nfSaida);

    }

    public static void main(String[] args) {

//        cadastrarUsuario();
//
//        cadastrarCliente();
//
//        cadastrarCategoria();
//
//        cadastrarFornecedor();
//
//        cadastrarProduto();
//
//        cadastrarNFEntrada();
//
//        cadastrarNFSaida();

    }
}