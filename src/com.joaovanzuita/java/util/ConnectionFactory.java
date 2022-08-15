package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL = "jdbc:postgresql://localhost/projetoppo";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final int MAX_CON = 10;

    private static final Connection[] CONNECTIONS = new Connection[MAX_CON];

    public static Connection getConnection() throws SQLException{


        for (int i = 0; i < MAX_CON; i++){

            if(CONNECTIONS[i] == null || CONNECTIONS[i].isClosed()){

                CONNECTIONS[i] = DriverManager.getConnection(URL, USER, PASSWORD);

                return  CONNECTIONS[i];
            }

        }

        throw new SQLException("Muitas conexões abertas.");

    }

}
