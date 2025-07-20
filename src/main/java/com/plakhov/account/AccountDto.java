package com.plakhov.account;

public class AccountDto {
    private long id;
    private int balance;

    public AccountDto(long id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }
}
