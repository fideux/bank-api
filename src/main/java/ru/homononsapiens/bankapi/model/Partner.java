package ru.homononsapiens.bankapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "partners")
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String inn;

    private String bik;

    private String account_number;

    @Column(name = "client_id")
    private Long clientId;
}
