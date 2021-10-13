package ru.homononsapiens.bankapi.dao.payment;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.homononsapiens.bankapi.dao.Dao;
import ru.homononsapiens.bankapi.dao.HibernateSessionFactory;
import java.util.List;

@Repository
public class PaymentDao implements Dao<Payment, Long> {

    @Override
    public List<Payment> findAll() {
        return HibernateSessionFactory.getSessionFactory().openSession().createQuery("From Payment", Payment.class).list();
    }

    @Override
    public Payment findById(Long id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Payment.class, id);
    }

    @Override
    public void update(Payment payment) {
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public boolean save(Payment payment) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(payment);
        tx.commit();
        session.close();

        return true;
    }

    public void confirm(Long id) {
        Payment payment = findById(id);

        if (payment == null || !payment.getStatus().equals("waiting")) {
            return;
        }

        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        session.createQuery("update Payment set status = 'confirmed' where id = :id").setParameter("id", id).executeUpdate();
        session.createQuery("update Account set balance = balance - :amount where id = :id")
                .setParameter("amount", payment.getAmount())
                .setParameter("id", payment.getAccountId())
                .executeUpdate();

        tx1.commit();
        session.close();
    }
}