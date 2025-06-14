package com.ashutosh.cachingLRU;

import java.util.*;

public class CachedLruTtl {
    private CacheLRU cache;
    private long ttl;
    public CachedLruTtl(int capacity,float loadfactor,long ttl){
        this.cache=new CacheLRU(capacity,loadfactor);
        this.ttl=ttl;
    }
private class CacheLRU extends LinkedHashMap<String,SQLResult> {
    int capacity;
    public CacheLRU(int capacity,float loadfactor){

        super(capacity,loadfactor,true);
        this.capacity=capacity;
    }
    @Override
    protected boolean removeEldestEntry(Map.Entry eldest){
      return size()>capacity;
    }
}
public  SQLResult get(String query){
    synchronized (this) {
        SQLResult result = this.cache.get(query);
        //if(null==result) return null;
        if (null == result) {
            result = fetchFromDB(query);
            result.setResult(result.getResult()+"Fresh fetch");
            this.cache.put(query, result);
        } else if (result.isExpired()) {
            this.cache.remove(query);
            result = fetchFromDB(query);
            result.setResult(result.getResult()+"Fresh fetch======After EXPIRY");
            this.cache.put(query, result);
        }else{
            result.setResult(result.getResult()+"CACHE HIT=========================================================");
        }

        return result;
    }
}
public SQLResult fetchFromDB(String sql){
    try {
        Thread.sleep(20);
    } catch (InterruptedException e) {
        //throw new RuntimeException(e);
    }
    return new SQLResult(sql,this.ttl,sql+"---Result",System.currentTimeMillis());
}
public  void cleanupCacheTTL(){
    synchronized (this) {
        Iterator<Map.Entry<String,SQLResult>> it=this.cache.entrySet().iterator();
        List<SQLResult> newResultList=new ArrayList<>();
        while(it.hasNext()) {
            Map.Entry<String,SQLResult> entry=it.next();
            SQLResult result = entry.getValue();
            if (result.isExpired()) {

                System.out.println("CLEANUP===============EXPIRED================="+result.getQuery());
                it.remove();
                newResultList.add( fetchFromDB(result.getQuery()));



                //this.cache.put(result.getQuery(), result);

            }
        }
        for(int i=0;i<newResultList.size();i++){
            this.cache.put(newResultList.get(i).getQuery(),newResultList.get(i));
        }
    }


}
}
