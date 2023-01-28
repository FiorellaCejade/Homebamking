package com.mindhub.homebaking.service;

import com.mindhub.homebaking.models.Account;

import java.util.List;

public interface AccountService {

    List<Account> getAccounts();

    Account getAccountById(Long id);

    void saveAccount(Account account);

    Account getAccountByNumber(String number);


}
