package ru.homononsapiens.bankapi.DAO;

import java.util.List;

public interface DAO<E, K> {
    public abstract List<E> findAll();
    public abstract E findById(K id);
    public abstract void update(E entity);
    public abstract void delete(K id);
    public abstract boolean save(E entity);
}
