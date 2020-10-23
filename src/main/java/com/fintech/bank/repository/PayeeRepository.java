package com.fintech.bank.repository;

import com.fintech.bank.model.dao.PayeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayeeRepository extends JpaRepository<PayeeEntity, Long> {

    boolean existsByPayeeNumber(String payeeNumber);

    boolean existsByPayeeNumberAndCustomer_CustomerNumber(String payeeNumber, String customerNumber);

    PayeeEntity findByPayeeNumber(String payeeNumber);

    List<PayeeEntity> findAllByCustomer_CustomerNumber(String customerNumber);
}
