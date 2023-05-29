package com.example.koreabank.transaction_history;

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
    private Integer fromAccountId;

    @Column(name = "to_account_id")
    private Integer toAccountId;

    @Column(name = "commit_time")
    private Long commitTime; //시간은 epoch time (밀리초)

    @Column(name = "money_")
    private Integer money; // 이동한 금액
}