package com.example.koreabank.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PutMapping("/account") // 생성
    public void createAccount(@RequestBody AccountRecord record) {
        accountRepository.save(record.toAccount());
    }

    @GetMapping("/account") 
    public List<Account> getAllAccounts(
        @RequestParam(name = "uid") String uid
    ) {
        var accounts =  accountRepository.findAll();
        var list = new ArrayList<Account>();
        for (Account account : accounts) {
            if(account.getUid().equals(uid)) { list.add(account); }
            else continue;
        }
        return list;
    }

    @DeleteMapping("/account")
    public void deleteAccount(
        @RequestParam(name = "accountId") Integer accountId
    ) {
        accountRepository.deleteById(accountId);
    }

    @PostMapping("/account")
    public void editAccountPw(
        @RequestParam(name = "uid") String uid,
        @RequestParam(name = "accountId") Integer accountId,
        @RequestParam(name = "newPw") Integer newPw
    ) {
        var account = accountRepository.findById(accountId);
        if(account.isPresent()) {
            var newAccount = account.get();
            newAccount.setPassword(newPw);
            accountRepository.save(newAccount);
        }
    }
}
