package ru.homononsapiens.bankapi.DAO.card;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homononsapiens.bankapi.DAO.client.Client;
import ru.homononsapiens.bankapi.utils.Util;

import java.util.List;

@Service
@AllArgsConstructor
public class CardService {
    private CardDAO cardDAO;

    public Card get(Long id) {
        return cardDAO.findById(id);
    }

    public List<Card> findAll() {
        return cardDAO.findAll();
    }

    public List<Card> findAllByAccountId(Long accountId) {
        return cardDAO.findAllByAccountId(accountId);
    }

    public JsonNode save(Card card) {
        String number;
        List<Card> list;

        do {
            number = Util.generateCardNumber();
            list = cardDAO.findByNumber(number);
        } while (!list.isEmpty());

        card.setNumber(number);
        card.setConfirmed(false);

        if (cardDAO.save(card)) {
            return Util.getMessageAsJsonObject("OK", "Карта успешно создана");
        }

        return Util.getMessageAsJsonObject("Error", "Ошибка при создании карты. Счет не найден");
    }

    public void confirm(Card card) {
        cardDAO.confirm(card.getId());
    }
}
