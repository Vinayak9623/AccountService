package com.account.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountDto {
    private UUID accountId;
    private UUID userId;
    private String accountNumber;
    private String routingNumber;
    private String accountType;
    private Double balance;
    private String status;
}
