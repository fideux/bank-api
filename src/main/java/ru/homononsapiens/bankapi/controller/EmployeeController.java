package ru.homononsapiens.bankapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.homononsapiens.bankapi.DAO.account.Account;
import ru.homononsapiens.bankapi.DAO.account.AccountService;
import ru.homononsapiens.bankapi.DAO.card.Card;
import ru.homononsapiens.bankapi.DAO.card.CardService;
import ru.homononsapiens.bankapi.DAO.client.Client;
import ru.homononsapiens.bankapi.DAO.client.ClientService;
import ru.homononsapiens.bankapi.DAO.operation.Operation;
import ru.homononsapiens.bankapi.DAO.operation.OperationService;

import java.util.List;

@RestController
@RequestMapping("api/employee/client")
@AllArgsConstructor
public class EmployeeController {

    private final ClientService clientService;
    private final AccountService accountService;
    private final CardService cardService;
    private final OperationService operationService;

    @PutMapping(path = "add")
    public JsonNode list(@RequestBody Client client) {
        return clientService.save(client);
    }

    @PutMapping(path = "account/open")
    public JsonNode open(@RequestBody Account account) {
        return accountService.save(account);
    }

    @PostMapping(path = "card/confirm")
    public void cardConfirm(@RequestBody Card card) {
        cardService.confirm(card);
    }

    @PostMapping(path = "operation/confirm")
    public void operationConfirm(@RequestBody Operation operation) {
        operationService.confirm(operation);
    }

    @GetMapping(path = "list")
    public List<Client> list() {
        return clientService.findAll();
    }

    @GetMapping(path = "card/list")
    public List<Card> cardList() {
        return cardService.findAll();
    }

    @GetMapping(path = "operation/list")
    public List<Operation> operationsList() {
        return operationService.findAll();
    }
}
