
package com.example.account.management.controller;

import com.example.account.management.model.dto.AccountCreateDTO;
import com.example.account.management.model.dto.AccountDTO;
import com.example.account.management.model.dto.AccountPasswordUpdateDTO;
import com.example.account.management.model.dto.AccountUpdateDTO;
import com.example.account.management.model.entity.Account;
import com.example.account.management.service.AccountService;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@Validated @RequestBody AccountCreateDTO accountCreateDTO) {
        Account createdAccount = accountService.createAccount(accountCreateDTO);
        AccountDTO accountDTO = convertAccountToDTO(createdAccount);

        return new ResponseEntity<>(accountDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        AccountDTO accountDTO = convertAccountToDTO(account);

        return ResponseEntity.ok(accountDTO);
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        List<AccountDTO> accountDTOs = accounts.stream().map(this::convertAccountToDTO).toList();

        return ResponseEntity.ok(accountDTOs);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id, @Validated @RequestBody AccountUpdateDTO accountUpdateDTO) {
        Account account = accountService.updateAccount(id, accountUpdateDTO);

        AccountDTO accountDTO = convertAccountToDTO(account);

        return ResponseEntity.ok(accountDTO);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updateAccountPassword(@PathVariable Long id, @Validated @RequestBody AccountPasswordUpdateDTO accountPasswordUpdateDTO) {
        accountService.updatePassword(id, accountPasswordUpdateDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Object>> countAllAccounts() {
        Long count = accountService.countAllAccounts();
        Map<String, Object> body = new HashMap<>();

        body.put("count", count);

        return ResponseEntity.ok(body);
    }

    private AccountDTO convertAccountToDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setName(account.getName());
        accountDTO.setEmail(account.getEmail());
        return accountDTO;
    }
}
