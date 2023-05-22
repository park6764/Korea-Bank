package com.example.koreabank;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TransactionHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "from_account_id")
    private Integer fromAcoountId;

    @Column(name = "to_account_id")
    private Integer toAcoountId;

    private LocalDateTime when;
    private Integer money; // 이동한 금액
}