package com.ashutosh.resourcepooling.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SequenceGenerator {
    private static final AtomicInteger sequence = new AtomicInteger(1); // starts from 1

    public static synchronized int nextId() {
        return sequence.getAndIncrement();
    }
}
