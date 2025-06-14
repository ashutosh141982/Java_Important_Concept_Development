package com.ashutosh.threadpool.work.interfaces;

public class WorkMeta {

    WorkCallBack callbackImplmentedClass;
    public void registerCallback(WorkCallBack callbackImplmentedClass){
        this.callbackImplmentedClass=callbackImplmentedClass;

    }
    public void doCallBack(){
        if(null!=callbackImplmentedClass){
            callbackImplmentedClass.callMe();
        }
    }
}
