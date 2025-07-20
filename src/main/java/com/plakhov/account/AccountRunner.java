package com.plakhov.account;

import java.util.List;
import java.util.stream.Stream;

public class AccountRunner {
    public static void main(String[] args) throws InterruptedException {
        AccountDto account1 = new AccountDto(1, 1000);
        AccountDto account2 = new AccountDto(2, 1000);

        List<Integer> amounts = Stream.generate(() -> (int) (Math.random() * 1100)).limit(100000).toList();
        System.out.println("amounts.size = " + amounts.size());
        System.out.println("account1 = " + account1);
        System.out.println("account2 = " + account2);

        for (Integer amount : amounts) {
            transfer(account1, account2, amount);
        }

        Thread thread2 = new Thread(() -> {
            for (Integer amount : amounts) {
                transfer(account2, account1, amount);
            }
        });
        Thread thread3 = new Thread(() -> {
            for (Integer amount : amounts) {
                transfer(account2, account1, amount);
            }
        });
        Thread thread4 = new Thread(() -> {
            for (Integer amount : amounts) {
                transfer(account1, account2, amount);
            }
        });
        thread2.start();
        thread3.start();
        thread4.start();
        thread2.join();
        thread3.join();
        thread4.join();


        System.out.println("account1 = " + account1);
        System.out.println("account2 = " + account2);

    }

    private static void transfer(AccountDto from, AccountDto to, int amount) {
        Object monitor1 = from.getId() < to.getId() ? from : to;
        Object monitor2 = from.getId() >= to.getId() ? from : to;
        synchronized (monitor1) {
            synchronized (monitor2) {
                from.withdraw(amount);
                to.deposit(amount);
            }
        }
    }
}
