package com.ashutosh.threadpool.work.interfaces;

public interface Work {
    public WorkMeta execute();
    public String getName();
    public void setTimeAddedToWorkQueue(long x);
    public long getTimeAddedToWorkQueue();

}
