package util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.SQLException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ReportFactory {

    private static ReportFactory instance;

    private ReportFactory() {
    }

    public static synchronized ReportFactory getInstance(){

        if(instance == null){

            instance = new ReportFactory();

        }

        return instance;

    }

    public JasperViewer criarNFEntrada(int idNFEntrada) {

        JasperPrint jasperPrint;
        JasperViewer viewer = null;

        Map param = new HashMap();
        param.put("ID_NF_ENTRADA", idNFEntrada);

        try {

            Connection connection = ConnectionFactory.getConnection();

            jasperPrint = JasperFillManager.fillReport("src/com.joaovanzuita/resources/reports/NFEntrada.jasper", param, connection);

            connection.close();

            viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle("Nota fiscal de entrada " + idNFEntrada);

            viewer.setVisible(true);

        } catch (JRException e) {

            e.printStackTrace();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return viewer;
    }

    public JasperViewer criarNFSaida(int idNFSaida) {

        JasperPrint jasperPrint;
        JasperViewer viewer = null;

        Map param = new HashMap();
        param.put("ID_NF_SAIDA", idNFSaida);

        try {

            Connection connection = ConnectionFactory.getConnection();

            jasperPrint = JasperFillManager.fillReport("src/com.joaovanzuita/resources/reports/NFSaida.jasper", param, connection);

            connection.close();


            viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle("Nota fiscal de saída " + idNFSaida);

        } catch (JRException e) {

            e.printStackTrace();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return viewer;
    }

    public JasperViewer criarRelatorioVendasPorFuncionario(LocalDate dataInicio, LocalDate dataFinal) {

        JasperPrint jasperPrint;
        JasperViewer viewer = null;

        Map param = new HashMap<>();
        param.put("datainicio", java.sql.Date.valueOf(dataInicio));
        param.put("datafinal", java.sql.Date.valueOf(dataFinal));

        try {

            Connection connection = ConnectionFactory.getConnection();

            jasperPrint = JasperFillManager.fillReport("src/com.joaovanzuita/resources/reports/VendasPorFuncionario.jasper",param, connection);

            connection.close();

            viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle("Relatório de vendas por funcionário");

        } catch (JRException e) {

            e.printStackTrace();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return viewer;
    }

    public JasperViewer criarRelatorioEstoque(){

        JasperPrint jasperPrint;
        JasperViewer viewer = null;

        try {

            Connection connection = ConnectionFactory.getConnection();

            jasperPrint = JasperFillManager.fillReport("src/com.joaovanzuita/resources/reports/Estoque.jasper",null, connection);

            connection.close();

            viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle("Relatório de estoque");

        } catch (JRException e) {

            e.printStackTrace();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return viewer;

    }

    public JasperViewer criarRelatorioProdutosMaisVendidos(LocalDate dataInicio, LocalDate dataFinal){

        JasperPrint jasperPrint;
        JasperViewer viewer = null;

        Map param = new HashMap<>();
        param.put("datainicio", java.sql.Date.valueOf(dataInicio));
        param.put("datafinal", java.sql.Date.valueOf(dataFinal));

        try {

            Connection connection = ConnectionFactory.getConnection();

            jasperPrint = JasperFillManager.fillReport("src/com.joaovanzuita/resources/reports/ProdutosMaisVendidos.jasper",param, connection);

            connection.close();

            viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle("Relatório de produtos mais vendidos");

        } catch (JRException e) {

            e.printStackTrace();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return viewer;

    }

    public JasperViewer criarRelatorioMovimentacoesFinanceiras(LocalDate dataInicio, LocalDate dataFinal){

        JasperPrint jasperPrint;
        JasperViewer viewer = null;

        Map param = new HashMap<>();
        param.put("datainicio", java.sql.Date.valueOf(dataInicio));
        param.put("datafinal", java.sql.Date.valueOf(dataFinal));


        try {

            Connection connection = ConnectionFactory.getConnection();

            jasperPrint = JasperFillManager.fillReport("src/com.joaovanzuita/resources/reports/movimentacoesFinanceiras.jasper", param, connection);

            connection.close();

            viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle("Relatório de movimentações financeiras");

        } catch (JRException e) {

            e.printStackTrace();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return viewer;

    }

}
