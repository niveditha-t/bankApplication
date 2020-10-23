package com.fintech.bank.service.impl;

import com.fintech.bank.model.dao.UserEntity;
import com.fintech.bank.repository.CustomerRepository;
import com.fintech.bank.repository.EmployeeRepository;
import com.fintech.bank.security.CustomerDetails;
import com.fintech.bank.repository.UserRepository;
import com.fintech.bank.security.EmployeeDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(userName);

        user.orElseThrow(() -> new UsernameNotFoundException(userName + " not found."));

        if(UserEntity.UserType.CUSTOMER.name().equalsIgnoreCase(user.get().getUserType().name()))
            return user.map(customerUser -> new CustomerDetails(customerUser, customerRepository.findByUser_Id(customerUser.getId()))).get();
        else
            return user.map(employeeUser -> new EmployeeDetails(employeeUser, employeeRepository.findByUser_Id(employeeUser.getId()))).get();
    }

}
