package com.account.master.service;

import com.account.dto.AccountDto;
import com.account.dto.UpdateBalanceDto;
import com.account.record.TransferBalance;
import org.springframework.http.ResponseEntity;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

public interface AccountService {

    ResponseEntity<?> createAccount(AccountDto accountDto);
    ResponseEntity<?> getAccountById(UUID accountId);
    ResponseEntity<?> getAccountList(int page , int size,String sortfield,String sortDir);
    ResponseEntity<?> InactiveAccount(UUID accountId);
    ResponseEntity<?> getAccountBalance(UUID accountId);
    ResponseEntity<?> updateAccountBalance(UUID accountId, UpdateBalanceDto balanceDto);
    ResponseEntity<?> tranferAccount(TransferBalance balance) throws AccountNotFoundException;



}
