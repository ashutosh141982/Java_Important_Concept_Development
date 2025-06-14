package com.ashutosh.threadpool.worker.implementation.level2;

import com.ashutosh.threadpool.work.interfaces.Work;
import com.ashutosh.threadpool.worker.implementation.level2.worker.Worker;

import java.util.concurrent.ArrayBlockingQueue;

public class MyL2ThreadPool {

    static MyL2ThreadPool pool;
    int state=0;
    ArrayBlockingQueue<Work> workStaggingQueue;
    ArrayBlockingQueue<Thread> worker;
    static{
        pool=new MyL2ThreadPool();
    }
    private MyL2ThreadPool(){
        if(null!=pool){
            throw new IllegalStateException("This is singletone class should not construct multiple times, already one instance present");
        }
    }
    public  static MyL2ThreadPool getInstance(){
        return pool;
    }
    public boolean initialize(int nosThreadInThreadPool, int maxWorkAllowed){//, List<Work> workList will allow user to add work as that is what required putting her may not be possible for user as they don't know at the time of initilization

        if(state==0){
            workStaggingQueue=new ArrayBlockingQueue<>(maxWorkAllowed,true);
            worker=new ArrayBlockingQueue<>(nosThreadInThreadPool);
            prepareWorkerandAssociateWorkStagingArea();
            state=1;
            return true;
        }else{
            throw new IllegalStateException("Initilization already done, reisitialization not permitted");
        }
    }
    private void prepareWorkerandAssociateWorkStagingArea(){
        if(state==0) {
            for (int i = worker.remainingCapacity(); i>0; i--) {
                Worker t = new Worker(this.workStaggingQueue, "WORKER-" + i);
                this.worker.add(new Thread(t,"THREAD-"+t.getName()));
            }
        }
    }
    public void startWorker()  {

        if(state==1){
            for (int i = 0; i < worker.size(); i++) {
                try {
                    this.worker.take().start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Workers started");
        }


    }
    public void addWork(Work work){
        if(state>0){
            try {
                this.workStaggingQueue.put(work);
            } catch (InterruptedException e) {
                System.out.println("Interrupt exception while putting work"+work);
            }
        }else{
            throw new IllegalStateException("Initilization not done yet prefer calling initilize method first");
        }

    }
}
