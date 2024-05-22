package me.dio.domain.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import me.dio.service.exception.BusinessException;

@Entity(name = "tb_account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String number;

    private String agency;

    @Column(precision = 13, scale = 2)
    private BigDecimal balance;

    @Column(name = "additional_limit", precision = 13, scale = 2)
    private BigDecimal limit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }
    public void transfer(double value, Account destinationAccount){
        if(value > balance.toBigInteger().doubleValue()){
            throw new BusinessException("Saldo insuficiente para realizar a transferência.");
        }
        if(destinationAccount == null){
            throw new BusinessException("A conta destino não existe.");
        }
        this.withdraw(value);
        destinationAccount.deposit(value);
    }

    private void deposit(double value) {
        balance = balance.add(BigDecimal.valueOf(value));
    }

    private void withdraw(double value) {
        balance = balance.subtract(BigDecimal.valueOf(value));
    }
}
