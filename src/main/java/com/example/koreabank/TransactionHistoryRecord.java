package com.example.koreabank;

import java.time.LocalDateTime;

public record TransactionHistoryRecord(Integer fromAccountId, Integer toAccountId, LocalDateTime when, Integer money) {

    public TransactionHistory toTransactionHistory() {
        var hs = new TransactionHistory();
        hs.setFromAccountId(fromAccountId);
        hs.setToAccountId(toAccountId);
        hs.setMoney(money);
        hs.setWhen(when);

        return hs;
    }
}