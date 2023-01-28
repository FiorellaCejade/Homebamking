package com.mindhub.homebaking.service.serviceImplementation;

import com.mindhub.homebaking.models.Loan;
import com.mindhub.homebaking.repositories.LoanRepository;
import com.mindhub.homebaking.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;


    @Override
    public List<Loan> getLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Loan getLoanById( Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public void saveLoan(Loan loan) {
        loanRepository.save(loan);
    }
}
