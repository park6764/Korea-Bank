package com.example.koreabank.transaction_history;

import java.time.LocalDateTime;

public record TransactionHistoryRecord(Integer fromAccountId, Integer toAccountId, Integer when, Integer money, Integer accountPassword) {

    public TransactionHistory toTransactionHistory() {
        var hs = new TransactionHistory();
        hs.setFromAccountId(fromAccountId);
        hs.setToAccountId(toAccountId);
        hs.setMoney(money);
        hs.setWhen(when);

        return hs;
    }
}