package ru.homononsapiens.bankapi.DAO.account;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homononsapiens.bankapi.DAO.operation.Operation;
import ru.homononsapiens.bankapi.DAO.card.Card;
import ru.homononsapiens.bankapi.utils.Util;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {
    private AccountDAO accountDAO;

    public Account get(Long id) {
        return accountDAO.findById(id);
    }

    public List<Account> getAll() {
        return accountDAO.findAll();
    }

    public JsonNode checkBalance(Long id) {
        Account account = accountDAO.findById(id);
        return (account == null)
                ? Util.getMessageAsJsonObject("Error", "Счет не найден")
                : Util.getMessageAsJsonObject("Balance", account.getBalance().toString());
    }

    public JsonNode add(Account account) {
        String number;
        List<Card> list;

        do {
            number = Util.generateAccountNumber();
            list = accountDAO.findByNumber(number);
        } while (!list.isEmpty());

        account.setNumber(number);

        if (accountDAO.save(account)) {
            return Util.getMessageAsJsonObject("OK", "Счет успешно открыт");
        }
        return Util.getMessageAsJsonObject("Error", "Ошибка при создании счета");
    }
}
