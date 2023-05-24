package com.example.koreabank;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/transaction-history")
public class TransactionHistoryController {
    @Autowired
    private TransactionHistoryRepository thRepository;
    @Autowired
    private AccountRepository accountRepository;

    @PutMapping
    public ResponseEntity<String> createTransactionHistory(
        @RequestBody TransactionHistoryRecord record
    ) {
        if(record.fromAccountId() == null && record.toAccountId() == null) return ResponseEntity.badRequest().body("보내는 계좌와 받는 계좌의 ID가 모두 비어있습니다.");
        else if(record.fromAccountId() == null) { //입금
            Optional<Account> to = accountRepository.findById(record.toAccountId());

            if(to.isEmpty()) return ResponseEntity.badRequest().body("입금하는 계좌가 존재하지 않습니다.");
            else {
                Account to_ = to.get();

                to_.setMoney(to_.getMoney() + record.money());
                accountRepository.save(to_);
                thRepository.save(record.toTransactionHistory());
                return ResponseEntity.created(null).build();
            }
        }
        else if(record.toAccountId() == null) { // 인출하는 경우
            Optional<Account> from = accountRepository.findById(record.fromAccountId());

            if(from.isEmpty()) return ResponseEntity.badRequest().body("출금하려는 계좌가 존재하지 않습니다.");
            else {
                Account from_ = from.get();

                if(from_.getMoney() < record.money()) return ResponseEntity.internalServerError().body("금액이 부족하여 출금할 수 없습니다.");
                else {
                    from_.setMoney(from_.getMoney() - record.money());
                    accountRepository.save(from_);
                    thRepository.save(record.toTransactionHistory());
                    return ResponseEntity.created(null).build();
                }
            }
        }
        else { // 송금하는 경우
            Optional<Account> from = accountRepository.findById(record.fromAccountId());
            Optional<Account> to = accountRepository.findById(record.toAccountId());

            if(from.isEmpty() || to.isEmpty()) return ResponseEntity.badRequest().body("보내는 계좌 또는 받는 계좌가 존재하지 않습니다.");
            else {
                Account from_ = from.get();
                Account to_ = to.get();

                if(from_.getMoney() < record.money()) return ResponseEntity.internalServerError().body("금액이 부족하여 출금할 수 없습니다.");
                else {
                    from_.setMoney(from_.getMoney() - record.money());
                    to_.setMoney(to_.getMoney() + record.money());
                    accountRepository.save(from_);
                    accountRepository.save(to_);
                    thRepository.save(record.toTransactionHistory());
                    return ResponseEntity.created(null).build();
                }
            }
        }
    }

    @GetMapping
    public List<TransactionHistory> getAllTransactionHistory(
        @RequestParam(name = "uid") String uid
    ) {
        Iterable<TransactionHistory> transactionHistorys = thRepository.findAll();
        ArrayList<Account> accountList = new ArrayList<Account>();

        Iterable<Account> accounts =  accountRepository.findAll();

        for (Account account : accounts) {
            if(account.getUid().equals(uid)) { accountList.add(account); }
            else continue;
        }

        ArrayList<TransactionHistory> transactionHistoryList = new ArrayList<TransactionHistory>();
        
        for (TransactionHistory transactionHistory : transactionHistorys) {
            for (Account account : accountList) {
                if(transactionHistory.getFromAccountId() == account.getId()
                    || transactionHistory.getToAccountId() == account.getId()) {
                        transactionHistoryList.add(transactionHistory);
                }
                else continue;
            }
        }
        return transactionHistoryList;
    }

    @GetMapping("byAccount")
    public List<TransactionHistory> getTransactionHistoryByAccount(
    @RequestParam(name = "accountId") Integer accountId
    ) {
        Iterable<TransactionHistory> ths = thRepository.findAll(); 
        ArrayList<TransactionHistory> tsByAccountList = new ArrayList<TransactionHistory>(); 
        for (TransactionHistory th : ths) {
            if(th.getFromAccountId() == accountId || th.getToAccountId() == accountId) {
                tsByAccountList.add(th);
            } else continue;
        }
        return tsByAccountList;
    }
}
