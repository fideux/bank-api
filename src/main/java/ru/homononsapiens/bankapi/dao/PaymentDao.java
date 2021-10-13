package ru.homononsapiens.bankapi.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.homononsapiens.bankapi.model.Payment;

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
        Payment payment = this.get(id);
        if (payment == null || !payment.getStatus().equals("waiting")) {
            return false;
        }

        int result = 0;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("update Payment set status = 'confirmed' where id = :id").setParameter("id", id).executeUpdate();
            result = session.createQuery("update Account set balance = balance - :amount where id = :id and balance - :amount >= 0")
                    .setParameter("amount", payment.getAmount())
                    .setParameter("id", payment.getAccountId())
                    .executeUpdate();
            session.getTransaction().commit();
        }
        return (result > 0) ? true : false;
    }
}