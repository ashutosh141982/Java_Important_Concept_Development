package com.ashutosh.cachingLRU;

public class SQLResult {
    private String result;
    private String query;
    private long timeFetched;
    private long ttl;

    public SQLResult(String query,long ttl,String result,long timeFetched){
        this.query=query;
        this.ttl=ttl;
        this.result=result;
        this.timeFetched=timeFetched;
    }
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public long getTimeFetched() {
        return timeFetched;
    }

    public void setTimeFetched(long timeFetched) {
        this.timeFetched = timeFetched;
    }
    public boolean isExpired(){
        return(this.timeFetched+this.ttl)<System.currentTimeMillis();
    }
}

