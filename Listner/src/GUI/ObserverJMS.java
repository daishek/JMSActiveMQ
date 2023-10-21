package GUI;

import java.util.Properties;
import java.util.Random;

import javax.jms.Connection;
import javax.jms.MessageConsumer;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TopicSession;
import javax.naming.Context;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

public class ObserverJMS {
    private ActiveMQTopic topicDestination;
    private ActiveMQQueue queueDestination;
    private MessageConsumer msgTopicConsumer;
    private MessageConsumer msgQueueConsumer;
    protected Connection conn;

    private Session topicSession, queueSession;
    
    public ObserverJMS(int clientNumber) {
        try {
            Properties props = new Properties();
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
            ActiveMQConnectionFactory connFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connFactory.setTrustAllPackages(true);
            conn = connFactory.createConnection();
            conn.start();

            /* recieve from 2 destinations [queue and topic] */
            topicDestination = new ActiveMQTopic("MyTopicForQT");
            queueDestination = new ActiveMQQueue("MyQueueForQT");

            queueSession = conn.createSession(false, QueueSession.AUTO_ACKNOWLEDGE);
            topicSession = conn.createSession(false, TopicSession.AUTO_ACKNOWLEDGE);

            // recieve messages from queue listner
            msgQueueConsumer = queueSession.createConsumer(queueDestination);
            msgQueueConsumer.setMessageListener(new ConsumerMsgListner());
            
            // recieve messages from topic listner
            msgTopicConsumer = topicSession.createConsumer(topicDestination);
            msgTopicConsumer.setMessageListener(new ConsumerMsgListner());

            System.out.println("// Client // #" + clientNumber);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        System.out.println(clientNumber + "------------");
                        try {
                            Thread.sleep(1500);
                            
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            // new Runnable() {
            //     @Override
            //     public void run() {
            //         System.out.println(clientNumber + "------------");
            //             try {
            //                 Thread.sleep(1500);
            //             } catch (InterruptedException e) {
            //                 e.printStackTrace();
            //             }
            //     }
            // };

        } catch (Exception e) {
            e.printStackTrace();
        }
    } 

    public static void main (String[] args) {
        new ObserverJMS(new Random().nextInt());
    }
    
}
