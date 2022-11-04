package util;

import javafx.scene.control.Alert;
import model.service.CategoriaService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {

    private static final Properties properties = getProperties();
    private static final String URL = properties.getProperty("database.url");
    private static final String USER = properties.getProperty("database.user");
    private static final String PASSWORD = properties.getProperty("database.password");
    private static final int MAX_CON = Integer.parseInt(properties.getProperty("database.max_con"));

    private static final Connection[] CONNECTIONS = new Connection[MAX_CON];

    public static Connection getConnection(){

        try {

            for (int i = 0; i < MAX_CON; i++) {

                if (CONNECTIONS[i] == null || CONNECTIONS[i].isClosed()) {

                    CONNECTIONS[i] = DriverManager.getConnection(URL, USER, PASSWORD);

                    return CONNECTIONS[i];
                }

            }

        }catch (SQLException e){

            e.printStackTrace();
            Logger.getLogger(CategoriaService.class.getName()).log(Level.WARNING, e.getMessage(), e);

            Alert alert = new util.AlertSQLException(Alert.AlertType.ERROR, e);
            alert.showAndWait();

        }

        return null;
    }

    private static Properties getProperties(){
        Properties properties = new Properties();

        String url =  "DataBaseConnection.properties";

        try {

            properties.load(ConnectionFactory.class.getResourceAsStream(url));

        } catch (IOException e) {

            e.printStackTrace();
        }

        return properties;
    }

}
