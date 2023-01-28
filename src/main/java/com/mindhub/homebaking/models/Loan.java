package com.mindhub.homebaking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ElementCollection
    private List<Integer> payments = new ArrayList<>();

    @OneToMany(mappedBy="loan", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientLoan = new HashSet<>();
    private String name;
    private int maxAmount;

    private double loanPercentaje;

    public Loan(){ }

    public Loan (String name, int maxAmount, List<Integer> payments, double loanPercentaje){
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.loanPercentaje = loanPercentaje;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLoanPercentaje() {
        return loanPercentaje;
    }

    public void setLoanPercentaje(double loanPercentaje) {
        this.loanPercentaje = loanPercentaje;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Set<ClientLoan> getClientLoan() {
        return clientLoan;
    }

    public void setClientLoan(Set<ClientLoan> clientLoan) {
        this.clientLoan = clientLoan;
    }

}
