package ru.homononsapiens.bankapi.dao;

import org.springframework.stereotype.Repository;
import ru.homononsapiens.bankapi.model.Partner;
import ru.homononsapiens.bankapi.utils.HibernateSessionFactory;

import java.util.List;

@Repository
public class PartnerDao extends AbstractDao<Partner, Long> {

    @Override
    public List<Partner> getAll() {
        return HibernateSessionFactory.getSessionFactory().openSession().createQuery("From Partner", Partner.class).list();
    }

    public List<Partner> findAllByClientId(Long clientId) {
        return HibernateSessionFactory.getSessionFactory().openSession()
                .createQuery("From Partner where clientId = :clientId", Partner.class)
                .setParameter("clientId", clientId).list();
    }
}
