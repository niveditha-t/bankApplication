package com.fintech.bank.security;

import com.fintech.bank.model.dao.EmployeeEntity;
import com.fintech.bank.model.dao.UserEntity;
import com.fintech.bank.model.dto.EmployeeDto;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class EmployeeDetails extends UserDetailsImpl {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private String employeeNumber;
    private List<GrantedAuthority> authorities;

    EmployeeDto user;

    public EmployeeDetails(UserEntity user) {
    }

    public EmployeeDetails(UserEntity user, EmployeeEntity employee){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.employeeNumber = employee.getEmployeeNumber();
        this.authorities = Arrays.stream(user.getUserType().name().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        this.user = EmployeeDto.buildEmployee(employee);
    }

}
