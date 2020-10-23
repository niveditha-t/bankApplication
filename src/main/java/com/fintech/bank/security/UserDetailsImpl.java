package com.fintech.bank.security;

import com.fintech.bank.model.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

abstract class UserDetailsImpl implements UserDetails {

    abstract UserDto getUser();

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}