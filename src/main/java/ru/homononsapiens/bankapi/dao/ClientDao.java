package ru.homononsapiens.bankapi.dao;

import org.springframework.stereotype.Repository;
import ru.homononsapiens.bankapi.model.Client;
import ru.homononsapiens.bankapi.utils.HibernateSessionFactory;

import java.util.List;

@Repository
public class ClientDao extends AbstractDao<Client, Long> {

    @Override
    public Client get(Long id) {
        return HibernateSessionFactory.getSessionFactory().openSession()
                .get(Client.class, id);
    }

    @Override
    public List<Client> getAll() {
        return HibernateSessionFactory.getSessionFactory().openSession()
                .createQuery("From Client", Client.class).list();
    }
}
