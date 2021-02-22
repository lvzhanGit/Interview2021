package com.sx.locks;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 *CyclicBarrier环形屏障
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,() -> {
            System.out.println(Thread.currentThread().getName()+"\t 召唤神龙");
        });
        for (int i = 1; i <= 7; i++) {
            final int intTemp = i;
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName()+" \t 集齐第"+intTemp+"颗龙珠");
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },"ThreadName").start();
        }
    }
}
