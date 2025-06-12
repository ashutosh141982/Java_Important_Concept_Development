package com.ashutosh.pool.polling;

import com.ashutosh.pool.poollingclass.ObjectPolling;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public final class ObjectPoolManager {
    static ObjectPoolManager manager;



    int poolSize=0;
    int state=0;///State need to in 3 that its ready to be used
    public int getTotalPoolSize() {
        return poolSize;
    }
    public int getPoolSizeClass(Class className){
        return this.availablePool.get(className).size();
    }
    public boolean isReady(){
        return (state==3?true:false);
    }
    HashMap<Class,ArrayBlockingQueue<ObjectPolling>> availablePool;
    ConcurrentHashMap<ObjectPolling,ObjectPolling> inUsePool;
    static{
        manager=new ObjectPoolManager();
    }
    public static ObjectPoolManager getInstance(){
        return manager;
    }
    private ObjectPoolManager(){
    this.availablePool=new HashMap<Class,ArrayBlockingQueue<ObjectPolling>>();
    this.inUsePool=new ConcurrentHashMap<ObjectPolling,ObjectPolling>();
    }
    private boolean addToMap(ObjectPolling obj,int nosObjects,HashMap<Class,ArrayBlockingQueue<ObjectPolling>> map){
        Class className=obj.getClass();
        ArrayBlockingQueue<ObjectPolling> mappedQueue=map.get(className);
        if(null==mappedQueue){
            synchronized (this){
                mappedQueue=map.get(className);
                if(null==mappedQueue) {
                    mappedQueue = new ArrayBlockingQueue<ObjectPolling>(nosObjects);
                    mappedQueue.add(obj);//IllegalStateException can be thrown if capacity is exhausted;
                    map.put(className,mappedQueue);
                }else {
                    mappedQueue.add(obj);
                }

            }
        }else{
            synchronized (this){
                mappedQueue.add(obj);

            }
        }
        return true;
    }
    public ObjectPoolManager add(ObjectPolling obj,int nosObjects) throws IllegalStateException,NullPointerException{
        if(this.state==3){
            throw new IllegalStateException("Pool Initialize and now NO new pool addintion is permitted");
        }
            addToMap(obj,nosObjects,this.availablePool);

        poolSize++;
        return manager;
    }
    public boolean initialize(){

        if(this.state==2){
            /*Set<Class> key=this.availablePool.keySet();
            for(Class k : key){
                this.inUsePool.put(k,new ArrayBlockingQueue<>(this.availablePool.get(k).size()));
            }*/
            this.state=3;
            return true;
        }

        return false;

    }
    public ObjectPolling getFromPool(Class className) throws InterruptedException {
        ObjectPolling tobeReturned=this.availablePool.get(className).take();


            this.inUsePool.put(tobeReturned,tobeReturned);///not sure if lock is required or i can directly use add method will revisit latter.


        return tobeReturned;
    }

    /**
     *
     * @param obj
     * @return
     * null return will indicate the object is successfully returned to pool
     */
    public ObjectPolling cacheToPool(ObjectPolling obj){

        /**
         * This can be deligated to a thread to process and return response asynchronously;
         */
        try{
            obj.clean();
            //First remove from inprogress and then return back to avaialblepool

                this.inUsePool.remove(obj);
                returnToMap(obj,this.availablePool);



        }catch(Exception e){
            System.out.println("Exception will putting back object to pool"+e.getMessage());
            return obj;
        }
        return null;
    }
    public CompletableFuture cacheToPoolAsynchronously(ObjectPolling obj){

        /**
         * This can be deligated to a thread to process and return response asynchronously;
         */
        CompletableFuture<String> completableFuture=new CompletableFuture<>();
        cacheToPoolThreaded(obj,completableFuture);
        return completableFuture;
    }
    private void returnToMap(ObjectPolling obj,HashMap<Class,ArrayBlockingQueue<ObjectPolling>> map){

        map.get(obj.getClass()).offer(obj);

    }
    private void cacheToPoolThreaded(ObjectPolling obj,CompletableFuture completableFuture1){
        CompletableFuture<String> completableFuture=completableFuture1;
        Object objParent=this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                obj.clean();
                //First remove from inprogress and then return back to avaialblepool
                System.out.println(Thread.currentThread().getName()+"After cleaning==="+obj.toString());
                ((ObjectPoolManager)objParent).inUsePool.remove(obj);
                returnToMap(obj,((ObjectPoolManager)objParent).availablePool);
                completableFuture.complete("Done Returning");
            }
        }).start();
    }
}
