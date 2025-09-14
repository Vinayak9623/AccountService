package com.account.master.service;

import com.account.dto.AccountDto;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AccountService {

    ResponseEntity<?> createAccount(AccountDto accountDto);
    ResponseEntity<?> getAccountById(UUID accountId);
    ResponseEntity<?> getAccountList(int page , int size,String sortfield,String sortDir);
    ResponseEntity<?> InactiveAccount(UUID accountId);
    ResponseEntity<?> getAccountBalance(UUID accountId);



}
