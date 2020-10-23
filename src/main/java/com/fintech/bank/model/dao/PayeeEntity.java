package com.fintech.bank.model.dao;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "payees")
public class PayeeEntity implements Serializable {

    private static AtomicInteger atomicInteger = new AtomicInteger(1);
    private static final String PREFIX = "PYE";

    private static final long serialVersionUID = -3009157732242241606L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String payeeNumber;

    @CreationTimestamp
    private Date dateCreated;

    @UpdateTimestamp
    private Date dateUpdated;

    private String accountNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_number")
    private CustomerEntity customer;
    
    private String payeeName;

    @Enumerated(EnumType.STRING)
    private PayeeType payeeType;
    
    private Double transferLimit;

    public static String genertePayeeNumber() {
        return PREFIX +  String.format("OC%03d",atomicInteger.getAndIncrement());
    }

    public enum PayeeType {
        INTERNAL, EXTERNAL
    }
}
