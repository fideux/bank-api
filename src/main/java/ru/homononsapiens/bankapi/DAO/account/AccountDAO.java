package ru.homononsapiens.bankapi.DAO.account;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.homononsapiens.bankapi.DAO.DAO;
import ru.homononsapiens.bankapi.DAO.HibernateSessionFactory;

import java.util.List;

@Repository
public class AccountDAO implements DAO<Account, Long> {
    @Override
    public List<Account> findAll() {
        return HibernateSessionFactory.getSessionFactory().openSession().createQuery("From Account", Account.class).list();
    }

    @Override
    public Account findById(Long id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Account.class, id);
    }

    @Override
    public Account update(Account entity) {
        return null;
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public void save(Account account) {
    }

    public void put(Account account) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.update(account);

        tx.commit();
        session.close();
    }
}