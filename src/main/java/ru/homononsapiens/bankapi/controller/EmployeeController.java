package ru.homononsapiens.bankapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.homononsapiens.bankapi.dao.account.Account;
import ru.homononsapiens.bankapi.dao.account.AccountService;
import ru.homononsapiens.bankapi.dao.card.Card;
import ru.homononsapiens.bankapi.dao.card.CardService;
import ru.homononsapiens.bankapi.dao.client.Client;
import ru.homononsapiens.bankapi.dao.client.ClientService;
import ru.homononsapiens.bankapi.dao.refill.Refill;
import ru.homononsapiens.bankapi.dao.refill.RefillService;

import java.util.List;

@RestController
@RequestMapping("api/employee/client")
@AllArgsConstructor
public class EmployeeController {

    private final ClientService clientService;
    private final AccountService accountService;
    private final CardService cardService;
    private final RefillService refillService;

    @PutMapping(path = "add")
    public JsonNode list(@RequestBody Client client) {
        return clientService.save(client);
    }

    @PutMapping(path = "account/open")
    public JsonNode open(@RequestBody Account account) {
        return accountService.add(account);
    }

    @PostMapping(path = "card/confirm")
    public void cardConfirm(@RequestBody Card card) {
        cardService.confirm(card);
    }

    @PostMapping(path = "operation/confirm")
    public void operationConfirm(@RequestBody Refill refill) {
        refillService.confirm(refill);
    }

    @GetMapping(path = "list")
    public List<Client> list() {
        return clientService.findAll();
    }

    @GetMapping(path = "card/list")
    public List<Card> cardList() {
        return cardService.getAll();
    }

    @GetMapping(path = "operation/list")
    public List<Refill> operationsList() {
        return refillService.getAll();
    }
}
