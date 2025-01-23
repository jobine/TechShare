
package com.example.account.management.controller;

import com.example.account.management.common.ResponseBody;
import com.example.account.management.common.validation.annotation.ValidatedAccountId;
import com.example.account.management.common.validation.annotation.ValidatedPageNumber;
import com.example.account.management.common.validation.annotation.ValidatedPageSize;
import com.example.account.management.model.dto.AccountCreateDTO;
import com.example.account.management.model.dto.AccountDTO;
import com.example.account.management.model.dto.AccountPasswordUpdateDTO;
import com.example.account.management.model.dto.AccountUpdateDTO;
import com.example.account.management.model.entity.Account;
import com.example.account.management.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Operation(summary = "Create an account", description = "Create an account by providing the necessary details.")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody<AccountDTO>> createAccount(
            @Schema(implementation = AccountCreateDTO.class, description = "The details of the account to be created.")
            @Validated
            @RequestBody
            AccountCreateDTO accountCreateDTO) {
        Account createdAccount = accountService.createAccount(accountCreateDTO);
        AccountDTO accountDTO = convertAccountToDTO(createdAccount);
        ResponseBody<AccountDTO> body = new ResponseBody<>(accountDTO);

        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @Operation(summary = "Get an account by ID", description = "Get an account by providing the account ID.")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody<AccountDTO>> getAccountById(
            @Schema(description = "The ID of the account to be retrieved.", example = "1")
            @ValidatedAccountId
            @PathVariable
            Long id) {
        Account account = accountService.getAccountById(id);
        AccountDTO accountDTO = convertAccountToDTO(account);
        ResponseBody<AccountDTO> body = new ResponseBody<>(accountDTO);

        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Get all accounts", description = "Get all accounts with pagination.")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody<Page<AccountDTO>>> getAllAccounts(
            @Schema(description = "The page number to retrieve.", example = "0")
            @ValidatedPageNumber
            @RequestParam
            int page,
            @Schema(description = "The size of the page to retrieve.", example = "10")
            @ValidatedPageSize
            @RequestParam
            int size) {
        Page<Account> accounts = accountService.getAccountsWithPagination(page, size);
        Page<AccountDTO> accountDTOs = accounts.map(this::convertAccountToDTO);
        ResponseBody<Page<AccountDTO>> body = new ResponseBody<>(accountDTOs);

        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Update an account", description = "Update an account by providing the account ID and the details to be updated.")
    @PatchMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody<AccountDTO>> updateAccount(
            @Schema(description = "The ID of the account to be updated.", example = "1")
            @ValidatedAccountId
            @PathVariable
            Long id,
            @Schema(implementation = AccountUpdateDTO.class, description = "The details of the account to be updated.")
            @Validated
            @RequestBody
            AccountUpdateDTO accountUpdateDTO) {
        Account account = accountService.updateAccount(id, accountUpdateDTO);
        AccountDTO accountDTO = convertAccountToDTO(account);
        ResponseBody<AccountDTO> body = new ResponseBody<>(accountDTO);

        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Update an account password", description = "Update an account password by providing the account ID and the old and new passwords.")
    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updateAccountPassword(
            @Schema(description = "The ID of the account to update the password.", example = "1")
            @ValidatedAccountId
            @PathVariable
            Long id,
            @Schema(implementation = AccountPasswordUpdateDTO.class, description = "The old and new passwords of the account.")
            @Validated
            @RequestBody
            AccountPasswordUpdateDTO accountPasswordUpdateDTO) {
        accountService.updatePassword(id, accountPasswordUpdateDTO);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete an account", description = "Delete an account by providing the account ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(
            @Schema(description = "The ID of the account to be deleted.", example = "1")
            @ValidatedAccountId
            @PathVariable
            Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Count all accounts", description = "Count all accounts.")
    @GetMapping(value = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody<Map<String, Long>>> countAllAccounts() {
        Long count = accountService.countAllAccounts();
        Map<String, Long> data = new HashMap<>();

        data.put("count", count);

        ResponseBody<Map<String, Long>> body = new ResponseBody<>(data);

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
