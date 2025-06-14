package com.ashutosh.mainImplementation.l3;

import com.ashutosh.threadpool.work.interfaces.Work;
import com.ashutosh.threadpool.work.interfaces.WorkMeta;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SimpleTestWork3 implements Work {
    String workName;
    long timeAddedToWorkQueue;
    public SimpleTestWork3(String workName){
        this.workName=workName;
    }
    @Override
    public WorkMeta execute() {
        String s="Work"+this.workName+" started"+Thread.currentThread().getName();
        System.out.println(s);
        int x=(new Random()).nextInt(0,20)%4;
        if(x==0){
            System.out.println(this.workName + "====================Waiting indefinitly ===========" + Thread.currentThread().getName());
            while(true) {
                try {


                    TimeUnit.MILLISECONDS.sleep(2000);
                    //Thread.sleep(2000);
                   // System.out.println(this.workName + "====================AWAKE ===========" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    System.out.println("=======Exception happened Intrrupt[" + this.workName + "]=>" + e.getMessage());
                }
            }
        }
        s=this.workName+"Work Completed"+Thread.currentThread().getName();
        System.out.println(s);
        WorkMeta returnVal=new WorkMeta();
        return returnVal;
    }

    @Override
    public String getName() {
        return this.workName;
    }

    @Override
    public void setTimeAddedToWorkQueue(long x) {
        this.timeAddedToWorkQueue=x;
    }

    @Override
    public long getTimeAddedToWorkQueue() {

        return this.timeAddedToWorkQueue;
    }
}
