package com.fintech.bank.model.dto;

import com.fintech.bank.model.dao.EmployeeEntity;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class EmployeeDto extends UserDto {

    private String employeeNumber;

    public static EmployeeDto buildEmployee(EmployeeEntity employeeEntity){
        return EmployeeDto.builder()
                .id(employeeEntity.getUser().getId())
                .username(employeeEntity.getUser().getUsername())
                .password(employeeEntity.getUser().getPassword())
                .userType(employeeEntity.getUser().getUserType().name())
                .employeeNumber(employeeEntity.getEmployeeNumber())
                .build();
    }
}
