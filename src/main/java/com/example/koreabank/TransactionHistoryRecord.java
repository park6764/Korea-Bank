package com.example.koreabank;

import java.time.LocalDateTime;

public record TransactionHistoryRecord(Integer fromAcoountId, Integer toAcoountId, LocalDateTime when, Integer money) {
}