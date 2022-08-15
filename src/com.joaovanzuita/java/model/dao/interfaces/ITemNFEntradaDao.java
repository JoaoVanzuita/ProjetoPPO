package model.dao.interfaces;

import model.bean.ItemNFEntrada;
import model.bean.NFEntrada;
import model.bean.Produto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ITemNFEntradaDao {

    void cadastrar(ItemNFEntrada iTemNFEntrada) throws SQLException;
    ArrayList<ItemNFEntrada> listarPorIdNFEntrada(NFEntrada nfEntrada) throws SQLException;
    Double consultarValorMedioPorProduto(Produto produto) throws  SQLException;

}
