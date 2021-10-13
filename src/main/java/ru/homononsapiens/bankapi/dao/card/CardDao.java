package ru.homononsapiens.bankapi.dao.card;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.homononsapiens.bankapi.dao.Dao;
import ru.homononsapiens.bankapi.dao.HibernateSessionFactory;

import java.util.List;

@Repository
public class CardDao implements Dao<Card, Long> {

    @Override
    public List<Card> findAll() {
        return HibernateSessionFactory.getSessionFactory().openSession().createQuery("From Card", Card.class).list();
    }

    public List<Card> findAllByClientId(Long clientId) {
        return HibernateSessionFactory.getSessionFactory().openSession()
                .createQuery("select c FROM Card AS c, Account AS a WHERE a.clientId = :clientId AND  a.id = c.accountId AND c.confirmed = true", Card.class)
                .setParameter("clientId", clientId).list();
    }

    @Override
    public Card findById(Long id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Card.class, id);
    }

    public List<Card> findByNumber(String number) {
        return HibernateSessionFactory.getSessionFactory().openSession().createQuery("from Card where number = :number").setParameter("number", number).list();
    }

    @Override
    public void update(Card card) {
    }

    public void confirm(Long id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.createQuery("update Card set confirmed = true where id = :id").setParameter("id", id).executeUpdate();
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public boolean save(Card card) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(card);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
