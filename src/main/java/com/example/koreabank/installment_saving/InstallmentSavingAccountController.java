package com.example.koreabank.installment_saving;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.koreabank.account.Account;
import com.example.koreabank.account.AccountRepository;

@Controller
@RequestMapping("/account/installment-saving")
public class InstallmentSavingAccountController {
    @Autowired
    private InstallmentSavingRepository installmentSavingRepository;
    private AccountRepository accountRepository;

    @PutMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createISA(
        @RequestBody InstallmentSavingAccountRecord record
    ) throws ResponseStatusException {
        Optional<Account> account = accountRepository.findById(record.fromAccountId());

        if(record.moneyToPut() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "적금할 금액이 부족합니다.");
        } else if(account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "일치하는 계좌가 존재하지 않습니다.");
        } else {
            String uid = account.get().getUid();
            if(uid.equals(record.uid())) {
                installmentSavingRepository.save(record.toInstallmentSavingAccount());
            } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "본인의 계좌가 아닙니다.");
        } 
    }

    @GetMapping("/") 
    public List<InstallmentSavingAccount> getAllISA(
        @RequestParam(name = "uid") String uid
    ) {
        Iterable<InstallmentSavingAccount> installmentSavingAccounts =  installmentSavingRepository.findAll();
        var list = new ArrayList<InstallmentSavingAccount>();
        for (InstallmentSavingAccount installmentSavingAccount : installmentSavingAccounts) {
            if(installmentSavingAccount.getUid().equals(uid)) { list.add(installmentSavingAccount); }
            else continue;
        }
        return list;
    }

    @PostMapping
    public void editInstallmentSavingAccountInfo(
        @RequestParam Integer accountId,
        @RequestParam Integer fromAccountId
    ) {
        
    }

    @PostMapping("/")
    public void editInstallmentSavingAccountInfo(
        @RequestParam String uid,
        @RequestParam Integer accountId,
        @RequestParam Optional<Integer> newPw,
        @RequestParam Optional<Integer> fromAccountId
    ) {
        Optional<InstallmentSavingAccount> ISA = installmentSavingRepository.findById(accountId);

        if(ISA.isPresent()) {
            var newISA = ISA.get();

            if(newPw.isPresent()) {
                newISA.setPassword(newPw.get());
            } 
            
            if(fromAccountId.isPresent()) {
                newISA.setFromAccountId(fromAccountId.get());
            }
            accountRepository.save(newISA);
        }
    }
}
