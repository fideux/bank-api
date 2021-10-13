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
import ru.homononsapiens.bankapi.dao.payment.Payment;
import ru.homononsapiens.bankapi.dao.payment.PaymentService;
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
    private final PaymentService paymentService;

    /**
     * Добавление нового клиента
     */
    @PostMapping(path = "add")
    public JsonNode addClient(@RequestBody Client client) {
        return clientService.add(client);
    }

    /**
     * Открытие счета
     */
    @PostMapping(path = "account/open")
    public JsonNode addAccount(@RequestBody Account account) {
        return accountService.add(account);
    }

    /**
     * Подтверждение выпуска карты
     */
    @PostMapping(path = "card/confirm")
    public void confirmCard(@RequestBody Card card) {
        cardService.confirm(card);
    }

    /**
     * Подтверждение операции пополнения
     */
    @PostMapping(path = "refill/confirm")
    public void confirmRefill(@RequestBody Refill refill) {
        refillService.confirm(refill);
    }

    /**
     * Подтверждение операции перевода средств контрагенту
     */
    @PostMapping(path = "payment/confirm")
    public void confirmPayment(@RequestBody Payment payment) {
        paymentService.confirm(payment);
    }

    /**
     * Список клиентов
     */
    @GetMapping(path = "list")
    public List<Client> getClients() {
        return clientService.getAll();
    }

    /**
     * Список счетов
     */
    @GetMapping(path = "account/list")
    public List<Account> getAccounts() {
        return accountService.getAll();
    }

    /**
     * Список выпущенных карт
     */
    @GetMapping(path = "card/list")
    public List<Card> getCards() {
        return cardService.getAll();
    }

    /**
     * Список операций пополнений
     */
    @GetMapping(path = "refill/list")
    public List<Refill> getRefills() {
        return refillService.getAll();
    }

    /**
     * Список операций перевода средств контрагенту
     */
    @GetMapping(path = "payment/list")
    public List<Payment> getPayments() {
        return paymentService.getAll();
    }
}
