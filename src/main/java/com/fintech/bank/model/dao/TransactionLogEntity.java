package com.fintech.bank.model.dao;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class TransactionLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private static AtomicInteger atomicInteger = new AtomicInteger(1);
    private static final String PREFIX = "TXN";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String txnId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_number", referencedColumnName = "account_number")
    private AccountEntity account;

    @Enumerated(EnumType.STRING)
    private TransactionType txnType;

    private Double amount;

    private String comment;

    @CreationTimestamp
    private Date txnDate;

    @Enumerated(EnumType.STRING)
    private AccountTransactionStatus txnStatus;

    public enum TransactionType {
        CREDIT, DEBIT, TRANSFER
    }

    public enum AccountTransactionStatus {
        SUCCESS, FAILED
    }

    public static String generteTxnId() {
        return PREFIX +  String.format("OC%03d",atomicInteger.getAndIncrement());
    }
}
