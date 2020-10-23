package com.fintech.bank.security;

import com.fintech.bank.model.dao.CustomerEntity;
import com.fintech.bank.model.dao.UserEntity;
import com.fintech.bank.model.dto.CustomerDto;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CustomerDetails extends UserDetailsImpl {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private String customerNumber;
    private List<GrantedAuthority> authorities;

    CustomerDto user;

    public CustomerDetails(UserEntity user) {
    }

    public CustomerDetails(UserEntity user, CustomerEntity customer){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.customerNumber = customer.getCustomerNumber();
        this.authorities = Arrays.stream(user.getUserType().name().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        this.user = CustomerDto.buildCustomer(customer);
    }
}
