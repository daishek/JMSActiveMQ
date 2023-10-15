import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

public class Consumer {
    public Consumer(String destinationName) {
        ConnectionFactory connFactory = null;
        Connection conn = null;
        Session session = null;
        Destination destination;
        MessageConsumer consumer = null;
        boolean useTransaction = false;
        TextMessage message;
    
        try {
            connFactory = new ActiveMQConnectionFactory();
            destination = new ActiveMQTopic(destinationName);
            conn = connFactory.createConnection();
            conn.start();
            session = conn.createSession(useTransaction, Session.AUTO_ACKNOWLEDGE);
            consumer = session.createConsumer(destination);
            System.out.println("i am recieving...");
            message = (TextMessage) consumer.receive();
            System.out.println("Recieved Message: " + message.getText());
        } catch (JMSException jmsEx) {

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
        new Consumer("TopicByDai");
    }

}
