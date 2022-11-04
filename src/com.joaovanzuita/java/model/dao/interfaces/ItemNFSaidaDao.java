package model.dao.interfaces;

import model.bean.ItemNFSaida;
import model.bean.NFSaida;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemNFSaidaDao {

    void cadastrar(ItemNFSaida itemNFSaida);
    ArrayList<ItemNFSaida> listarPorIdNFSaida(NFSaida nfSaida);

}
