package com.fintech.bank.model.dao;

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
@Table(name = "employees")
public class EmployeeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private static AtomicInteger atomicInteger = new AtomicInteger(1);
    private static final String PREFIX = "EMP";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private UserEntity user;

    @Id
    @Column(name = "employee_number")
    private String employeeNumber;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "employee"
    )
    private List<AccountEntity> accounts;

    public static String generteEmployeeNumber() {
        return PREFIX +  String.format("OC%03d",atomicInteger.getAndIncrement());
    }
}