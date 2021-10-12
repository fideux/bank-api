package ru.homononsapiens.bankapi.DAO.operation;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.homononsapiens.bankapi.DAO.DAO;
import ru.homononsapiens.bankapi.DAO.HibernateSessionFactory;

import java.util.List;

@Repository
public class OperationDAO implements DAO<Operation, Long> {
    @Override
    public List<Operation> findAll() {
        return HibernateSessionFactory.getSessionFactory().openSession().createQuery("From Operation", Operation.class).list();
    }

    @Override
    public Operation findById(Long id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Operation.class, id);
    }

    @Override
    public void update(Operation operation) {
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public boolean save(Operation operation) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(operation);
        tx.commit();
        session.close();

        return true;
    }

    public void confirm(Long id) {
        Operation operation = findById(id);

        if (operation == null || !operation.getStatus().equals("waiting")) {
            return;
        }

        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        session.createQuery("update Operation set status = 'confirmed' where id = :id").setParameter("id", id).executeUpdate();
        session.createQuery("update Account set balance = balance + :amount where id = :id")
                .setParameter("amount", operation.getAmount())
                .setParameter("id", operation.getAccountId())
                .executeUpdate();

        tx1.commit();
        session.close();
    }
}