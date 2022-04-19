package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();
    private String sqlCommand;
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        sqlCommand = "CREATE TABLE Users (" +
                "id MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name CHAR(20) NOT NULL, " +
                "lastName CHAR(20) NOT NULL, " +
                "age TINYINT UNSIGNED NOT NULL)";
        try {
            session.beginTransaction();
            session.createSQLQuery(sqlCommand).addEntity(User.class);
            session.getTransaction().commit();
            System.out.println("Таблица User создана");
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            System.out.println("ОТКАТ создания таблицы User");
        } finally {
            session.close();
            System.out.println("Закрываем сессию");
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        sqlCommand = "DROP TABLE Users";
        try {
            session.beginTransaction();
            session.createSQLQuery(sqlCommand);
            session.getTransaction().commit();
            System.out.println("Таблицы User удалена");
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            System.out.println("ОТКАТ удаления таблицы User");
        } finally {
            session.close();
            System.out.println("Закрываем сессию");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.getCurrentSession();
        try {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            System.out.println("ОТКАТ добавления юзера");
        } finally {
            session.close();
            System.out.println("Закрываем сессию");
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            System.out.println("User с номером - " + id + " удален из базы данных");
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            System.out.println("ОТКАТ удаления юзера");
        } finally {
            session.close();
            System.out.println("Закрываем сессию");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            userList = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
            System.out.println("Все User'ы получены ");
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            System.out.println("ОТКАТ удаления юзера");
        } finally {
            session.close();
            System.out.println("Закрываем сессию");
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица User очищена");
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            System.out.println("ОТКАТ очистки таблицы");
        } finally {
            session.close();
            System.out.println("Закрываем сессию");
        }
    }
}
