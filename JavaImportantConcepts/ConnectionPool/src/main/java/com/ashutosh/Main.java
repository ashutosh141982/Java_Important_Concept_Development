package com.ashutosh;

import com.ashutosh.resourcepooling.connection.DB.DBConnectionProxy;
import com.ashutosh.resourcepooling.connection.MQ.MQConnectionProxy;
import com.ashutosh.resourcepooling.connection.pooling.ResourcePoolManager;
import com.ibm.jms.JMSTextMessage;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

public class Main {
    static boolean keepRunning=true;
    public static void main(String[] args) {

        System.out.println("ResourcePolling");
        ResourcePoolManager mqManager= new ResourcePoolManager();
        mqManager.initialize(MQConnectionProxy.class,5);
        ResourcePoolManager dbManager= new ResourcePoolManager();
        dbManager.initialize(DBConnectionProxy.class,5);

        for(int i=0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        MQConnectionProxy mqCon=(MQConnectionProxy) mqManager.getResourceFromPool();
                        mqCon.openConnection();
                        try {
                            System.out.println(mqCon.getConsumer("Q.LOCAL.ASHU.RCV1").receive().toString());
                            MessageProducer mp=mqCon.getProducer("Q.LOCAL.ASHU.RCV2");
                            TextMessage tm=new JMSTextMessage();
                            tm.setText("Test mesaage");
                            mqCon.getProducer("Q.LOCAL.ASHU.RCV2").send(tm);
                            mqCon.closeConnection();
                        } catch (JMSException e) {

                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            },"MQ-Processing-Thread"+i);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        MQConnectionProxy mqCon=(MQConnectionProxy) mqManager.getResourceFromPool();
                        mqCon.openConnection();
                        try {
                            System.out.println(mqCon.getConsumer("Q.LOCAL.ASHU.RCV1").receive().toString());
                            MessageProducer mp=mqCon.getProducer("Q.LOCAL.ASHU.RCV2");
                            TextMessage tm=new JMSTextMessage();
                            tm.setText("TX MSG");
                            mqCon.getProducer("Q.LOCAL.ASHU.RCV2").send(tm);
                            mqCon.closeConnection();
                        } catch (JMSException e) {

                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            },"MQ-Processing-Thread"+i);
        }


    }
}