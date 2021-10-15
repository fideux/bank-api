package ru.homononsapiens.bankapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homononsapiens.bankapi.dao.AccountDao;
import ru.homononsapiens.bankapi.dao.ClientDao;
import ru.homononsapiens.bankapi.model.Refill;
import ru.homononsapiens.bankapi.dao.RefillDao;
import ru.homononsapiens.bankapi.utils.Util;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class RefillService {

    private RefillDao refillDao;
    private AccountDao accountDao;

    public List<Refill> getAll() {
        return refillDao.getAll();
    }

    public Long add(Refill refill) {
        // Проверяем, что сумма пополнения положительная
        if (refill.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Сумма отрицательная или равна нулю");
            return null;
        }
        // Проверяем, есть ли такой счет
        if (accountDao.get(refill.getAccountId()) == null) {
            System.out.println("Счет не найден");
            return null;
        }

        return refillDao.save(refill);
    }

    public boolean confirm(Long id) {
        return refillDao.confirm(id);
    }
}