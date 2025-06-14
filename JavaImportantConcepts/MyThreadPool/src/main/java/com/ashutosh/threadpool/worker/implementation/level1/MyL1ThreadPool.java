package com.ashutosh.threadpool.worker.implementation.level1;

import com.ashutosh.threadpool.work.interfaces.Work;


import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class MyL1ThreadPool {

    static MyL1ThreadPool pool;
    int state=0;

    ArrayBlockingQueue<Thread> worker;
    static{
        pool=new MyL1ThreadPool();
    }
    private MyL1ThreadPool(){
        if(null!=pool){
            throw new IllegalStateException("This is singletone class should not construct multiple times, already one instance present");
        }
    }
    public  static MyL1ThreadPool getInstance(){
        return pool;
    }
    public boolean initialize(int nosThreadInThreadPool){//, List<Work> workList will allow user to add work as that is what required putting her may not be possible for user as they don't know at the time of initilization


        if(state==0){
            synchronized (this){
                if(state==0){
                    worker=new ArrayBlockingQueue<>(nosThreadInThreadPool);

                    state=1;
                    return true;
                }else{
                    throw new IllegalStateException("Initilization already done, reisitialization not permitted");
                }

            }


        }else{
            throw new IllegalStateException("Initilization already done, reisitialization not permitted");
        }
    }
    public Thread getOneThreadFromPool() throws InterruptedException {
        return worker.take();

    }
    public boolean returnThreadToPool(Thread t){
        return worker.offer(t);
    }

}
