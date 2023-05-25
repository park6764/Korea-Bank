package com.example.koreabank.installment_saving;

import com.example.koreabank.account.Account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class InstallmentSavingAccount extends Account {
    private Integer dueDate;
    private Integer moneyToPut;
    private Integer fromAccountId;

    @Column(columnDefinition = "integer default 3")
    private Integer penalties;
}
