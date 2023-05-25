package com.example.koreabank.account;

public record AccountRecord(String uid, Integer password, Integer money) {
    
    public Account toAccount() {
        var account = new Account();
        account.setUid(uid);
        account.setPassword(password);
        account.setMoney(money);
        return account;
    }
}