package ru.homononsapiens.bankapi.dao.payment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "payments")
@ToString
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "partner_id")
    private Long partnerId;

    private String purpose;

    private String status = "waiting";
}
