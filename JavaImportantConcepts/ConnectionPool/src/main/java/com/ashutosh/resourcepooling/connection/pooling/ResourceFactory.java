package com.ashutosh.resourcepooling.connection.pooling;

import com.ashutosh.resourcepooling.PoolingResource;
import com.ashutosh.resourcepooling.connection.DB.DBConnectionProxy;
import com.ashutosh.resourcepooling.connection.MQ.MQConnectionProxy;

public class ResourceFactory {
    private static final ResourceFactory factory;
    static{
        factory=new ResourceFactory();
    }
    private ResourceFactory(){}
    public static ResourceFactory getInstance(){
        return factory;
    }
    //Factory implementation for the resources.
    public PoolingResource getResource(Class cls){
        if(cls== DBConnectionProxy.class){
            new DBConnectionProxy();
        }else if(cls== MQConnectionProxy.class){
            new MQConnectionProxy();
        }
        return null;
    }
}
