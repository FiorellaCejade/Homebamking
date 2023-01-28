package com.mindhub.homebaking.service.serviceImplementation;

import com.mindhub.homebaking.models.Account;
import com.mindhub.homebaking.repositories.AccountRepository;
import com.mindhub.homebaking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<Account> getAccounts(){
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);

    }
    @Override
    public Account getAccountByNumber(String number) {
        return accountRepository.findByNumber(number);
    }


}
