package ru.homononsapiens.bankapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homononsapiens.bankapi.dao.AccountDao;
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

    public JsonNode add(Payment payment) {
        payment.setStatus("waiting");

        if (accountDao.get(payment.getAccountId()).getBalance().compareTo(payment.getAmount()) < 0) {
            return Util.getMessageAsJsonObject("Error", "Недостаточно денег на счете. Пожалуйста, пополните баланс");
        }

        if (paymentDao.save(payment)) {
            return Util.getMessageAsJsonObject("OK", "Запрос на платеж успешно создан. Ожидайте подтверждения");
        }
        return Util.getMessageAsJsonObject("Error", "Ошибка при создании запроса на платеж");
    }

    public JsonNode confirm(Payment payment) {
        if (paymentDao.confirm(payment.getId())) {
            return Util.getMessageAsJsonObject("OK", "Платеж успешно подтвержден");
        }
        return Util.getMessageAsJsonObject("Error", "Ошибка при подтверждении платежа");
    }
}