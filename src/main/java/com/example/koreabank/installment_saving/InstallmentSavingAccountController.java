package com.example.koreabank.installment_saving;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.koreabank.account.Account;
import com.example.koreabank.account.AccountRepository;

@Controller
@RequestMapping("/account/installment-saving")
public class InstallmentSavingAccountController {
    @Autowired private InstallmentSavingRepository installmentSavingRepository;
    @Autowired private AccountRepository accountRepository;

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

    @GetMapping
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

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/tryDeposit")
    public void tryDeposit(
        @RequestParam Integer id
    ) {
        InstallmentSavingAccount isa = installmentSavingRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "일치하는 적금 계좌가 존재하지 않습니다."));

        
        Account account = accountRepository.findById(isa.getFromAccountId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "일치하는 출금 계좌가 존재하지 않습니다."));

        if(account.getMoney() < isa.getMoneyToPut()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "출금 계좌의 잔액이 부족합니다.");
        
        if(isa.getPenalties() == 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "만료된 적금 계좌입니다.");

        // Integer fullCount = (int) ((isa.getDueDate() - isa.getCreatedDate()) / isa.getInterval());
        // Integer current = fullCount - isa.getRemainingCount();
        Long currentDue = isa.getDueDate() - (isa.getInterval() * isa.getRemainingCount()); // 현재 만료 날짜
        if(System.currentTimeMillis() > currentDue) {
            isa.setPenalties(isa.getPenalties() - 1);
        }

        isa.setMoney(isa.getMoney() + isa.getMoneyToPut());
        account.setMoney(account.getMoney() - isa.getMoneyToPut());

        installmentSavingRepository.save(isa); // 객체 수정 후 save 호출 시 SQL UPDATE 문 실행과 같다.
        accountRepository.save(account);
    }

    @PostMapping
    public void editInstallmentSavingAccountInfo(
        @RequestParam String uid,
        @RequestParam Integer accountId,
        @RequestParam Optional<Integer> newPw,
        @RequestParam Optional<Integer> fromAccountId
    ) {
        Optional<InstallmentSavingAccount> ISA = installmentSavingRepository.findById(accountId);

        if(ISA.isPresent()) {
            var newISA = ISA.get();

            if(newPw.isEmpty() && fromAccountId.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정할 정보를 입력해 주세요.");

            if(newPw.isPresent()) {
                newISA.setPassword(newPw.get());
            }

            if(fromAccountId.isPresent()) {
                newISA.setFromAccountId(fromAccountId.get());
            }
            installmentSavingRepository.save(newISA);
        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "일치하는 계좌가 존재하지 않습니다.");
    }

    @DeleteMapping
    public void deleteAccount(
        @RequestParam(name = "id") Integer id
    ) {
        installmentSavingRepository.deleteById(id);
    }
}
