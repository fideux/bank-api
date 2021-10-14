package ru.homononsapiens.bankapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homononsapiens.bankapi.dao.AccountDao;
import ru.homononsapiens.bankapi.model.Account;
import ru.homononsapiens.bankapi.model.Payment;
import ru.homononsapiens.bankapi.dao.PaymentDao;
import ru.homononsapiens.bankapi.utils.Util;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentService {

    private PaymentDao paymentDao;
    private AccountDao accountDao;

    public List<Payment> getAll() {
        return paymentDao.getAll();
    }

    public Long add(Payment payment) {
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