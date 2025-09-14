package com.account.master.service.impl;

import com.account.client.MicroClient;
import com.account.common.ApiResponse;
import com.account.dto.AccountDto;
import com.account.dto.UserRequest;
import com.account.master.entity.Account;
import com.account.master.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private MicroClient microClient;

    private AccountDto accountDto;
    private UserRequest userRequest;
    private Account account;

    @BeforeEach
    void setUp(){

        MockitoAnnotations.openMocks(this);

        UUID userId =UUID.randomUUID();
        UUID accountId =UUID.randomUUID();

        accountDto = new AccountDto();
        accountDto.setUserId(userId);
        accountDto.setAccountNumber("123456");
        accountDto.setRoutingNumber("654321");
        accountDto.setAccountType("SAVINGS");
        accountDto.setBalance(1000.0);
        accountDto.setStatus("ACTIVE");
        userRequest=new UserRequest(userId,"test","test@gmail.com",null,"9308074152");
        account=new Account();
        account.setAccountId(accountId);
        account.setUserId(userId);
    }

    @Test
    void createAccount(){
        Mockito.when(microClient.getUserById(accountDto.getUserId())).thenReturn(userRequest);
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);

        ResponseEntity<?> response = accountService.createAccount(accountDto);

        Assertions.assertEquals(200,response.getStatusCode().value());
        Assertions.assertTrue(response.getBody().toString().contains("Account created successfully"));

        Mockito.verify(accountRepository,Mockito.times(1)).save(Mockito.any(Account.class));
        Mockito.verify(microClient,Mockito.times(1)).getUserById(accountDto.getUserId());

    }


    @Test
    void testUpdateAccount(){

        UUID existingAccountId=UUID.randomUUID();
        accountDto.setAccountId(existingAccountId);

      Account existingAccount =new Account();
      existingAccount.setAccountId(existingAccountId);
      existingAccount.setUserId(accountDto.getUserId());

      Mockito.when(accountRepository.findById(existingAccountId)).thenReturn(Optional.of(existingAccount));
      Mockito.when(microClient.getUserById(accountDto.getUserId())).thenReturn(userRequest);
      Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(existingAccount);

        ResponseEntity<?> response = accountService.createAccount(accountDto);

        Assertions.assertEquals(200,response.getStatusCode().value());
        Assertions.assertTrue(response.getBody().toString().contains("Account updated successfully"));

        Mockito.verify(accountRepository,Mockito.times(1)).findById(existingAccountId);
        Mockito.verify(accountRepository,Mockito.times(1)).save(Mockito.any(Account.class));
        Mockito.verify(microClient,Mockito.times(1)).getUserById(accountDto.getUserId());

    }

    @Test
    void testUpdateAccount_notFound(){
        UUID invalidId =UUID.randomUUID();
        accountDto.setAccountId(invalidId);

        Mockito.when(accountRepository.findById(invalidId)).thenReturn(Optional.empty());
        Exception exception =Assertions.assertThrows(RuntimeException.class,()->{
            accountService.createAccount(accountDto);
        });

        Assertions.assertEquals("Account not found",exception.getMessage());
        Mockito.verify(accountRepository,Mockito.times(1)).findById(invalidId);
        Mockito.verify(accountRepository,Mockito.never()).save(Mockito.any());
    }

    @Test
    public void getAccountList(){
    Account account =new Account();
    account.setAccountId(UUID.randomUUID());
    account.setUserId(UUID.randomUUID());
    account.setAccountNumber("126784936");
    account.setRoutingNumber("6749374537");
    account.setAccountType("SAVING");
    account.setBalance(12234.00);
    account.setStatus("ACTIVE");

        List<Account> list = List.of(account);
        Page<Account> page= new PageImpl<>(list);
        Mockito.when(accountRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);
        ResponseEntity<?> response = accountService.getAccountList(0, 5, "accountNumber", "asc");

        Assertions.assertEquals(200,response.getStatusCode().value());
        ApiResponse<?> body = (ApiResponse<?>) response.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals("Account list get successfully",body.getMessage());
    }

    @Test
    public void deleteAccount(){
        UUID accountId =UUID.randomUUID();
      Account account =new Account();
      account.setAccountId(accountId);
      account.setStatus("INACTIVE");

      Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
      Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);

        ResponseEntity<?> response = accountService.InactiveAccount(accountId);

        Assertions.assertEquals(200,response.getStatusCode().value());
        ApiResponse<?> body =(ApiResponse<?>) response.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals("Account marked as Inactive successfully",body.getMessage());
        Assertions.assertEquals("INACTIVE",account.getStatus());

    }

}