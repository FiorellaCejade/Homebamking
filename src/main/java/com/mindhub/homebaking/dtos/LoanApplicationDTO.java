package com.mindhub.homebaking.dtos;

import java.util.List;

public class LoanApplicationDTO {

    private long id;
    private double amount;
    private int payment;
    private String accounDestino;

    public LoanApplicationDTO(long id, double amount, int payment,String accounDestino) {
       this.id = id;
       this.amount = amount;
       this.payment = payment;
       this.accounDestino = accounDestino;
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayment() {
        return payment;
    }

    public String getAccounDestino() {
        return accounDestino;
    }
}
