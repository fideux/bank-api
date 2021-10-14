package ru.homononsapiens.bankapi.dao;

import java.util.List;

public interface Dao<E, K> {

    List<E> getAll();

    E get(K id);

    boolean update(E entity);

    boolean delete(K id);

    Long save(E entity);
}
