package com.sx.locks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 资源类
 */
class MyCache{
    private volatile Map<String,Object> map = new HashMap<>();
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();


    public void put(String key, Object value){

        rwLock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t 正在写入:"+key);
            map.put(key,value);
            try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName()+"\t 写入成功;");
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            rwLock.writeLock().unlock();
        }
    }

    public void get(String key){
        rwLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t 正在获取:"+key);
            Object result = map.get(key);
            try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName()+"\t 获取成功:"+result);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            rwLock.readLock().unlock();
        }
    }
}
/**
 * 读写锁
 * 多个资源类同时读一个资源类没有任何问题,所以为了满足并发量,读取共享资源应该可以同时进行.
 * 但是
 * 如果一个线程想去写共享资源类,就不应该再有其他线程对该资源类进行读或写
 * 小总结:
 *      读-读:能共存
 *      读-写:不能共存
 *      写-写:不能共存
 *      写操作:原子+独占;整个过程必须是一个整体完成,不许分割
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 0; i < 5; i++) {
            final int intTemplet = i;
            new Thread(() -> {
                myCache.put(intTemplet+"",intTemplet+"");
            },String.valueOf(i)).start();
        }
        for (int i = 0; i < 5; i++) {
            final int intTemplet = i;
            new Thread(() -> {
                myCache.get(intTemplet+"");
            },String.valueOf(i)).start();
        }
    }
}
