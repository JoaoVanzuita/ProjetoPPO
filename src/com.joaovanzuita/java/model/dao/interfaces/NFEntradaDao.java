package model.dao.interfaces;

import model.bean.NFEntrada;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public interface NFEntradaDao {

    int cadastrar(NFEntrada nfEntrada);
    ArrayList<NFEntrada> listarTodos();
    void atualizarValorTotal(Double valorTotal, int idNFEntrada);
    NFEntrada consultarPorId(int idNFEntrada);
    ArrayList<NFEntrada> consultarPorData(Date dataInicial, Date dataFinal);
}
