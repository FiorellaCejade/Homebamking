package com.mindhub.homebaking.service;

import com.mindhub.homebaking.models.Loan;

import java.util.List;

public interface LoanService {

    List<Loan> getLoans();

    Loan getLoanById(Long id);

    void saveLoan( Loan loan);
}
