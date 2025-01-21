
package com.example.account.management.service;

import com.example.account.management.exception.ResourceAlreadyExistsException;
import com.example.account.management.exception.ResourceNotFoundException;
import com.example.account.management.model.Account;
import com.example.account.management.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) {
        accountRepository
                .findById(account.getId())
                .ifPresent(a -> {
                    throw new ResourceAlreadyExistsException(String.format("Account with id %s already exists", a.getId()));
                });

        accountRepository
                .findByEmail(account.getEmail())
                .ifPresent(a -> {
                    throw new ResourceAlreadyExistsException(String.format("Account with email %s already exists.", a.getEmail()));
                });

        return accountRepository.save(account);
    }

    public Account getAccountById(Long id) {
        return accountRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found."));
    }

    public Account getAccountByEmail(String email) {
        return accountRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found."));
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account updateAccount(Long id, Account accountDetails) {
        Account account = getAccountById(id);

        account.setName(accountDetails.getName());
//        account.setEmail(accountDetails.getEmail());
        account.setPassword(accountDetails.getPassword());

        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        Account account = getAccountById(id);
        accountRepository.delete(account);
    }

    public Long countAllAccounts() {
        return accountRepository.count();
    }
}
