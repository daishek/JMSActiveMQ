package queue_topic;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class QueueProducer {
    ActiveMQConnectionFactory connFactory;
    Connection conn = null;
    Session session = null;
    Destination destination;
    Message message;
    boolean useTransaction = false;
    MessageProducer producer = null;

    public QueueProducer() {
        try {
            connFactory = new ActiveMQConnectionFactory();
            conn = connFactory.createConnection();
            conn.start();

            session = conn.createSession(useTransaction, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("MyQueueForQT");
            producer = session.createProducer(destination);
            
            publish();
        }  catch (JMSException jmsEx) {

        }finally {
            try {
                producer.close();
                session.close();
                conn.close();
            } catch (JMSException jmsEx) {
                jmsEx.printStackTrace();
            }
        }
    }


    public void publish() throws JMSException {
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        String ligne = null;

        long currentTime, experationTime;

        int i = 1;
        while (i <= 10) {
            System.out.println("Input text =>");
            try {
                MyMsg msg = new MyMsg();
                ligne = entree.readLine();
                msg.setTexte(ligne);
                ObjectMessage _message = session.createObjectMessage(msg);
                

                /* delete a message after a certain time if no clients retrieve the message.*/
                currentTime = System.currentTimeMillis();
                // experationTime = currentTime + 10 * 60 * 1000; // expire after 10 minutes
                experationTime = currentTime + 10 * 1000; // expire after 10 seconds
                _message.setJMSExpiration(experationTime);
                
                producer.send(_message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new QueueProducer();
    }
}

