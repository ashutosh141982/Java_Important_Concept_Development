package com.ashutosh.resourcepooling;

public interface PoolingResource {
    public void closeConnection();
    public void openConnection();
    public int getIndex();
}
