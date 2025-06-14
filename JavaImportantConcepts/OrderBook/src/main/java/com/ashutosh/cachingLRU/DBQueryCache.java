package com.ashutosh.cachingLRU;

import java.util.LinkedHashMap;
import java.util.Map;

public class DBQueryCache extends LinkedHashMap<String, String> {
    private final int capacity;

    public DBQueryCache(int capacity) {
        // accessOrder = true → enables LRU behavior
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
        return size() > capacity;
    }

    // Simulate a DB query (slow)
    private String fetchFromDatabase(String query) {
        try { Thread.sleep(100); } catch (InterruptedException ignored) {}
        return "Result for: " + query;
    }

    public String getQueryResult(String query) {
        String result = get(query);
        if (result != null) {
            System.out.println("Cache HIT for: " + query);
            return result;
        }

        System.out.println("Cache MISS for: " + query);
        result = fetchFromDatabase(query);
        put(query, result);
        return result;
    }
}
