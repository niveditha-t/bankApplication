package com.fintech.bank.repository;

import com.fintech.bank.model.dao.TransactionLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionLogEntity, Long> {

    List<TransactionLogEntity> getAllByAccount_AccountNumber (String accountNumber);

    TransactionLogEntity findByTxnId(String transactionId);

//    boolean existsByTxnIdAAndAccount_Customer_CustomerNumber(String transactionId, String customerNumber);

    boolean existsByTxnId(String transactionId);
}
