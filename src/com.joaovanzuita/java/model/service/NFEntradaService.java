package model.service;

import model.bean.*;
import model.dao.ItemNFEntradaDaoImpl;
import model.dao.NFEntradaDaoImpl;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class NFEntradaService {

    //TODO: tratamento de SQLException

    private static final NFEntradaDaoImpl nfEntradaDao = NFEntradaDaoImpl.getInstance();
    private static final ItemNFEntradaDaoImpl itemNFEntradaDao = ItemNFEntradaDaoImpl.getInstance();
    private final ProdutoService produtoService = ProdutoService.getInstance();
    private static NFEntradaService instance;

    private NFEntradaService() {
    }

    public static synchronized NFEntradaService getInstance(){

        if(instance == null){

            instance = new NFEntradaService();

        }

        return instance;
    }

    public int cadastrarNFEntrada (NFEntrada nfEntrada){

        try{

            int idNFEntrada = nfEntradaDao.cadastrar(nfEntrada);

            nfEntrada.setIdNFEntrada(idNFEntrada);

            ArrayList<ItemNFEntrada> itensNFEntrada = nfEntrada.getItensNFEntrada();

            Double valorTotal = 0.0;

            for (ItemNFEntrada item: itensNFEntrada
                 ) {

                item.setNfEntrada(nfEntrada);

                itemNFEntradaDao.cadastrar(item);

                //Atualizando valortotal
                valorTotal += item.getValorUnitario() * item.getQuantidade();

                //Atualizando quantidade de produto em estoque
                produtoService.atualizarEstoque(item.getProduto(), item.getQuantidade());

                //Atualizando preços de custo e venda do produto
                Double valorMedio = itemNFEntradaDao.consultarValorMedioPorProduto(item.getProduto());

                produtoService.atualizarValores(item.getProduto(), valorMedio);

            }

            //Atualizando valorTotal de nfEntrada
            nfEntradaDao.atualizarValorTotal(valorTotal, nfEntrada.getIdNFEntrada());

            return idNFEntrada;

        }catch(SQLException e){

            e.printStackTrace();

        }

        return 0;
    }

    public ArrayList<NFEntrada> listarTodasNFEntrada(){

        ArrayList<NFEntrada> nfEntradas = null;

        try {

            nfEntradas = nfEntradaDao.listarTodos();

            //Para cada nfEntrada, adicionar os itens relacionados a ela
            //Item já é retornado com a NFEntrada correspondente

            for (NFEntrada nfEntrada: nfEntradas
                 ) {

                ArrayList<ItemNFEntrada> itens = itemNFEntradaDao.listarPorIdNFEntrada(nfEntrada);

                nfEntrada.setItensNFEntrada(itens);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return  nfEntradas;
    }

    public NFEntrada consultarNFEntradaPorId(int idNFEntrada){

        NFEntrada nfEntrada = null;

        try{

            nfEntrada = nfEntradaDao.consultarPorId(idNFEntrada);

            ArrayList<ItemNFEntrada> itens = itemNFEntradaDao.listarPorIdNFEntrada(nfEntrada);

            nfEntrada.setItensNFEntrada(itens);

        }catch (SQLException e){

            e.printStackTrace();

        }

        return nfEntrada;
    }

    public ArrayList<NFEntrada> consultarNFEntradaPorData(LocalDate localDateInicial, LocalDate localDateFinal){

        ArrayList<NFEntrada> nfEntradas = null;

        Date dataInicial = Date.valueOf(localDateInicial);
        Date dataFinal = Date.valueOf(localDateFinal);

        try{

            nfEntradas = nfEntradaDao.consultarPorData(dataInicial, dataFinal);

            for (NFEntrada nfEntrada: nfEntradas
                 ) {

                ArrayList<ItemNFEntrada> itens = itemNFEntradaDao.listarPorIdNFEntrada(nfEntrada);

                nfEntrada.setItensNFEntrada(itens);

            }

        }catch (SQLException e){

            e.printStackTrace();

        }

        return  nfEntradas;
    }

    //Métodos criados com o objetivo de deixar o código do Controller mais limpo
    public ItemNFEntrada criarItemNFEntrada(Produto produto, Double valorUnitario, int quantidade){

        ItemNFEntrada itemNFEntrada = new ItemNFEntrada();

        itemNFEntrada.setProduto(produto);
        itemNFEntrada.setQuantidade(quantidade);
        itemNFEntrada.setValorUnitario(valorUnitario);

        return itemNFEntrada;
    }

    public NFEntrada criarNFEntrada(ArrayList<ItemNFEntrada> itens){

        NFEntrada nfEntrada = new NFEntrada();

        nfEntrada.setItensNFEntrada(itens);
        nfEntrada.setData(LocalDate.now());
        nfEntrada.setValorTotal(0.0);

        return nfEntrada;
    }
}
