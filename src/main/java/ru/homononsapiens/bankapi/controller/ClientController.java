package ru.homononsapiens.bankapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.homononsapiens.bankapi.model.Card;
import ru.homononsapiens.bankapi.model.Partner;
import ru.homononsapiens.bankapi.model.Payment;
import ru.homononsapiens.bankapi.model.Refill;
import ru.homononsapiens.bankapi.service.*;
import ru.homononsapiens.bankapi.utils.BalanceResponse;
import ru.homononsapiens.bankapi.utils.IdRequest;
import ru.homononsapiens.bankapi.utils.IdResponse;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/client")
@AllArgsConstructor
public class ClientController {

    private final AccountService accountService;
    private final CardService cardService;
    private final RefillService refillService;
    private final PartnerService partnerService;
    private final PaymentService paymentService;

    /**
     * Выпуск новой карты по счету
     */
    @PostMapping(path = "card/add")
    public ResponseEntity<?> addCard(@RequestBody IdRequest accountId) {
        Long id = cardService.add(accountId.getId());
        return (id != null)
                ? new ResponseEntity<>(new IdResponse(id), HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Просмотр списка подтвержденных карт клиента
     */
    @PostMapping(path = "card/list")
    public ResponseEntity<List<Card>> getCards(@RequestBody IdRequest clientId) {
        List<Card> cards = cardService.getAllByClientId(clientId.getId());
        return (cards != null) && !cards.isEmpty()
                ? new ResponseEntity<>(cards, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Внесение вредств на счет
     */
    @PostMapping(path = "account/refill")
    public ResponseEntity<?> doRefill(@RequestBody Refill refill) {
        Long id = refillService.add(refill);
        return (id != null)
                ? new ResponseEntity<>(new IdResponse(id), HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Проверка баланса счета
     */
    @PostMapping(path = "account/balance")
    public ResponseEntity<?> getAccountBalance(@RequestBody IdRequest accountId) {
        BigDecimal balance = accountService.getBalance(accountId.getId());
        return (balance != null)
                ? new ResponseEntity<>(new BalanceResponse(balance), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Добавление контрагента
     */
    @PostMapping(path = "partner/add")
    public ResponseEntity<?> addPartner(@RequestBody Partner partner) {
        Long id = partnerService.add(partner);
        return (id != null)
                ? new ResponseEntity<>(new IdResponse(id), HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Просмотр контрагентов
     */
    @PostMapping(path = "partner/list")
    public ResponseEntity<List<Partner>> getPartners(@RequestBody IdRequest clientId) {
        List<Partner> partners = partnerService.getAllByClientId(clientId.getId());
        return (partners != null) && !partners.isEmpty()
                ? new ResponseEntity<>(partners, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Перевод средств контрагенту
     */
    @PostMapping(path = "partner/payment")
    public ResponseEntity<?> doPayment(@RequestBody Payment payment) {
        Long id = paymentService.add(payment);
        return (id != null)
                ? new ResponseEntity<>(new IdResponse(id), HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}