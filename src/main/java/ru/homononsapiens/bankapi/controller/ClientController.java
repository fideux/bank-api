package ru.homononsapiens.bankapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.homononsapiens.bankapi.DAO.account.Account;
import ru.homononsapiens.bankapi.DAO.account.AccountService;
import ru.homononsapiens.bankapi.DAO.card.Card;
import ru.homononsapiens.bankapi.DAO.card.CardService;
import ru.homononsapiens.bankapi.DAO.operation.Operation;
import ru.homononsapiens.bankapi.DAO.operation.OperationService;

import java.util.List;

@RestController
@RequestMapping("api/client")
@AllArgsConstructor
public class ClientController {

    private final AccountService accountService;
    private final CardService cardService;
    private final OperationService operationService;

    @PostMapping(path = "card/list")
    public List<Card> getCards(@RequestBody Card card) {
        return cardService.getAllByAccountId(card.getAccountId());
    }

    @GetMapping(path = "card/get/{cardId}")
    public Card getCardById(@PathVariable("cardId") Long cardId) {
        return cardService.get(cardId);
    }

    @PostMapping(path = "card/add")
    public JsonNode addCard(@RequestBody Card card) {
        return cardService.add(card);
    }

    @PostMapping(path = "account/balance")
    public JsonNode getAccountBalance(@RequestBody Account account) {
        return accountService.checkBalance(account.getId());
    }

    @PutMapping(path = "account/put")
    public void topUpBalance(@RequestBody Operation operation) {
        operationService.add(operation);
    }
}
