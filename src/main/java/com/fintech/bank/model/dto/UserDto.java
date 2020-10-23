package com.fintech.bank.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    Long id;

    private String username;

    private String password;

    private String getPassword(){
        return null;
    }

    private String userType;
}
