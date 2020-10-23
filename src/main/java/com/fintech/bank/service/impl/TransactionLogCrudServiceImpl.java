package com.fintech.bank.service.impl;

import com.fintech.bank.exception.TransactionNotFoundException;
import com.fintech.bank.model.dao.AccountEntity;
import com.fintech.bank.model.dao.TransactionLogEntity;
import com.fintech.bank.model.dto.TransactionLogDto;
import com.fintech.bank.repository.TransactionRepository;
import com.fintech.bank.service.EntityAccessValidationService;
import com.fintech.bank.service.TransactionCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionLogCrudServiceImpl implements TransactionCrudService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private EntityAccessValidationService entityAccessValidationService;

    @Override
    public List<TransactionLogDto> fetchTransactions(String accountNumber, String customerNumber) {
        return TransactionLogDto.buildAccountTransactions(transactionRepository.getAllByAccount_AccountNumber(accountNumber));
    }

    @Override
    public TransactionLogDto fetchTransactionDetails(String txnId, String customerNumber) throws TransactionNotFoundException {
        entityAccessValidationService.validateTransactionId(txnId, customerNumber);
        return TransactionLogDto.buildAccountTransaction(transactionRepository.findByTxnId(txnId));
    }

    @Override
    public TransactionLogDto addTransactionDetails(AccountEntity accountEntity, TransactionLogEntity.TransactionType transactionType, Double amount, String txnComment) {
        TransactionLogEntity transactionLogEntity = TransactionLogEntity.builder()
                .txnId(TransactionLogEntity.generteTxnId())
                .account(accountEntity)
                .amount(amount)
                .txnStatus(TransactionLogEntity.AccountTransactionStatus.SUCCESS)
                .txnType(transactionType)
                .comment(getComment(transactionType.name(), amount, accountEntity.getCurrentBalance(), txnComment))
                .build();
        return TransactionLogDto.buildAccountTransaction(transactionRepository.save(transactionLogEntity));
    }

    private String getComment(String type, Double amount, Double balance, String txnComment) {
        return new StringBuilder().append("Rs.").append(amount).append(" ").append(type.toLowerCase()).append("ed ").append(txnComment).append("; Updated Balance : Rs."+balance).toString();
    }
}
