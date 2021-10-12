package ru.homononsapiens.bankapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.homononsapiens.bankapi.DAO.card.Card;
import ru.homononsapiens.bankapi.DAO.card.CardService;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("client/card")
@AllArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping(path = "list")
    public List<Card> list() {
        return cardService.findAll();
    }

    @GetMapping(path = "get/{cardId}")
    public Card get(@PathVariable("cardId") Long id) {
        return cardService.get(id);
    }

    @PostMapping(path = "add")
    public void add(@RequestBody Card card) {
        cardService.save(card);
    }
}
