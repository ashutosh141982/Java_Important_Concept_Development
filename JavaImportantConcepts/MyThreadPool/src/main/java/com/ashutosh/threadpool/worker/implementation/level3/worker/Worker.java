package com.ashutosh.threadpool.worker.implementation.level3.worker;

import com.ashutosh.threadpool.work.interfaces.Work;

import java.util.concurrent.ArrayBlockingQueue;

public class Worker implements Runnable {
    ArrayBlockingQueue<Work> work;
    int keepAlive=0;
    String name;


    public  Worker(ArrayBlockingQueue<Work> work,String name){
        this.work=work;
        this.name=name;
        this.keepAlive=1;
    }
    private Worker(){
        throw new UnsupportedOperationException("Instantiation not allowed");
    }
    @Override
    public void run() {
        System.out.println("Thread---"+this.name+" STARTED====================================");
        while(this.keepAlive==1){
            try {
                this.work.take().execute();
            } catch (InterruptedException e) {
                e.printStackTrace();/// can implement intimation to actual program of what happened.
            }
        }

    }
    public String getName(){
        return this.name;
    }
}
