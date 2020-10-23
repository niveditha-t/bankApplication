package com.fintech.bank;

import com.fintech.bank.security.CustomerDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserContextHelper {

    private String getCustomerNumber() {
        return ((CustomerDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCustomerNumber();
    }

}
