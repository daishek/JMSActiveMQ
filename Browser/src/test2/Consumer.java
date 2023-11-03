package test2;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class Consumer {
    private Queue queue;
    private Session session;
    TextMessage message;
    public Consumer(String destinationName) {
        ConnectionFactory connFactory;
        Connection conn = null;
        session = null;
        Destination destination;
        MessageConsumer consumer = null;

        try {
            connFactory = new ActiveMQConnectionFactory();
            destination = new ActiveMQQueue(destinationName);
            conn = connFactory.createConnection();
            conn.setClientID("WHATS_MY_PURPOSE");
            conn.start();

            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            String selector = "req_type = 'temperature'";
            consumer = session.createConsumer(destination, selector);
            System.out.println("Recieving temperature...");
            
            message = (TextMessage) consumer.receive();
            System.out.println("Message: " + message.getText());
            
            message = (TextMessage) consumer.receive();
            System.out.println("Message: " + message.getText());

        
        }catch (JMSException jmsEx) {

        }finally {
            try {
                consumer.close();
                session.close();
                conn.close();
            } catch (JMSException jmsEx) {
                jmsEx.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        new Consumer("QBrowser");
        }
}
