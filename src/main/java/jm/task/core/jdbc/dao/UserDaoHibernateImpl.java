package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        this.sessionFactory = Util.getSessionFactory();
    }

    @Override
    public void createUsersTable() {
        try (Session session = this.sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String query = "CREATE TABLE IF NOT EXISTS users(id INT AUTO_INCREMENT, name VARCHAR(100) NOT NULL , lastName VARCHAR(100) NOT NULL , age TINYINT NOT NULL , CONSTRAINT id PRIMARY KEY (id));";
            session.createSQLQuery(query).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = this.sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String query = "DROP TABLE IF EXISTS users;";
            session.createSQLQuery(query).executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = this.sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String query = "INSERT INTO users (name, lastName, age) VALUES (:name, :lastName, :age)";
            session.createSQLQuery(query)
                    .setParameter("name", name)
                    .setParameter("lastName", lastName)
                    .setParameter("age", age)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = this.sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String query = "DELETE FROM users WHERE id = :id";
            session.createSQLQuery(query)
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = this.sessionFactory.openSession()) {
            Query query = session.createQuery("FROM User", User.class);
            List<User> list = query.list();
            session.close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = this.sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String query = "DELETE FROM users";
            session.createSQLQuery(query)
                    .executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
