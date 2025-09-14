package com.account.master.repository;

import com.account.master.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account,UUID> {

    @Query(value = "SELECT a.balance FROM mt_account a WHERE a.account_id = :accountId", nativeQuery = true)
    Double getAccountBalanceByAccountId(@Param("accountId") UUID accountId);

}
