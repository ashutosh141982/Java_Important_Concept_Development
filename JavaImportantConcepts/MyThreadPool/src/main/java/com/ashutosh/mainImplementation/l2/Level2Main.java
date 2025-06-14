package com.ashutosh.mainImplementation.l2;


import com.ashutosh.threadpool.worker.implementation.level2.MyL2ThreadPool;

import java.util.Scanner;

public class Level2Main {
    static boolean keepRunning=true;
    public static void main(String[] args) {


        MyL2ThreadPool pool= MyL2ThreadPool.getInstance();
        pool.initialize(5,25);
        int i=0;
        for(;i<25;i++){
            pool.addWork(new SimpleTestWork("Work-"+i));
        }
        System.out.println("First 25 started to have initial load");
        pool.startWorker();

        for(;i<500;i++){
                pool.addWork(new SimpleTestWork("Work-"+i));
        }
        System.out.println("All 500 Work added"+i);


        Scanner sc = new Scanner(System.in);
        int number = sc.nextInt();
        if(number==0){
            keepRunning=false;
        }
        System.out.println("You entered: " + number);
    }
}
