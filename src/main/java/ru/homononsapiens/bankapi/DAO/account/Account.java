package ru.homononsapiens.bankapi.DAO.account;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.homononsapiens.bankapi.DAO.DAO;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "accounts")
public abstract class Account implements DAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    private Long balance;

    private Long client_id;
}
