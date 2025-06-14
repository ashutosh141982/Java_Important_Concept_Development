package com.ashutosh.cachingLRU;

import java.util.Scanner;

public class MainCacheLruTtl {
   static boolean keepRunning=true;
    public static void main(String[] args) {
        CachedLruTtl cacheImplementationSQL=new CachedLruTtl(5,0.8f,200);

        String[] sql1=new String[]{"select * from emp;",
                "select * from dpt;",        "select * from sal;",        "select * from address;",
                "select * from phone;",        "select * from hr;",        "select * from emp;",        "select * from emp;"};
                ;
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                while(MainCacheLruTtl.keepRunning){
                    cacheImplementationSQL.cleanupCacheTTL();
                    try{
                        Thread.sleep(100);
                    }catch(InterruptedException ie){

                    }
                }
            }
        });
        t.start();
        Thread t11=new Thread(new Runnable() {
            @Override
            public void run() {
                while(MainCacheLruTtl.keepRunning){
                    for(int i=0;i<sql1.length;i++){
                        System.out.println(sql1[i]);
                        System.out.println(cacheImplementationSQL.get(sql1[i]).getResult());
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        //throw new RuntimeException(e);
                    }

                }
            }
        });
        t11.start();
        Scanner sc = new Scanner(System.in);
        int number = sc.nextInt();
        if(number==0){
            keepRunning=false;
        }
        System.out.println("You entered: " + number);

    }
}
