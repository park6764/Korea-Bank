package com.example.koreabank.installment_saving;

import com.example.koreabank.account.Account;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class InstallmentSavingAccount{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String uid;

    private Integer password;

    @Column(name = "money_")
    private Integer money;

    private Long createdDate; //적금 시작 날짜
    private Long dueDate; ///시간은 epoch time (밀리초)
    private Integer moneyToPut; // 매번 넣을 돈
    private Integer fromAccountId; // 출금 계좌
    private Integer remainingCount; // 남은 출금 횟수

    @Column(name = "interval_")
    private Long interval; // 얼마마다 출금할지
    @Column(columnDefinition = "integer default 3")
    private Integer penalties; // 출금 시간이 지날 시 페널티 감소, 0이 될 시 계좌 만료
}
