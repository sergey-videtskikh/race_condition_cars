package com.plakhov.account;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AccountDto {
    private final long id;
    private int balance;
    private final Lock lock = new ReentrantLock();

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

    public long getId() {
        return id;
    }

    public Lock getLock() {
        return lock;
    }
}
