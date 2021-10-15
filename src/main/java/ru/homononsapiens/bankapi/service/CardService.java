package ru.homononsapiens.bankapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homononsapiens.bankapi.dao.AccountDao;
import ru.homononsapiens.bankapi.dao.ClientDao;
import ru.homononsapiens.bankapi.model.Card;
import ru.homononsapiens.bankapi.dao.CardDao;
import ru.homononsapiens.bankapi.utils.Util;

import java.util.List;

@Service
@AllArgsConstructor
public class CardService {

    private CardDao cardDao;
    private AccountDao accountDao;
    private ClientDao clientDao;

    public Card get(Long id) {
        return cardDao.get(id);
    }

    public List<Card> getAll() {
        return cardDao.getAll();
    }

    public List<Card> getAllByClientId(Long clientId) {
        // Проверяем, есть ли такой клиент
        if (clientDao.get(clientId) == null) {
            System.out.println("Клиент не найден");
            return null;
        }
        return cardDao.findAllByClientId(clientId);
    }

    public Long add(Long accountId) {
        // Проверяем, есть ли такой счет
        if (accountDao.get(accountId) == null) {
            System.out.println("Счет не найден");
            return null;
        }

        // Генерация уникального номера карты
        String number;
        do {
            number = Util.generateCardNumber();
        } while (cardDao.isExistsByNumber(number));

        return cardDao.save(new Card(number, accountId));
    }

    public boolean confirm(Long cardId) {
        return cardDao.confirm(cardId);
    }
}
