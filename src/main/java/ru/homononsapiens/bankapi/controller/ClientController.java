package ru.homononsapiens.bankapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.homononsapiens.bankapi.dao.account.Account;
import ru.homononsapiens.bankapi.dao.account.AccountService;
import ru.homononsapiens.bankapi.dao.card.Card;
import ru.homononsapiens.bankapi.dao.card.CardService;
import ru.homononsapiens.bankapi.dao.client.Client;
import ru.homononsapiens.bankapi.dao.partner.Partner;
import ru.homononsapiens.bankapi.dao.partner.PartnerService;
import ru.homononsapiens.bankapi.dao.payment.Payment;
import ru.homononsapiens.bankapi.dao.payment.PaymentService;
import ru.homononsapiens.bankapi.dao.refill.Refill;
import ru.homononsapiens.bankapi.dao.refill.RefillService;

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
    public JsonNode addCard(@RequestBody Card card) {
        return cardService.add(card);
    }

    /**
     * Просмотр списка подтвержденных карт клиента
     */
    @PostMapping(path = "card/list")
    public List<Card> getCards(@RequestBody Client client) {
        return cardService.getAllByClientId(client.getId());
    }

    /**
     * Внесение вредств на счет
     */
    @PostMapping(path = "account/refill")
    public void doRefill(@RequestBody Refill refill) {
        refillService.add(refill);
    }

    /**
     * Проверка баланса счета
     */
    @PostMapping(path = "account/balance")
    public JsonNode getAccountBalance(@RequestBody Account account) {
        return accountService.checkBalance(account.getId());
    }

    /**
     * Добавление контрагента
     */
    @PostMapping(path = "partner/add")
    public JsonNode addPartner(@RequestBody Partner partner) {
        return partnerService.add(partner);
    }

    /**
     * Просмотр контрагентов
     */
    @PostMapping(path = "partner/list")
    public List<Partner> getPartners(@RequestBody Client client) {
        return partnerService.getAllByClientId(client.getId());
    }

    /**
     * Перевод средств контрагенту
     */
    @PostMapping(path = "partner/payment")
    public void doPayment(@RequestBody Payment payment) {
        paymentService.add(payment);
    }

}