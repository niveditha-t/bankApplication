package com.fintech.bank.model.dto;

import com.fintech.bank.model.dao.PayeeEntity;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Transient;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Data
@Builder
public class PayeeDto {

    private String payeeNumber;

    private String payeeAccountNumber;

    private String payeeName;

    private String payeeType;

    private Double transferLimit;

    public static PayeeDto buildPayee(PayeeEntity payeeEntity){
        return PayeeDto.builder()
                .payeeAccountNumber(payeeEntity.getAccountNumber())
                .payeeNumber(payeeEntity.getPayeeNumber())
                .payeeName(payeeEntity.getPayeeName())
                .payeeType(payeeEntity.getPayeeType().name())
                .transferLimit(payeeEntity.getTransferLimit())
                .build();
    }

    public static List<PayeeDto> buildPayees(List<PayeeEntity> payeeEntities){
        return payeeEntities.stream()
                .filter(Objects::nonNull)
                .map(PayeeDto::buildPayee)
                .collect(Collectors.toList());
    }
}
