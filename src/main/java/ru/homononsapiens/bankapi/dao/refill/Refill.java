package ru.homononsapiens.bankapi.dao.refill;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "refills")
@ToString
public class Refill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    @Column(name = "account_id")
    private Long accountId;

    private String status = "waiting";
}
