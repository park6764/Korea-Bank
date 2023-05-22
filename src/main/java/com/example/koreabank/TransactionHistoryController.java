package com.example.koreabank;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transaction-history")
public class TransactionHistoryController {
    @PutMapping
    public void createTransactionHistory(
        @RequestBody TransactionHistoryRecord record
    ) {
        
    }
}
