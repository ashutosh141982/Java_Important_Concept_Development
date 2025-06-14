package com.ashutosh.mainImplementation.l3;


import com.ashutosh.threadpool.worker.implementation.level2.MyL2ThreadPool;
import com.ashutosh.threadpool.worker.implementation.level3.MyL3ThreadPool;
import com.ashutosh.threadpool.worker.implementation.level3.SaturationException;

import java.util.Scanner;

public class Level3Main {
    static boolean keepRunning=true;
    public static void main(String[] args) {
        try {

        System.out.println("Creation of thread saturation where once all the 5 threads are working into while(true) " +
                "will never complete and hence work from the workstaging Queue will nover be picked and " +
                "hence puttig new work in workstaging queue will be impossible waiting to be available" +
                "In this program will try identity such problem and will report");
        MyL3ThreadPool pool= MyL3ThreadPool.getInstance();
        pool.initialize(5,25);
        int i=0;

            for (; i < 25; i++) {
                boolean success = pool.addWork(new SimpleTestWork3("Work-" + i));
                if (!success) {
                    success = pool.addWork(new SimpleTestWork3("Work-" + i));
                    if (!success) {
                        success = pool.addWork(new SimpleTestWork3("Work-" + i));
                        if (!success) {
                            success = pool.addWork(new SimpleTestWork3("Work-" + i));
                        }
                    }
                }
            }

        System.out.println("First 25 started to have initial load");
        pool.startWorker();

        for(;i<500;i++){
            boolean success = pool.addWork(new SimpleTestWork3("Work-" + i));
            if (!success) {
                success = pool.addWork(new SimpleTestWork3("Work-" + i));
                if (!success) {
                    success = pool.addWork(new SimpleTestWork3("Work-" + i));
                    if (!success) {
                        success = pool.addWork(new SimpleTestWork3("Work-" + i));
                    }
                }
            }
        }
        System.out.println("All 500 Work added"+i);
        }catch(RuntimeException re){
            if(re.getClass()== SaturationException.class){
                System.out.println("Saturation Exception["+re.getMessage()+"] CAN'T ADD MORE WORK");
            }
        }

        Scanner sc = new Scanner(System.in);
        int number = sc.nextInt();
        if(number==0){
            keepRunning=false;
        }
        System.out.println("You entered: " + number);
    }
}
