package com.account.master.service.impl;

import com.account.client.MicroClient;
import com.account.common.ApiResponse;
import com.account.dto.AccountDto;
import com.account.dto.UserRequest;
import com.account.master.entity.Account;
import com.account.master.repository.AccountRepository;
import com.account.master.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private MicroClient client;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public ResponseEntity<?> createAccount(AccountDto accountDto) {
     var response =new ApiResponse<>();

     Account account;
     account=Objects.nonNull(accountDto.getAccountId())?
             accountRepository
                .findById(accountDto
                        .getAccountId())
                .orElseThrow(() ->
                        new RuntimeException("Account not found"))
             :new Account();

         UserRequest user = client.getUserById(accountDto.getUserId());

         account.setUserId(user.getId());
         account.setAccountNumber(accountDto.getAccountNumber());
         account.setRoutingNumber(accountDto.getRoutingNumber());
         account.setAccountType(accountDto.getAccountType());
         account.setBalance(accountDto.getBalance());
         account.setStatus(accountDto.getStatus());
         accountRepository.save(account);
        String message=Objects.isNull(accountDto.getAccountId())?"Account created successfully":"Account updated successfully";
         response.responseMethod(HttpStatus.OK.value(),message ,null,null);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getAccountById(UUID accountId) {
        var response=new ApiResponse<>();
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found with given Id: " + accountId));
        AccountDto dto = AccountDto.builder().accountId(account.getAccountId())
                .userId(account.getUserId())
                .accountNumber(account.getAccountNumber())
                .routingNumber(account.getRoutingNumber())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .status(account.getStatus()).build();
        response.responseMethod(HttpStatus.OK.value(), "Account get successfully",dto,null);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getAccountList(int page, int size,String sortField,String sortDir) {
        var response=new ApiResponse<>();
      Sort sort =sortDir.equalsIgnoreCase("asc")
              ? Sort.by(sortField).ascending():
              Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Account> AccountList = accountRepository.findAll(pageable);
        List<AccountDto> list = AccountList.stream().map(x -> AccountDto.builder().accountId(x.getAccountId())
                .userId(x.getUserId())
                .accountNumber(x.getAccountNumber())
                .routingNumber(x.getRoutingNumber())
                .accountType(x.getAccountType())
                .balance(x.getBalance())
                .status(x.getStatus()).build()
        ).toList();
        response.responseMethod(HttpStatus.OK.value(), "Account list get successfully",list,null);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> InactiveAccount(UUID accountId) {
        var response = new ApiResponse<>();

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setStatus("INACTIVE");
        accountRepository.save(account);

        response.responseMethod(
                HttpStatus.OK.value(),
                "Account marked as Inactive successfully",
                null,
                null
        );
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getAccountBalance(UUID accountId) {
        var response=new ApiResponse<>();
      Double balance =accountRepository.getAccountBalanceByAccountId(accountId);
      response.responseMethod(HttpStatus.OK.value(),"Balance fetch successfully",balance,null);
        return ResponseEntity.ok(response);
    }

}
