package ru.homononsapiens.bankapi.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.homononsapiens.bankapi.model.Payment;
import ru.homononsapiens.bankapi.utils.HibernateSessionFactory;

import java.util.List;

@Repository
public class PaymentDao extends AbstractDao<Payment, Long> {

    @Override
    public List<Payment> getAll() {
        return HibernateSessionFactory.getSessionFactory().openSession().createQuery("From Payment", Payment.class).list();
    }

    @Override
    public Payment get(Long id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Payment.class, id);
    }

    public boolean confirm(Long id) {
        int result = 0;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            result = session.createQuery("update Payment set status = 'confirmed' where id = :id and status = 'waiting'")
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        }
        return result > 0 ? true : false;
    }

    public Long save(Payment payment) {
        Long id = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            int result = session.createQuery("update Account set balance = balance - :amount where id = :accountId")
                    .setParameter("amount", payment.getAmount())
                    .setParameter("accountId", payment.getAccountId())
                    .executeUpdate();
            if (result == 1) {
                id = (Long) session.save(payment);
                session.getTransaction().commit();
            } else {
                session.getTransaction().rollback();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return id;
    }
}