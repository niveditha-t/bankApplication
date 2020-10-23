package com.fintech.bank.service;

import com.fintech.bank.exception.TransactionNotFoundException;
import com.fintech.bank.model.dao.AccountEntity;
import com.fintech.bank.model.dao.TransactionLogEntity;
import com.fintech.bank.model.dto.TransactionLogDto;

import java.util.List;

public interface TransactionCrudService {
    List<TransactionLogDto> fetchTransactions(String accountNumber, String customerNumber);

    TransactionLogDto fetchTransactionDetails(String txnId, String customerNumber) throws TransactionNotFoundException;

    TransactionLogDto addTransactionDetails(AccountEntity accountEntity, TransactionLogEntity.TransactionType transactionType, Double amount, String txnComment);
}
