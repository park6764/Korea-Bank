package com.example.koreabank.account;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String uid;

    private Integer password;

    @Column(name = "money_")
    private Integer money;
}
