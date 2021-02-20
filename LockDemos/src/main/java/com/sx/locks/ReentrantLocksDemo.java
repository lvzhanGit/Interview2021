package com.sx.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Phone
{
    public synchronized void sendMSG() throws Exception
    {
        System.out.println(Thread.currentThread().getName()+"\t ******发送短信");
        sendEmail();
    }

    public synchronized void sendEmail() throws Exception
    {
        System.out.println(Thread.currentThread().getName()+"\t ******发送邮件");
    }
}


/**
 * 可重入锁
 * 指的是同一线程外层函数获得锁之后,内层递归函数仍能获取该锁的代码;
 * 在同一个线程在外层方法获取锁的时候,在进入内层方法会自动获取锁
 */
public class ReentrantLocksDemo {

    public static void main(String[] args) {
        Phone phone = new Phone();

        new Thread(() -> {
            try {
                phone.sendMSG();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"AA").start();

        new Thread(() -> {
            try {
                phone.sendMSG();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"BB").start();

        System.out.println("");
        System.out.println("");
        System.out.println("");

        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try{
            send();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }

    }

    public static void send(){
        System.out.println("***********发送");
    }

}
