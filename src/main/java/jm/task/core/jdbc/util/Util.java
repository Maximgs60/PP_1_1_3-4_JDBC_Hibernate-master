package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/mytestbd";
    // реализуйте настройку соеденения с БД

    //JDBC
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD);
            System.out.println("Успешное подключение JDBC");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //Hibernate
    public static SessionFactory getSessionFactory() {
//        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
//        Configuration configuration = new Configuration()
//                .setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect")
//                .setProperty("hibernate.connection.username", USER_NAME)
//                .setProperty("hibernate.connection.password", PASSWORD)
//                .setProperty("hibernate.connection.url", CONNECTION_URL)
//                .configure();
        SessionFactory sessionFactory = null;
        try {

            Configuration configuration = new Configuration();
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL, "jdbc:mysql://localhost:3306/mytestbd");
            settings.put(Environment.USER, "root");
            settings.put(Environment.PASS, "root");
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            settings.put(Environment.HBM2DDL_AUTO, "create-drop");
            configuration.setProperties(settings);
            configuration.addAnnotatedClass(User.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            System.out.println("Успешное подключение Hibernate");
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }
}
