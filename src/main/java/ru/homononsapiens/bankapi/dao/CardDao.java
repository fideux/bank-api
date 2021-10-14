package ru.homononsapiens.bankapi.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.homononsapiens.bankapi.model.Card;
import ru.homononsapiens.bankapi.utils.HibernateSessionFactory;

import java.util.List;

@Repository
public class CardDao extends AbstractDao<Card, Long> {

    @Override
    public List<Card> getAll() {
        return HibernateSessionFactory.getSessionFactory().openSession()
                .createQuery("From Card", Card.class).list();
    }

    public List<Card> findAllByClientId(Long clientId) {
        return HibernateSessionFactory.getSessionFactory().openSession()
                .createQuery("select c FROM Card AS c, Account AS a WHERE a.clientId = :clientId AND  a.id = c.accountId AND c.confirmed = true", Card.class)
                .setParameter("clientId", clientId).list();
    }

    @Override
    public Card get(Long id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Card.class, id);
    }

    public boolean isExistsByNumber(String number) {
        List<Card> cards = HibernateSessionFactory.getSessionFactory().openSession().createQuery("from Card where number = :number").setParameter("number", number).getResultList();
        return (cards.isEmpty()) ? false : true;
    }


    public boolean confirm(Long id) {
        int result = 0;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            result = session.createQuery("update Card set confirmed = true where id = :id").setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
        }
        return (result > 0) ? true : false;
    }
}
