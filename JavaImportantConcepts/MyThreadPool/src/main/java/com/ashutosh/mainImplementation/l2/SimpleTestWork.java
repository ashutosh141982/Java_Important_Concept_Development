package com.ashutosh.mainImplementation.l2;

import com.ashutosh.threadpool.work.interfaces.Work;
import com.ashutosh.threadpool.work.interfaces.WorkMeta;

import java.util.Random;

public class SimpleTestWork implements Work {
    String workName;
    public SimpleTestWork(String workName){
        this.workName=workName;
    }
    @Override
    public WorkMeta execute() {
        String s="Work"+this.workName+" started"+Thread.currentThread().getName();
        System.out.println(s);
        int x=(new Random()).nextInt(0,20)%4;
        if(x==0){
            try {
                System.out.println(this.workName+"====================Waiting ==========="+Thread.currentThread().getName());
                Thread.sleep(2000);
                System.out.println(this.workName+"====================AWAKE ==========="+Thread.currentThread().getName());
            } catch (InterruptedException e) {
                System.out.println("=======Exception happened Intrrupt["+this.workName+"]=>"+e.getMessage());
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
}
