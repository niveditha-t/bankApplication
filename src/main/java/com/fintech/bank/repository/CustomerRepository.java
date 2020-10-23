package com.fintech.bank.repository;

import com.fintech.bank.model.dao.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {

    boolean existsByCustomerNumber(String customerNumber);

    CustomerEntity findByCustomerNumber(String customerNumber);

    CustomerEntity findByUser_Id(Long id);
}
