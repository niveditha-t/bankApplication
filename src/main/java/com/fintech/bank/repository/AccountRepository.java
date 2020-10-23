package com.fintech.bank.repository;

import com.fintech.bank.model.Status;
import com.fintech.bank.model.dao.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    List<AccountEntity> getAllByCustomer_CustomerNumber(String customerNumber);

    boolean existsByAccountNumber(String accountNumber);

    boolean existsByAccountNumberAndAccountStatus(String accountNumber, Status accountStatus);

    boolean existsByAccountNumberAndCustomer_CustomerNumber(String accountNumber, String customerNumber);

    AccountEntity findByAccountNumber(String accountNumber);
}
