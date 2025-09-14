package com.account.master.entity;

import com.account.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mt_account")
public class Account extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(name = "account_id")
    private UUID accountId;
    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "account_number", unique = true)
    private String accountNumber;
    @Column(name = "routing_number")
    private String routingNumber;
    @Column(name = "account_type")
    private String accountType;
    @Column(name = "balance")
    private Double balance;
    @Column(name = "status")
    private String status;

}
