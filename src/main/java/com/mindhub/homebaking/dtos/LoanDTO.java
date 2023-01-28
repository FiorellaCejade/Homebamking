package com.mindhub.homebaking.dtos;

import com.mindhub.homebaking.models.Loan;

import java.util.List;

public class LoanDTO {

    private long id;
    private String name;
    private int maxAmount;

    private double loanPercentaje;
    private List<Integer> payments;

    public LoanDTO ( Loan loan){
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
        this.loanPercentaje = loan.getLoanPercentaje();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public double getLoanPercentaje() {
        return loanPercentaje;
    }
}
