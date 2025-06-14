package com.ashutosh.threadpool.worker.implementation.level3;

import com.ashutosh.threadpool.work.interfaces.Work;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class IdentifySaturation {
    ArrayBlockingQueue<Work> workStaggingQueue;
    ArrayBlockingQueue<Thread> worker;
    int maxWorkAllowed;
    int maxRetryCount=0;
    int maxWorkerAllowed;
    int currentRetryCount=0;
    public IdentifySaturation(ArrayBlockingQueue<Work> workStaggingQueue, ArrayBlockingQueue<Thread> worker, int maxWorkAllowed, int maxRetryCount, int maxWorkerAllowed, AtomicInteger currentRetryCount){
        this.worker=worker;
        this.workStaggingQueue=workStaggingQueue;
        this.maxWorkAllowed=maxWorkAllowed;
        this.maxRetryCount=maxRetryCount;
        this.maxWorkerAllowed=maxWorkerAllowed;
        this.currentRetryCount=currentRetryCount.get();
    }

    public void run() {
            if(this.workStaggingQueue.remainingCapacity()==0 && this.worker.remainingCapacity()==this.maxWorkerAllowed){
                int totalWorkSaturation=0;
                for(int i=this.workStaggingQueue.size();i>0;i--){
                    Work work=this.workStaggingQueue.peek();
                    long workinsertTime=work.getTimeAddedToWorkQueue();
                    long currentTime=System.currentTimeMillis();
                    if(currentTime-workinsertTime>2000){
                        totalWorkSaturation++;

                    }
                }
                if(totalWorkSaturation==this.workStaggingQueue.size()){
                    System.out.println("Saturation suspected");
                    if(this.currentRetryCount>0 && this.currentRetryCount==this.maxRetryCount){
                        throw new SaturationException("Thread Saturation identified, NO more work can be accepted and current work not finishing");
                    }

                }
            }
    }
}
