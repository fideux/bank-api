package ru.homononsapiens.bankapi.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.homononsapiens.bankapi.model.Refill;

import java.util.List;

@Repository
public class RefillDao extends AbstractDao<Refill, Long> {

    @Override
    public List<Refill> getAll() {
        return HibernateSessionFactory.getSessionFactory().openSession().createQuery("From Refill", Refill.class).list();
    }

    @Override
    public Refill get(Long id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Refill.class, id);
    }

    public boolean confirm(Long id) {
        Refill refill = this.get(id);
        if (refill == null || !refill.getStatus().equals("waiting")) {
            return false;
        }

        int result = 0;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("update Refill set status = 'confirmed' where id = :id").setParameter("id", id).executeUpdate();
            result = session.createQuery("update Account set balance = balance + :amount where id = :id")
                    .setParameter("amount", refill.getAmount())
                    .setParameter("id", refill.getAccountId())
                    .executeUpdate();
            session.getTransaction().commit();
        }
        return (result > 0) ? true : false;
    }
}