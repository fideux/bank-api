package ru.homononsapiens.bankapi.DAO.account;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.homononsapiens.bankapi.DAO.DAO;

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
