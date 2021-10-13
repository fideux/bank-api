package ru.homononsapiens.bankapi.dao.card;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homononsapiens.bankapi.utils.Util;

import java.util.List;

@Service
@AllArgsConstructor
public class CardService {

    private CardDao cardDao;

    public Card get(Long id) {
        return cardDao.findById(id);
    }

    public List<Card> getAll() {
        return cardDao.findAll();
    }

    public List<Card> getAllByClientId(Long clientId) {
        return cardDao.findAllByClientId(clientId);
    }

    public JsonNode add(Card card) {
        String number;
        List<Card> list;

        do {
            number = Util.generateCardNumber();
            list = cardDao.findByNumber(number);
        } while (!list.isEmpty());

        card.setNumber(number);
        card.setConfirmed(false);

        if (cardDao.save(card)) {
            return Util.getMessageAsJsonObject("OK", "Карта успешно создана");
        }

        return Util.getMessageAsJsonObject("Error", "Ошибка при создании карты. Счет не найден");
    }

    public void confirm(Card card) {
        cardDao.confirm(card.getId());
    }
}
