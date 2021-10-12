package ru.homononsapiens.bankapi.DAO.card;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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

    public void save(Card card) {
        String number;
        List<Card> list;

        do {
            number = generateCardNumber();
            list = cardDAO.findByNumber(number);
            System.out.println(number);
        } while (!list.isEmpty());

        card.setNumber(number);
        cardDAO.save(card);
    }

    private String generateCardNumber() {
        return Long.toString((long) (Math.random() * (1_0000_0000_0000_0000L - 1000_0000_0000_0000L)));
    }
}
