package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/testbase";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                if (!connection.isClosed()) {
                    System.out.println("Соединение с БД установлено");
                }
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка SQL", e);
            }
        }
        return connection;
    }

}
