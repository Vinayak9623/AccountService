package com.account.master.entity;

import com.account.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "user can not be null")
    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "account_number", unique = true)
    @NotNull(message = "account number can not be null")
    private String accountNumber;
    @Column(name = "routing_number")
    @NotNull(message = "routing number can not be null")
    private String routingNumber;
    @Column(name = "account_type")
    @NotNull(message = "account-type can not be null")
    private String accountType;
    @Column(name = "balance")
    private Double balance;
    @Column(name = "status")
    private String status;

}
