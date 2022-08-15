package model.service;

import model.bean.*;
import model.dao.ItemNFSaidaDaoImpl;
import model.dao.NFSaidaDaoImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.sql.Date;

public class NFSaidaService {

    //TODO: tratamento de SQLException

    private static final NFSaidaDaoImpl nfSaidaDao = NFSaidaDaoImpl.getInstance();
    private static final ItemNFSaidaDaoImpl itemNFSaidaDao = ItemNFSaidaDaoImpl.getInstance();
    private final ProdutoService produtoService = ProdutoService.getInstance();
    private static NFSaidaService instance;

    private NFSaidaService() {
    }

    public static synchronized NFSaidaService getInstance(){

        if(instance == null){

            instance = new NFSaidaService();

        }

        return instance;
    }

    public int cadastrarNFSaida (NFSaida nfSaida){

        try{

            int idNFSaida = nfSaidaDao.cadastrar(nfSaida);

            nfSaida.setIdNFSaida(idNFSaida);

            ArrayList<ItemNFSaida> itensNFSaida = nfSaida.getItensNFSaida();

            Double valorTotal = 0.0;

            for (ItemNFSaida item: itensNFSaida
                 ) {

                item.setNfSaida(nfSaida);

                itemNFSaidaDao.cadastrar(item);

                //Atualizando valorTotal
                valorTotal += item.getValorUnitario() * item.getQuantidade();


                //Atualizar quantidade de produto em estoque
                produtoService.atualizarEstoque(item.getProduto(), -(item.getQuantidade()));

            }

            //Atualizando valorTotal de nfSaida
            nfSaidaDao.atualizarValorTotal(valorTotal, nfSaida.getIdNFSaida());

            return idNFSaida;

        }catch (SQLException e){

            e.printStackTrace();

        }

        return 0;

    }

    public ArrayList<NFSaida> listarTodasNFSaida (){

        ArrayList<NFSaida> nfSaidas = null;

        try{

            nfSaidas = nfSaidaDao.listarTodos();

            //Para cada nfSaida, adicionar os itens relacionados a ela
            //Item já é retornado com a NFSaida correspondente

            for (NFSaida nfSaida: nfSaidas
                 ) {

                ArrayList<ItemNFSaida> itens = itemNFSaidaDao.listarPorIdNFSaida(nfSaida);

                nfSaida.setItensNFSaida(itens);

            }

        }catch (SQLException e){

            e.printStackTrace();

        }

        return nfSaidas;
    }

    public NFSaida consultarNFSaidaPorId(int idNFSaida){

        NFSaida nfSaida = null;

        try{

            nfSaida = nfSaidaDao.consultarPorId(idNFSaida);

            ArrayList<ItemNFSaida> itens = itemNFSaidaDao.listarPorIdNFSaida(nfSaida);

            nfSaida.setItensNFSaida(itens);

        }catch (SQLException e){

            e.printStackTrace();

        }

        return nfSaida;
    }

    public ArrayList<NFSaida> consultarNFSaidaPorData(LocalDate localDateInicial, LocalDate localDateFinal){

        ArrayList<NFSaida> nfSaidas = null;

        Date dataInicial = Date.valueOf(localDateInicial);
        Date dataFinal = Date.valueOf(localDateFinal);

        try{

            nfSaidas = nfSaidaDao.consultarPorData(dataInicial, dataFinal);

            for (NFSaida nfSaida: nfSaidas
                 ) {

                ArrayList<ItemNFSaida> itens = itemNFSaidaDao.listarPorIdNFSaida(nfSaida);

                nfSaida.setItensNFSaida(itens);

            }

        }catch (SQLException e){

            e.printStackTrace();

        }

        return nfSaidas;
    }

    //Objetivo: gerar relatórios (?)
    public ArrayList<NFSaida> consultarNFSaidaPorFuncionario(int idUsuario){

        ArrayList<NFSaida> nfSaidas = null;

        try{

            nfSaidas = nfSaidaDao.consultarPorFuncionario(idUsuario);

            for (NFSaida nfSaida: nfSaidas
            ) {

                ArrayList<ItemNFSaida> itens = itemNFSaidaDao.listarPorIdNFSaida(nfSaida);

                nfSaida.setItensNFSaida(itens);

            }

        }catch (SQLException e){

            e.printStackTrace();

        }

        return nfSaidas;

    }

    //Métodos criados com o objetivo de deixar o código do Controller mais limpo
    public ItemNFSaida criarItemNFSaida(Produto produto, int quantidade){

        ItemNFSaida itemNFSaida = new ItemNFSaida();

        itemNFSaida.setProduto(produto);
        itemNFSaida.setQuantidade(quantidade);
        itemNFSaida.setValorUnitario(produto.getPrecoDeVenda());

        return itemNFSaida;
    }

    public NFSaida criarNFSaida(Usuario usuario, Cliente cliente, ArrayList<ItemNFSaida> itens){

        NFSaida nfSaida = new NFSaida();

        nfSaida.setUsuario(usuario);
        nfSaida.setCliente(cliente);
        nfSaida.setItensNFSaida(itens);
        nfSaida.setData(LocalDate.now());
        nfSaida.setValorTotal(0.0);

        return nfSaida;
    }
}
