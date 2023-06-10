package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao, AutoCloseable {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String query = "create table IF NOT EXISTS users(id int auto_increment, name VARCHAR(100) not null, last_name varchar(100) not null, age tinyint not null, constraint id primary key (id));";

        try {
            Statement statement = Util.getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String query = "drop table IF EXISTS users;";

        try {
            Statement statement = Util.getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "Insert Into users (name, last_name, age) values (?, ?, ?)";

        try {
            PreparedStatement statement = Util.getConnection().prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String query = "delete from users where id = ?";
        try {
            PreparedStatement statement = Util.getConnection().prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String query = "select id, name, last_name, age from users";
        try {
            List<User> list = new ArrayList<>();
            Statement statement = Util.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                list.add(new User(
                        resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age")));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        String query = "delete from users";
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            Util.getConnection().close();
            if (Util.getConnection().isClosed()) {
                System.out.println("Соединение с БД завершено");
            }
        } catch (Exception e) {
            System.err.println("Ошибка закрытия БД");
        }
    }
}
