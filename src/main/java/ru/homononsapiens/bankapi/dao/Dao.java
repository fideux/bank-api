package ru.homononsapiens.bankapi.dao;

import java.util.List;

public interface Dao<E, K> {

    List<E> findAll();

    E findById(K id);

    void update(E entity);

    void delete(K id);

    boolean save(E entity);
}
