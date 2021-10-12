package ru.homononsapiens.bankapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.homononsapiens.bankapi.DAO.card.Card;
import ru.homononsapiens.bankapi.DAO.card.CardService;

import java.util.List;

@RestController
@RequestMapping("api/client/card")
@AllArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping(path = "list")
    public List<Card> list(@RequestBody Card card) {
        System.out.println(card);
        return cardService.findAllByAccountId(card.getAccountId());
    }

    @GetMapping(path = "get/{cardId}")
    public Card get(@PathVariable("cardId") Long cardId) {
        return cardService.get(cardId);
    }

    @PostMapping(path = "add")
    public JsonNode add(@RequestBody Card card) {
        return cardService.save(card);
    }
}
