package com.fintech.bank.model.dao;

import com.fintech.bank.model.Status;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class CustomerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private static AtomicInteger atomicInteger = new AtomicInteger(1);
    private static final String PREFIX = "CUS";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private UserEntity user;

    @Id
    @Column(name = "customer_number")
    private String customerNumber;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "customer"
    )
    private List<AccountEntity> accounts;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "customer"
    )
    private List<PayeeEntity> payees;

    @Enumerated(EnumType.STRING)
    private Status status;

    public static String generteCustomerNumber() {
        return PREFIX +  String.format("OC%03d",atomicInteger.getAndIncrement());
    }
}

