package com.sx.locks;

import com.sx.enums.CountryEnum;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 倒计时锁
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch lock = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println(Thread.currentThread().getName()+" \t 国被灭");
                lock.countDown();
            }, CountryEnum.getCountry(i).getCountry()).start();
        }
        lock.await();
        System.out.println(Thread.currentThread().getName()+"\t 秦统一");
        System.out.println(CountryEnum.ONE.getCountry());
    }
}
