package com.ashutosh.pool.poollingclass;

/**
 * This need to be implemented to get clean() implemented by the class to be pooled, so that Object Pooling manager can clean the object before marking it to be reused.
 * Initillizing the object with values is up to the implementer, as implementer only know what and when(what sequence) the calues need to be populated.
 */
public interface ObjectPolling {
    public void clean();
}
