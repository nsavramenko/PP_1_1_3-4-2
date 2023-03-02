package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static Transaction transaction;
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(new StringBuilder().append("CREATE TABLE IF NOT EXISTS users12")
                    .append("(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,")
                    .append("name VARCHAR(65) NOT NULL, lastname VARCHAR (65) NOT NULL,")
                    .append("age TINYINT NOT NULL)")
                    .toString())
                    .addEntity(User.class)
                    .addEntity(User.class)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction= session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users12").addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User deleteUser = session.get(User.class, id);
            session.delete(deleteUser);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            result = session.createQuery("FROM User").getResultList();
            for (User e : result) {
                System.out.println(e);
            }
            transaction.commit();
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users12").addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
