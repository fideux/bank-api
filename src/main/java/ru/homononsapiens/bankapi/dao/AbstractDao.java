package ru.homononsapiens.bankapi.dao;

import org.hibernate.Session;
import ru.homononsapiens.bankapi.utils.HibernateSessionFactory;

public abstract class AbstractDao<E, K> implements Dao<E, K> {

    @Override
    public Long save(E entity) {
        Long id = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            id = (Long) session.save(entity);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public E get(K id) {
        return null;
    }

    @Override
    public boolean delete(K id) {
        return false;
    }

    @Override
    public boolean update(E entity) {
        return false;
    }
}
