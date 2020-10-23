package com.fintech.bank.repository;

import com.fintech.bank.model.dao.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, String> {

    boolean existsByEmployeeNumber(String employeeNumber);

    EmployeeEntity findByEmployeeNumber(String employeeNumber);

    EmployeeEntity findByUser_Id(Long id);
}
