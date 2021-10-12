package ru.homononsapiens.bankapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.homononsapiens.bankapi.DAO.operation.Operation;
import ru.homononsapiens.bankapi.DAO.account.Account;
import ru.homononsapiens.bankapi.DAO.account.AccountService;
import ru.homononsapiens.bankapi.DAO.operation.OperationService;

@RestController
@RequestMapping("api/client/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final OperationService operationService;

//    @PostMapping(path = "list")
//    public List<Account> list() {
//        return accountService.findAll();
//    }

//    @GetMapping(path = "get/{accountId}")
//    public Account get(@PathVariable("accountId") Long id) {
//        return accountService.find(id);
//    }


    @PostMapping(path = "balance")
    public JsonNode balance(@RequestBody Account account) {
        return accountService.checkBalanceByAccountId(account.getId());
    }

    @PutMapping(path = "put")
    public void put(@RequestBody Operation operation) {
        operationService.save(operation);
    }
}
