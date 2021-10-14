package ru.homononsapiens.bankapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.homononsapiens.bankapi.model.*;
import ru.homononsapiens.bankapi.service.*;
import ru.homononsapiens.bankapi.utils.IdRequest;
import ru.homononsapiens.bankapi.utils.IdResponse;

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
    public ResponseEntity<?> addClient(@RequestBody Client client) {
        Long id = clientService.add(client);
        return (id != null)
                ? new ResponseEntity<>(new IdResponse(id), HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Открытие счета
     */
    @PostMapping(path = "account/open")
    public ResponseEntity<?> addAccount(@RequestBody Account account) {
        Long id = accountService.add(account);
        return (id != null)
                ? new ResponseEntity<>(new IdResponse(id), HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Подтверждение выпуска карты
     */
    @PostMapping(path = "card/confirm")
    public ResponseEntity<?> confirmCard(@RequestBody IdRequest idCard) {
        return cardService.confirm(idCard.getId())
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Подтверждение операции пополнения
     */
    @PostMapping(path = "refill/confirm")
    public ResponseEntity<?> confirmRefill(@RequestBody IdRequest idCard) {
        return refillService.confirm(idCard.getId())
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Подтверждение операции перевода средств контрагенту
     */
    @PostMapping(path = "payment/confirm")
    public ResponseEntity<?> confirmPayment(@RequestBody IdRequest idCard) {
        return paymentService.confirm(idCard.getId())
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Список клиентов
     */
    @GetMapping(path = "list")
    public ResponseEntity<List<Client>> getClients() {
        List<Client> clients = clientService.getAll();
        return (clients != null) && !clients.isEmpty()
                ? new ResponseEntity<>(clients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Список счетов
     */
    @GetMapping(path = "account/list")
    public ResponseEntity<List<Account>> getAccounts() {
        List<Account> accounts = accountService.getAll();
        return (accounts != null) && !accounts.isEmpty()
                ? new ResponseEntity<>(accounts, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Список выпущенных карт
     */
    @GetMapping(path = "card/list")
    public ResponseEntity<List<Card>> getCards() {
        List<Card> cards = cardService.getAll();
        return (cards != null) && !cards.isEmpty()
                ? new ResponseEntity<>(cards, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Список операций пополнений
     */
    @GetMapping(path = "refill/list")
    public ResponseEntity<List<Refill>> getRefills() {
        List<Refill> refills = refillService.getAll();
        return (refills != null) && !refills.isEmpty()
                ? new ResponseEntity<>(refills, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Список операций перевода средств контрагенту
     */
    @GetMapping(path = "payment/list")
    public ResponseEntity<List<Payment>> getPayments() {
        List<Payment> payments = paymentService.getAll();
        return (payments != null) && !payments.isEmpty()
                ? new ResponseEntity<>(payments, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
