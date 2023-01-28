package com.mindhub.homebaking.dtos;

import com.mindhub.homebaking.models.ClientLoan;

import java.time.LocalDateTime;

public class ClientLoanDTO {

    private long clientId;
    private long loanId;

    private String loanName;
    private double amount;
    private int payment;

    private LocalDateTime creationDate;

    public ClientLoanDTO (ClientLoan clientLoan) {
        this.clientId = clientLoan.getClient().getId();
        this.loanId = clientLoan.getLoan().getId();
        this.loanName = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payment = clientLoan.getPayments();
        this.creationDate = clientLoan.getCreationDate();

    }

    public long getClientId() {
        return clientId;
    }

    public long getLoanId() {
        return loanId;
    }

    public String getLoanName() {
        return loanName;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayment() {
        return payment;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }




}