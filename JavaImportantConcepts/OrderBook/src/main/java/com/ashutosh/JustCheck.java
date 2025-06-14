package com.ashutosh;

import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

public class JustCheck {
    public static void main(String[] args) {
        //LinkedHashMap<String,SQLResult> cache
    }
    private class SQLResult{
        private String result;
        private String query;
        private long timeFetched;

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
    }
}
