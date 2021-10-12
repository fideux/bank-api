package ru.homononsapiens.bankapi.DAO.card;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.homononsapiens.bankapi.DAO.DAO;
import ru.homononsapiens.bankapi.DAO.HibernateSessionFactory;

import java.util.List;

@Repository
public class CardDAO implements DAO<Card, Long> {
    @Override
    public List<Card> findAll() {
        return HibernateSessionFactory.getSessionFactory().openSession().createQuery("From Card", Card.class).list();
    }

    @Override
    public Card findById(Long id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Card.class, id);
    }

    public List<Card> findByNumber(String number) {
        return HibernateSessionFactory.getSessionFactory().openSession().createQuery("From Card where number = :number").setParameter("number", number).list();
    }

    @Override
    public Card update(Card entity) {
        return null;
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public void save(Card card) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.save(card);

        tx.commit();
        session.close();
    }
}
