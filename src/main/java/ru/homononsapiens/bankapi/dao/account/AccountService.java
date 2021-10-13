package ru.homononsapiens.bankapi.dao.account;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homononsapiens.bankapi.dao.card.Card;
import ru.homononsapiens.bankapi.utils.Util;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {

    private AccountDao accountDao;

    public Account get(Long id) {
        return accountDao.findById(id);
    }

    public List<Account> getAll() {
        return accountDao.findAll();
    }

    public JsonNode checkBalance(Long id) {
        Account account = accountDao.findById(id);
        return (account == null)
                ? Util.getMessageAsJsonObject("Error", "Счет не найден")
                : Util.getMessageAsJsonObject("Balance", account.getBalance().toString());
    }

    public JsonNode add(Account account) {
        String number;
        List<Card> list;

        do {
            number = Util.generateAccountNumber();
            list = accountDao.findByNumber(number);
        } while (!list.isEmpty());

        account.setNumber(number);

        if (accountDao.save(account)) {
            return Util.getMessageAsJsonObject("OK", "Счет успешно открыт");
        }
        return Util.getMessageAsJsonObject("Error", "Ошибка при создании счета");
    }
}
