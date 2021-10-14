package ru.homononsapiens.bankapi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    @Column(name = "account_id")
    private Long accountId;

    private Boolean confirmed;

    public Card() {}

    public Card(String number, Long accountId) {
        this.number = number;
        this.accountId = accountId;
        this.confirmed = false;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = false;
    }
}
