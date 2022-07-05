package model.dao.interfaces;

import model.bean.NFSaida;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public interface NFSaidaDao {

    int cadastrar(NFSaida nfSaida) throws SQLException;
    ArrayList<NFSaida> listarTodos() throws SQLException;
    void atualizarValorTotal(Double valorTotal, int idNFSaida) throws SQLException;
    NFSaida consultarPorId(int idNFSaida) throws  SQLException;
    ArrayList<NFSaida> consultarPorData(Date dataInicial, Date dataFinal) throws SQLException;
    ArrayList<NFSaida> consultarPorFuncionario(int idUsuario) throws SQLException;

}