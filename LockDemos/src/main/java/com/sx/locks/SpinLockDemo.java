package com.sx.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自旋锁
 * 自旋锁好处:循环获取锁直到成功,没有类似wait的阻塞
 * 通过CAS操作完成自旋锁,A线程先进来调动myLock方法自己持有5秒钟,B随后进来后发现
 * 当前有线程持有锁,不是Null,所以只能通过自旋等待,直到A释放锁后B随后捡到
 */
public class SpinLockDemo {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"\t Come in ^_^");
        while (!atomicReference.compareAndSet(null,thread)){

        }
    }

    public void unMyLock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName()+"\t invoke unMyLock");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(() -> {
            spinLockDemo.myLock();;
            try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
            spinLockDemo.unMyLock();
        },"A").start();

        new Thread(() -> {
            spinLockDemo.myLock();;
            spinLockDemo.unMyLock();
        },"B").start();
    }

}
