package com.ashutosh.threadpool.worker.implementation.level3;

import com.ashutosh.threadpool.work.interfaces.Work;
import com.ashutosh.threadpool.worker.implementation.level3.worker.Worker;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class MyL3ThreadPool {

    static MyL3ThreadPool pool;
    int state=0;
    ArrayBlockingQueue<Work> workStaggingQueue;
    ArrayBlockingQueue<Thread> worker;
    int maxWorkAllowed;
    AtomicInteger retryCount=new AtomicInteger(0);//Saturation handler will look for 3 count before thorwing exception
    int maxWorkerAllowed=0;
    ReentrantLock lock=new ReentrantLock();
    static{
        pool=new MyL3ThreadPool();
    }
    private MyL3ThreadPool(){
        if(null!=pool){
            throw new IllegalStateException("This is singletone class should not construct multiple times, already one instance present");
        }
    }
    public  static MyL3ThreadPool getInstance(){
        return pool;
    }
    public boolean initialize(int nosThreadInThreadPool, int maxWorkAllowed){//, List<Work> workList will allow user to add work as that is what required putting her may not be possible for user as they don't know at the time of initilization

        if(state==0){
            this.maxWorkAllowed=maxWorkAllowed;
            workStaggingQueue=new ArrayBlockingQueue<>(this.maxWorkAllowed,true);
            worker=new ArrayBlockingQueue<>(nosThreadInThreadPool);
            this.maxWorkerAllowed=nosThreadInThreadPool;
            prepareWorkerandAssociateWorkStagingArea();
            state=1;
            return true;
        }else{
            throw new IllegalStateException("Initilization already done, reisitialization not permitted");
        }
    }
    private void prepareWorkerandAssociateWorkStagingArea(){
        if(state==0) {
            for (int i =  this.maxWorkerAllowed; i>0; i--) {
                Worker t = new Worker(this.workStaggingQueue, "WORKER-" + i);
                this.worker.add(new Thread(t,"THREAD-"+t.getName()));
            }
        }
    }
    public void startWorker()  {

        if(state==1){
            for (int i = 0; i < this.maxWorkerAllowed; i++) {
                try {
                    this.worker.take().start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Workers started");
        }


    }

    /**
     *
     * @param work
     * @return if return value is false then should retry 3 times;
     */
    public boolean addWork(Work work) throws SaturationException{
        if(state>0){
            try {
                boolean success=this.workStaggingQueue.offer(work,2000, TimeUnit.MILLISECONDS);
                if(!success){
                    this.lock.lock();
                   new IdentifySaturation(this.workStaggingQueue,this.worker,this.maxWorkAllowed,3,this.maxWorkerAllowed,this.retryCount).run();
                   this.retryCount.getAndIncrement();
                   this.lock.unlock();
                    return success;
                }else{
                    retryCount.set(0);
                    return success;
                }


            } catch (InterruptedException e) {
                System.out.println("Interrupt exception while putting work["+work+"]"+e.getMessage());
                return false;
            }
        }else{
            throw new IllegalStateException("Initilization not done yet prefer calling initilize method first");
        }

    }
}
