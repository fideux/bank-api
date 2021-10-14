package ru.homononsapiens.bankapi.dao;

import org.springframework.stereotype.Repository;
import ru.homononsapiens.bankapi.model.Account;
import ru.homononsapiens.bankapi.utils.HibernateSessionFactory;

import java.util.List;

@Repository
public class AccountDao extends AbstractDao<Account, Long> {

    @Override
    public List<Account> getAll() {
        return HibernateSessionFactory.getSessionFactory().openSession()
                .createQuery("From Account", Account.class).list();
    }

    @Override
    public Account get(Long id) {
        return HibernateSessionFactory.getSessionFactory().openSession()
                .get(Account.class, id);
    }

    public boolean isExistsByNumber(String number) {
        List<Account> accounts = HibernateSessionFactory.getSessionFactory().openSession()
                .createQuery("From Account where number = :number").setParameter("number", number).list();
        return (accounts.isEmpty()) ? false : true;
    }
}