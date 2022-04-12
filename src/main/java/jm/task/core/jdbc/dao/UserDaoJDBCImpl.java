package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();
    private String sqlCommand;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        sqlCommand = "CREATE TABLE Users (" +
                "id MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name CHAR(20) NOT NULL, " +
                "lastName CHAR(20) NOT NULL, " +
                "age TINYINT UNSIGNED NOT NULL)";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlCommand);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        sqlCommand = "DROP TABLE Users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        sqlCommand = "INSERT INTO Users (name, lastName, age) VALUES (?,?,?)";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sqlCommand)) {
            prepareStatement.setString(1, name);
            prepareStatement.setString(2, lastName);
            prepareStatement.setByte(3, age);
            prepareStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        sqlCommand = "DELETE FROM Users WHERE id = ?";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sqlCommand)) {
            prepareStatement.setLong(1, id);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        User user = new User();
        sqlCommand = "SELECT * FROM Users";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                System.out.println(user);
                usersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public void cleanUsersTable() {
        sqlCommand = "TRUNCATE TABLE Users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
