package ru.homononsapiens.bankapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.homononsapiens.bankapi.DAO.account.Account;
import ru.homononsapiens.bankapi.DAO.account.AccountService;
import ru.homononsapiens.bankapi.DAO.card.Card;
import ru.homononsapiens.bankapi.DAO.card.CardService;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("client/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping(path = "list")
    public List<Account> list() {
        return accountService.findAll();
    }

    @GetMapping(path = "get/{accountId}")
    public Account get(@PathVariable("accountId") Long id) {
        return accountService.find(id);
    }

    @PutMapping(path = "put")
    public void put(@RequestBody Account account) {
        accountService.put(account);
    }
}
