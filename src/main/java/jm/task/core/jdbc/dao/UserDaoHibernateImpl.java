package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String query = "CREATE TABLE IF NOT EXISTS users(id INT AUTO_INCREMENT, name VARCHAR(100) NOT NULL , lastName VARCHAR(100) NOT NULL , age TINYINT NOT NULL , CONSTRAINT id PRIMARY KEY (id));";
        session.createSQLQuery(query).executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String query = "DROP TABLE IF EXISTS users;";
        session.createSQLQuery(query).executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String query = "INSERT INTO users (name, lastName, age) VALUES (:name, :lastName, :age)";
        session.createSQLQuery(query)
                .setParameter("name", name)
                .setParameter("lastName", lastName)
                .setParameter("age", age)
                .executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String query = "DELETE FROM users WHERE id = :id";
        session.createSQLQuery(query)
                .setParameter("id", id)
                .executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM User", User.class);
        List<User> list = query.list();
        session.close();
        return list;
    }

    @Override
    public void cleanUsersTable() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String query = "DELETE FROM users";
        session.createSQLQuery(query)
                .executeUpdate();
        transaction.commit();
        session.close();
    }
}
