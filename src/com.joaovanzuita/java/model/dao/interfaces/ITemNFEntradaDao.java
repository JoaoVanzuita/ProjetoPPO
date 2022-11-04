package model.dao.interfaces;

import model.bean.ItemNFEntrada;
import model.bean.NFEntrada;
import model.bean.Produto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ITemNFEntradaDao {

    void cadastrar(ItemNFEntrada iTemNFEntrada);
    ArrayList<ItemNFEntrada> listarPorIdNFEntrada(NFEntrada nfEntrada);
    Double consultarValorMedioPorProduto(Produto produto);

}
