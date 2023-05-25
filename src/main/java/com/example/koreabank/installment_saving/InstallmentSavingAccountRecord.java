package com.example.koreabank.installment_saving;

public record InstallmentSavingAccountRecord(
    String uid, Integer password, Integer money, Integer dueDate, 
    Integer moneyToPut, Integer fromAccountId
) {
    
    public InstallmentSavingAccount toInstallmentSavingAccount() {
        InstallmentSavingAccount installmentSavingAccount = new InstallmentSavingAccount();
        installmentSavingAccount.setUid(uid);
        installmentSavingAccount.setPassword(password);
        installmentSavingAccount.setMoney(money);
        installmentSavingAccount.setDueDate(dueDate);
        installmentSavingAccount.setMoneyToPut(moneyToPut);
        installmentSavingAccount.setFromAccountId(fromAccountId);
        return installmentSavingAccount;
    }
}
