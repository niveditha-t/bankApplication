package com.fintech.bank.model.dto;

import lombok.Data;

@Data
public class FundTransferDto {
    String payeeNumber;
    Double transferAmount;
}
