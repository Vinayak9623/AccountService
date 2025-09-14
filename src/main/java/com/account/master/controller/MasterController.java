package com.account.master.controller;

import com.account.dto.AccountDto;
import com.account.master.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/account")
public class MasterController {

    @Autowired
    private AccountService service;


    @PostMapping("/create")
    public ResponseEntity<?> createUpdateAccount(@RequestBody AccountDto accountDto) {
        return service.createAccount(accountDto);
    }

    @GetMapping("/getAccount/{accountId}")
    public ResponseEntity<?> getAccountById(@PathVariable("accountId") UUID accountId){
        return service.getAccountById(accountId);
    }

    @GetMapping("/getUserList")
    public ResponseEntity<?> getAccountList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "accountId") String sortfield, @RequestParam(defaultValue = "asc") String sortDir) {
        return service.getAccountList(page, size, sortfield, sortDir);
    }

    @PostMapping("/delete/{accountId}")
    public ResponseEntity<?> inActiveAccount(@PathVariable("accountId") UUID accountId) {
        return service.InactiveAccount(accountId);

    }

    @GetMapping("/getBalance/{accountId}")
    public ResponseEntity<?> getBalanceByAccountId(@PathVariable("accountId") UUID accountId){
        return service.getAccountBalance(accountId);
    }

}
