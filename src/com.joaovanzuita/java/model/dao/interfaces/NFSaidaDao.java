package model.dao.interfaces;

import model.bean.NFSaida;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public interface NFSaidaDao {

    int cadastrar(NFSaida nfSaida);
    ArrayList<NFSaida> listarTodos();
    void atualizarValorTotal(Double valorTotal, int idNFSaida);
    NFSaida consultarPorId(int idNFSaida);
    ArrayList<NFSaida> consultarPorData(Date dataInicial, Date dataFinal);
    ArrayList<NFSaida> consultarPorFuncionario(int idUsuario);

}