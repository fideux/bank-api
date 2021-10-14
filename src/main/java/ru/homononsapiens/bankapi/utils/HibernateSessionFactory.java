package ru.homononsapiens.bankapi.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.homononsapiens.bankapi.model.Account;
import ru.homononsapiens.bankapi.model.Card;
import ru.homononsapiens.bankapi.model.Client;
import ru.homononsapiens.bankapi.model.Partner;
import ru.homononsapiens.bankapi.model.Payment;
import ru.homononsapiens.bankapi.model.Refill;

public class HibernateSessionFactory {

    private static SessionFactory sessionFactory;

    private HibernateSessionFactory() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Card.class);
                configuration.addAnnotatedClass(Account.class);
                configuration.addAnnotatedClass(Refill.class);
                configuration.addAnnotatedClass(Client.class);
                configuration.addAnnotatedClass(Partner.class);
                configuration.addAnnotatedClass(Payment.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}