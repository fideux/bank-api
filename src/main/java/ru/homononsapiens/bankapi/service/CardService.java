package ru.homononsapiens.bankapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homononsapiens.bankapi.model.Card;
import ru.homononsapiens.bankapi.dao.CardDao;
import ru.homononsapiens.bankapi.utils.Util;

import java.util.List;

@Service
@AllArgsConstructor
public class CardService {

    private CardDao cardDao;

    public Card get(Long id) {
        return cardDao.get(id);
    }

    public List<Card> getAll() {
        return cardDao.getAll();
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
            return Util.getMessageAsJsonObject("OK", "Запрос на выпуск карты создан. Ожидайте подтверждения");
        }
        return Util.getMessageAsJsonObject("Error", "Ошибка при выпуске карты");
    }

    public JsonNode confirm(Card card) {
        if (cardDao.confirm(card.getId())) {
            return Util.getMessageAsJsonObject("OK", "Карта успешно выпущена");
        }
        return Util.getMessageAsJsonObject("Error", "Ошибка при выпуске карты");
    }
}
