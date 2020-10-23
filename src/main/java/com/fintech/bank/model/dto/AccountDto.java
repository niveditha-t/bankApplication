package com.fintech.bank.model.dto;

import com.fintech.bank.model.dao.AccountEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
public class AccountDto {
    private String accountNumber;
    private String accountType;
    private String accountStatus;
    private String customerNumber;
    private Double currentBalance;

    public static AccountDto buildAccount(AccountEntity accountEntity){
        return AccountDto.builder()
                .accountNumber(accountEntity.getAccountNumber())
                .accountType(accountEntity.getAccountType().name())
                .accountStatus(accountEntity.getAccountStatus().name())
                .currentBalance(accountEntity.getCurrentBalance())
                .customerNumber(accountEntity.getCustomer().getCustomerNumber())
                .build();
    }

    public static List<AccountDto> buildAccounts(List<AccountEntity> accountEntities){
        return accountEntities.stream()
                .filter(Objects::nonNull)
                .map(AccountDto::buildAccount)
                .collect(Collectors.toList());
    }
}
