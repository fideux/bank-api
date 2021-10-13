package ru.homononsapiens.bankapi.dao;

import org.hibernate.Session;

public abstract class AbstractDao<E, K> implements Dao<E, K> {

    @Override
    public boolean save(E entity) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
        return true;
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
