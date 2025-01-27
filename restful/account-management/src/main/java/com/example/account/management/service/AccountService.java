
package com.example.account.management.service;

import com.example.account.management.exception.ResourceAlreadyExistsException;
import com.example.account.management.exception.ResourceNotFoundException;
import com.example.account.management.model.dto.AccountCreateDTO;
import com.example.account.management.model.dto.AccountPasswordUpdateDTO;
import com.example.account.management.model.dto.AccountUpdateDTO;
import com.example.account.management.model.entity.Account;
import com.example.account.management.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account createAccount(AccountCreateDTO accountCreateDTO) {
        accountRepository
                .findByEmail(accountCreateDTO.getEmail())
                .ifPresent(a -> {
                    throw new ResourceAlreadyExistsException(String.format("Account with email %s already exists.", a.getEmail()));
                });

        Account account = new Account();

        account.setName(accountCreateDTO.getName());
        account.setEmail(accountCreateDTO.getEmail());
        // encryption password
        String encodedPassword = passwordEncoder.encode(accountCreateDTO.getPassword());
        account.setPassword(encodedPassword);

        return accountRepository.save(account);
    }

    public Account getAccountById(Long id) {
        return accountRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Account with id %s is not found.", id)));
    }

    public Account getAccountByEmail(String email) {
        return accountRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Account with email %s is not found.", email)));
    }

//    public List<Account> getAllAccounts() {
//        return accountRepository.findAll();
//    }

    public Page<Account> getAccountsWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return accountRepository.findAll(pageable);
    }

    public void updatePassword(Long id, AccountPasswordUpdateDTO accountPasswordUpdateDTO) {
        // check account exists
        Account account = getAccountById(id);

        // check old password
        if (!passwordEncoder.matches(accountPasswordUpdateDTO.getOldPassword(), account.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect.");
        }

        // encrypt new password
        String encodedPassword = passwordEncoder.encode(accountPasswordUpdateDTO.getNewPassword());

        // update account
        account.setPassword(encodedPassword);
        accountRepository.save(account);
    }

    public Account updateAccount(Long id, AccountUpdateDTO accountUpdateDTO) {
        Account account = getAccountById(id);

        if (accountUpdateDTO.getName() != null) {
            account.setName(accountUpdateDTO.getName());
        }

        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        accountRepository
                .findById(id)
                .ifPresent(a -> {
                    accountRepository.delete(a);
                });
    }

    public Long countAllAccounts() {
        return accountRepository.count();
    }
}
