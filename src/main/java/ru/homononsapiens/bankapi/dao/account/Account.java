package ru.homononsapiens.bankapi.dao.account;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    private Long balance;

    @Column(name = "client_id")
    private Long clientId;
}
