package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserDao userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();

        userDao.saveUser("Svyatoslav", "Loginov", (byte) 25);
        userDao.saveUser("Polina", "Polyhina", (byte) 25);
        userDao.saveUser("Nastya", "Polyhina", (byte) 25);
        userDao.saveUser("Chypa", "Chyprigin", (byte) 25);

        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
