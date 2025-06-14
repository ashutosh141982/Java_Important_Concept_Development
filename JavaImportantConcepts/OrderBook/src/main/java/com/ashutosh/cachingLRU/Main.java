package com.ashutosh.cachingLRU;

public class Main {
    public static void main(String[] args) {
        DBQueryCache cache = new DBQueryCache(3); // cache size = 3

        System.out.println(cache.getQueryResult("SELECT * FROM users WHERE id=1"));
        System.out.println(cache.getQueryResult("SELECT * FROM orders WHERE user_id=1"));
        System.out.println(cache.getQueryResult("SELECT * FROM products"));

        // Access again (should hit cache)
        System.out.println(cache.getQueryResult("SELECT * FROM users WHERE id=1"));

        // Add one more query → should evict LRU
        System.out.println(cache.getQueryResult("SELECT * FROM categories"));

        // Check eviction
        System.out.println(cache.getQueryResult("SELECT * FROM orders WHERE user_id=1")); // likely evicted
    }
}
