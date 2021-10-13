package ru.homononsapiens.bankapi.dao.refill;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.homononsapiens.bankapi.dao.Dao;
import ru.homononsapiens.bankapi.dao.HibernateSessionFactory;
import java.util.List;

@Repository
public class RefillDao implements Dao<Refill, Long> {

    @Override
    public List<Refill> findAll() {
        return HibernateSessionFactory.getSessionFactory().openSession().createQuery("From Refill", Refill.class).list();
    }

    @Override
    public Refill findById(Long id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Refill.class, id);
    }

    @Override
    public void update(Refill refill) {
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public boolean save(Refill refill) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(refill);
        tx.commit();
        session.close();

        return true;
    }

    public void confirm(Long id) {
        Refill refill = findById(id);

        if (refill == null || !refill.getStatus().equals("waiting")) {
            return;
        }

        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        session.createQuery("update Refill set status = 'confirmed' where id = :id").setParameter("id", id).executeUpdate();
        session.createQuery("update Account set balance = balance + :amount where id = :id")
                .setParameter("amount", refill.getAmount())
                .setParameter("id", refill.getAccountId())
                .executeUpdate();

        tx1.commit();
        session.close();
    }
}