package com.ashutosh.resourcepooling.connection.DB;

import com.ashutosh.resourcepooling.PoolingResource;
import com.ashutosh.resourcepooling.util.SequenceGenerator;

public class DBConnectionProxy implements PoolingResource {
    private final int instanceIndex= SequenceGenerator.nextId();
    @Override
    public void closeConnection() {

    }

    @Override
    public void openConnection() {

    }

    @Override
    public int getIndex() {
        return this.instanceIndex;
    }
}
