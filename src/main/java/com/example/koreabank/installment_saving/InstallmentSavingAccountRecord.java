package com.example.koreabank.installment_saving;

public record InstallmentSavingAccountRecord(
    String uid,
    Integer password,
    Integer money,
    Long createdDate,
    Long dueDate,
    Integer remainingCount, // 남은 출금 횟수
    Long interval, // 얼마마다 출금할지
    Integer moneyToPut,
    Integer fromAccountId
) {
    
    public InstallmentSavingAccount toInstallmentSavingAccount() {
        InstallmentSavingAccount installmentSavingAccount = new InstallmentSavingAccount();
        installmentSavingAccount.setUid(uid);
        installmentSavingAccount.setPassword(password);
        installmentSavingAccount.setMoney(money);
        installmentSavingAccount.setDueDate(dueDate);
        installmentSavingAccount.setMoneyToPut(moneyToPut);
        installmentSavingAccount.setFromAccountId(fromAccountId);
        installmentSavingAccount.setCreatedDate(createdDate);
        installmentSavingAccount.setRemainingCount(remainingCount);
        installmentSavingAccount.setInterval(interval);
        return installmentSavingAccount;
    }
}
