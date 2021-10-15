package ru.homononsapiens.bankapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homononsapiens.bankapi.dao.AccountDao;
import ru.homononsapiens.bankapi.dao.ClientDao;
import ru.homononsapiens.bankapi.model.Account;
import ru.homononsapiens.bankapi.utils.Util;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {

    private AccountDao accountDao;
    private ClientDao clientDao;

    public Account get(Long id) {
        return accountDao.get(id);
    }

    public List<Account> getAll() {
        return accountDao.getAll();
    }

    public BigDecimal getBalance(Long id) {
        Account account = accountDao.get(id);
        return (account != null) ? accountDao.get(id).getBalance() : null;
    }

    public Long add(Account account) {

        // Проверяем, что сумма начального баланса положительная
        if (account.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Сумма отрицательная или равна нулю");
            return null;
        }

        // Проверяем, есть ли такой клиент
        if (clientDao.get(account.getClientId()) == null) {
            System.out.println("Клиент не найден");
            return null;
        }

        // Генерация уникального номера счета
        String number;
        do {
            number = Util.generateAccountNumber();
        } while (accountDao.isExistsByNumber(number));

        account.setNumber(number);
        return accountDao.save(account);
    }
}
