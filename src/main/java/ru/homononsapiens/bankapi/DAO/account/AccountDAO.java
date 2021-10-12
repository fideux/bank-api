package ru.homononsapiens.bankapi.DAO.account;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.homononsapiens.bankapi.DAO.DAO;
import ru.homononsapiens.bankapi.DAO.HibernateSessionFactory;
import ru.homononsapiens.bankapi.DAO.card.Card;

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

    public List<Card> findByNumber(String number) {
        return HibernateSessionFactory.getSessionFactory().openSession().createQuery("From Account where number = :number").setParameter("number", number).list();
    }

    @Override
    public void update(Account entity) {
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public boolean save(Account account) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(account);
            tx.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}