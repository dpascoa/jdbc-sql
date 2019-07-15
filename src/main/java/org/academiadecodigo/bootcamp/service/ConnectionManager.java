package org.academiadecodigo.bootcamp.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by codecadet on 15/07/2019.
 */
public class ConnectionManager {

    Connection connection;

    public ConnectionManager() {

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ac?serverTimezone=UTC", "root", "");
            this.connection = connection;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
