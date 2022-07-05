package model.dao.interfaces;

import model.bean.NFEntrada;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public interface NFEntradaDao {

    int cadastrar(NFEntrada nfEntrada) throws SQLException;
    ArrayList<NFEntrada> listarTodos() throws SQLException;
    void atualizarValorTotal(Double valorTotal, int idNFEntrada) throws SQLException;
    NFEntrada consultarPorId(int idNFEntrada) throws  SQLException;
    ArrayList<NFEntrada> consultarPorData(Date dataInicial, Date dataFinal) throws SQLException;
}
