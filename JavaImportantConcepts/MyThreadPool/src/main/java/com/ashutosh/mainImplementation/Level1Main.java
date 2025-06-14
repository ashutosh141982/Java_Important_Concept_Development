package com.ashutosh.mainImplementation;

import com.ashutosh.threadpool.worker.implementation.level1.MyL1ThreadPool;


import java.util.Scanner;

public class Level1Main {
    static boolean keepRunning=true;
    public static void main(String[] args) {


        MyL1ThreadPool pool= MyL1ThreadPool.getInstance();
        pool.initialize(5);
       /* for(int i=0;i<100;i++){
            try {
                Thread t=pool.getOneThreadFromPool();
                if(null==t){
                    System.out.println("Thread returned as NULL"+i);
                    continue;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/


        Scanner sc = new Scanner(System.in);
        int number = sc.nextInt();
        if(number==0){
            keepRunning=false;
        }
        System.out.println("You entered: " + number);
    }
}
