package com.example.koreabank;

import org.springframework.data.repository.CrudRepository;

public interface TransactionHistoryRepository extends CrudRepository<TransactionHistory, Integer> {}
