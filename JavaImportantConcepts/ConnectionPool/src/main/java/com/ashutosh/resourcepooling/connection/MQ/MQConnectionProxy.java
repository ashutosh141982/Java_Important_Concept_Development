package com.ashutosh.resourcepooling.connection.MQ;

import com.ashutosh.resourcepooling.PoolingResource;
import com.ashutosh.resourcepooling.util.SequenceGenerator;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

public final class MQConnectionProxy implements PoolingResource {
    // Connection details
    private  String HOST = "localhost";
    private  int PORT = 1419;
    private  String SVRCON_CHANNEL = "SVRCONN.ASHU.LOCAL";
    private  String QMGR = "ASHU";
    private  String INPUT_QUEUE = "Q.LOCAL.RCV1";
    private  String OUTPUT_QUEUE = "Q.REMOTE.SND1";
    private  String USER = "loginid";
    private  String PASSWORD = "password";
    private boolean isUserNpassRequired=false;
    private int poolSize=1;
    private final int instanceIndex=SequenceGenerator.nextId();
    Session session;
final List<MessageProducer> producer=new ArrayList<>(10);
final List<MessageConsumer> consumer=new ArrayList<>(10);


    public MQConnectionProxy(String HOST, int PORT, String SVRCON_CHANNEL, String QMGR, String INPUT_QUEUE, String OUTPUT_QUEUE, String USER, String PASSWORD,boolean isUserNpassRequired,int poolSize) {
        this.HOST = HOST;
        this.PORT = PORT;
        this.SVRCON_CHANNEL = SVRCON_CHANNEL;
        this.QMGR = QMGR;
        this.INPUT_QUEUE = INPUT_QUEUE;
        this.OUTPUT_QUEUE = OUTPUT_QUEUE;
        this.USER = USER;
        this.PASSWORD = PASSWORD;
        this.isUserNpassRequired=isUserNpassRequired;
        if(poolSize>1){
            this.poolSize=poolSize;
        }
        //this.instanceIndex= SequenceGenerator.nextId();



    }
    public MQConnectionProxy(){
        this.session=getFreshConnection();
    }
    private Session getFreshConnection(){
        MQQueueConnectionFactory factory = new MQQueueConnectionFactory();

        try {
            // Step 1: Configure connection factory
            factory.setHostName(HOST);
            factory.setPort(PORT);
            factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            factory.setQueueManager(QMGR);
            factory.setChannel(SVRCON_CHANNEL);
            Connection connection;
            // Step 2: Create JMS Connection
            if(isUserNpassRequired) {
                connection= factory.createConnection(USER, PASSWORD);
            }else  {
                connection = factory.createConnection();
            }
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            return session;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void closeConnection() {

        try {
            this.session.commit();
            for(int i=0;i<this.producer.size();i++){
                this.producer.get(i).close();
            }
            this.producer.clear();
            for(int i=0;i<this.consumer.size();i++){
                this.consumer.get(i).close();
            }
            this.consumer.clear();

        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void openConnection() {

    }

    @Override
    public int getIndex() {
        return this.instanceIndex;
    }
    public MessageProducer getProducer(String remoteQueue){
        try {
            MessageProducer mp=this.session.createProducer(this.session.createQueue(remoteQueue));
            this.producer.add(mp);
           return mp;
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
    public MessageConsumer getConsumer(String localQueue){
        try {

            MessageConsumer mc=this.session.createConsumer(this.session.createQueue(localQueue));
            this.consumer.add(mc);
            return mc;
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
