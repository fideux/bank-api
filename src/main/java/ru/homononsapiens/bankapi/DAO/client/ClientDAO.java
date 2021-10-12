package ru.homononsapiens.bankapi.DAO.client;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.homononsapiens.bankapi.DAO.DAO;
import ru.homononsapiens.bankapi.DAO.HibernateSessionFactory;
import ru.homononsapiens.bankapi.DAO.card.Card;

import java.util.List;

@Repository
public class ClientDAO implements DAO<Client, Long> {
    @Override
    public List<Client> findAll() {
        return HibernateSessionFactory.getSessionFactory().openSession().createQuery("From Client", Client.class).list();
    }

    @Override
    public Client findById(Long id) {
        return null;
    }

    @Override
    public void update(Client client) {
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public boolean save(Client client) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(client);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
