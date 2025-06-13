package com.ashutosh.resourcepooling.connection.pooling;

import com.ashutosh.resourcepooling.PoolingResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class ResourcePoolManager {
    private static ResourcePoolManager manager;
    ArrayBlockingQueue<PoolingResource> availableResource;
    ArrayList<PoolingResource> inProgress;
    int poolSize=0;
    int state=0;

    public ResourcePoolManager() {
    }

    /*static{
            manager=new ResourcePoolManager();
        }
        public static ResourcePoolManager getInstance(){
            manager.state=1;
            return manager;
        }*/
    public boolean initialize(Class resource,int poolSize){
        if(this.state==1){
            this.availableResource=new ArrayBlockingQueue<>(poolSize);
            for(int i=0;i<poolSize;i++){
                PoolingResource reso= ResourceFactory.getInstance().getResource(resource);
                this.availableResource.add(reso);
            }
            this.inProgress=new ArrayList<>(poolSize);
            this.state=2;
            return true;

        }
        return false;
    }
    public PoolingResource getResourceFromPool() throws InterruptedException {
        PoolingResource pr=this.availableResource.take();
        synchronized (this.inProgress){
            this.inProgress.set(pr.getIndex(),pr);
        }

        return pr;

    }
    public boolean returnbackResourceToPool(PoolingResource pr) throws InterruptedException {
        synchronized (this.inProgress){
            this.inProgress.set(pr.getIndex(),null);
        }
        this.availableResource.put(pr);
        return true;
    }
    public boolean handleIssueWithResourceWhileProcessing(PoolingResource pr){
        synchronized (this.inProgress){
            this.inProgress.set(pr.getIndex(),null);
        }
        PoolingResource reso= ResourceFactory.getInstance().getResource(pr.getClass());
        return this.availableResource.add(reso);
    }

}
