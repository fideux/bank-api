package ru.homononsapiens.bankapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homononsapiens.bankapi.dao.AccountDao;
import ru.homononsapiens.bankapi.dao.ClientDao;
import ru.homononsapiens.bankapi.dao.PartnerDao;
import ru.homononsapiens.bankapi.model.Account;
import ru.homononsapiens.bankapi.model.Payment;
import ru.homononsapiens.bankapi.dao.PaymentDao;
import ru.homononsapiens.bankapi.utils.Util;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentService {

    private PaymentDao paymentDao;
    private AccountDao accountDao;
    private PartnerDao partnerDao;
    private ClientDao clientDao;

    public List<Payment> getAll() {
        return paymentDao.getAll();
    }

    public Long add(Payment payment) {

        // Проверяем, что сумма перевода положительная
        if (payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Сумма отрицательная или равна нулю");
            return null;
        }

        // Проверяем, есть ли такой счет
        if (accountDao.get(payment.getAccountId()) == null) {
            System.out.println("Счет не найден");
            return null;
        }
        // Проверяем, есть ли такой контрагент
        if (partnerDao.get(payment.getPartnerId()) == null) {
            System.out.println("Контрагент не найден");
            return null;
        }

        Account account = accountDao.get(payment.getAccountId());
        if (account == null || account.getBalance().compareTo(payment.getAmount()) < 0) {
            return null;
        }
        return paymentDao.save(payment);
    }

    public boolean confirm(Long paymentId) {
        return paymentDao.confirm(paymentId);
    }
}