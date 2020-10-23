package com.fintech.bank.model.dto;

import com.fintech.bank.model.dao.TransactionLogEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
public class TransactionLogDto {

    private String txnId;

    private String accountNumber;

    private String txnType;

    private Double amount;

    private String comment;

    private Date txnDate;

    private String txnStatus;

    public static TransactionLogDto buildAccountTransaction(TransactionLogEntity transactionLogEntity){
        return TransactionLogDto.builder()
                .accountNumber(transactionLogEntity.getAccount().getAccountNumber())
                .amount(transactionLogEntity.getAmount())
                .comment(transactionLogEntity.getComment())
                .txnDate(transactionLogEntity.getTxnDate())
                .txnId(transactionLogEntity.getTxnId())
                .txnStatus(transactionLogEntity.getTxnStatus().name())
                .txnType(transactionLogEntity.getTxnType().name())
                .build();
    }

    public static List<TransactionLogDto> buildAccountTransactions(List<TransactionLogEntity> transactionLogEntities){
        return transactionLogEntities.stream()
                .filter(Objects::nonNull)
                .map(TransactionLogDto::buildAccountTransaction)
                .collect(Collectors.toList());
    }

}
