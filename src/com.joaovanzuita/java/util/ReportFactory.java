package util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;

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

            JasperReport jasperReport = JasperCompileManager.compileReport("src/com.joaovanzuita/resources/reports/NFEntrada.jrxml");
            jasperPrint = JasperFillManager.fillReport(jasperReport, param, ConnectionFactory.getConnection());

            viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle("Nota fiscal de entrada");

            viewer.setVisible(true);

        } catch (JRException e) {

            e.printStackTrace();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return viewer;
    }

}
