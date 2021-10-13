package ru.homononsapiens.bankapi.dao.partner;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.homononsapiens.bankapi.dao.Dao;
import ru.homononsapiens.bankapi.dao.HibernateSessionFactory;
import ru.homononsapiens.bankapi.dao.card.Card;

import java.util.List;

@Repository
public class PartnerDao implements Dao<Partner, Long> {

    @Override
    public List<Partner> findAll() {
        return HibernateSessionFactory.getSessionFactory().openSession().createQuery("From Partner", Partner.class).list();
    }

    @Override
    public Partner findById(Long id) {
        return null;
    }

    public List<Partner> findAllByClientId(Long clientId) {
        return HibernateSessionFactory.getSessionFactory().openSession()
                .createQuery("From Partner where clientId = :clientId", Partner.class)
                .setParameter("clientId", clientId).list();
    }

    @Override
    public void update(Partner Partner) {
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public boolean save(Partner partner) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(partner);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
