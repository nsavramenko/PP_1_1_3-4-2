package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    PreparedStatement preparedStatement = null;

    public void createUsersTable() throws SQLException {
        String createTable = "CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT, user_name VARCHAR(45), user_lastname VARCHAR(60), user_age INT);";
        try {
            preparedStatement = Util.getConnection().prepareStatement(createTable);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (Util.getConnection() != null) {
                Util.getConnection().close();
            }
        }
    }

    public void dropUsersTable() throws SQLException {
        String dropTable = "DROP TABLE IF EXISTS users;";
        try {
            preparedStatement = Util.getConnection().prepareStatement(dropTable);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (Util.getConnection() != null) {
                Util.getConnection().close();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String saveUser = "INSERT INTO users (user_name, user_lastname, user_age) VALUES (?, ?, ?);";
        try {
            preparedStatement = Util.getConnection().prepareStatement(saveUser);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (Util.getConnection() != null) {
                Util.getConnection().close();
            }
        }
    }

    public void removeUserById(long id) throws SQLException {
        String removeUser = "DELETE FROM users WHERE id=?;";
        try {
            preparedStatement = Util.getConnection().prepareStatement(removeUser);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (Util.getConnection() != null) {
                Util.getConnection().close();
            }
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        Statement statement;
        ResultSet resultSet = null;

        String getUsers = "SELECT * FROM users;";
        try {
            statement = Util.getConnection().createStatement();
            resultSet = statement.executeQuery(getUsers);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("user_name"));
                user.setLastName(resultSet.getString("user_lastname"));
                user.setAge(resultSet.getByte("user_age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (Util.getConnection() != null) {
                Util.getConnection().close();
            }
        }
        return userList;
    }

    public void cleanUsersTable() throws SQLException {
        String truncateTable = "TRUNCATE users;";
        try {
            preparedStatement = Util.getConnection().prepareStatement(truncateTable);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (Util.getConnection() != null) {
                Util.getConnection().close();
            }
        }
    }
}
