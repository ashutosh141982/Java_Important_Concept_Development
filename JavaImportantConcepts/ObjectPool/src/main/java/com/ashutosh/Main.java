package com.ashutosh;

import com.ashutosh.pool.polling.ObjectPoolManager;
import com.ashutosh.pool.poollingclass.Address;
import com.ashutosh.pool.poollingclass.Employee;
import com.ashutosh.pool.poollingclass.Student;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    static boolean keepRunning=true;
    public static void main(String[] args) {


        System.out.println("Hello world!");

        ObjectPoolManager.getInstance().add(new Employee(),1).add(new Student(),1).initialize();
        for(int i=0;i<20;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(Main.keepRunning){
                        try {
                            Employee e=(Employee)ObjectPoolManager.getInstance().getFromPool(Employee.class);
                            e.setEmployeeID(new Random().nextInt());
                            e.setEmployeeName("A"+e.getEmployeeID());
                            ArrayList<Number> list=new ArrayList<>();
                            list.add(12345);
                            list.add(445566);
                            list.add(990088);
                            e.setAddress(new Address("abcd"+e.getEmployeeID(),"Pune","MH","IN",list));
                            System.out.println(Thread.currentThread().getName()+"====="+e.toString());
                            System.out.println(Thread.currentThread().getName()+"returning back the object to pool");
                            CompletableFuture<String> done=ObjectPoolManager.getInstance().cacheToPoolAsynchronously(e);
                            System.out.println(Thread.currentThread().getName()+":return from Completable"+done.get());
                            //System.out.println(Thread.currentThread().getName()+"After returning the object back to pool==="+e.toString());

                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            },"My_Employee_" + i).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(Main.keepRunning){
                        try {
                            Student e=(Student)ObjectPoolManager.getInstance().getFromPool(Student.class);
                            e.setStudentID(new Random().nextInt());
                            e.setStudentName("A"+e.getStudentID());
                            ArrayList<Number> list=new ArrayList<>();
                            list.add(12345);
                            list.add(445566);
                            list.add(990088);
                            e.setAddress(new Address("abcd"+e.getStudentID(),"Pune","MH","IN",list));
                            System.out.println(Thread.currentThread().getName()+"====="+e.toString());
                            System.out.println("returning back the object to pool");
                            CompletableFuture<String> done=ObjectPoolManager.getInstance().cacheToPoolAsynchronously(e);
                            System.out.println(done.get());
                            System.out.println("After returning the object back to pool==="+e.toString());

                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            },"My_Student_" + i).start();
        }

        Scanner sc = new Scanner(System.in);
        int number = sc.nextInt();
        if(number==0){
            keepRunning=false;
        }
        System.out.println("You entered: " + number);

    }
}