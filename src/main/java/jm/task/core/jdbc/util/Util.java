package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/mytestbd";
    // реализуйте настройку соеденения с БД
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD);
            System.out.println("Успешное подключение");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
