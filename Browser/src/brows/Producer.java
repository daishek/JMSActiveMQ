package brows;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Connection;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.JMSException;

public class Producer {
    public Producer(String destinationName) {
        ConnectionFactory connFactory;
        Connection conn = null;
        Session session = null;
        Destination destination;
        Message message;
        boolean useTransaction = false;
        MessageProducer producer = null;

        try {
            connFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
            conn = connFactory.createConnection();
            conn.start();
            session = conn.createSession(useTransaction, Session.AUTO_ACKNOWLEDGE);
            // session = conn.createSession(useTransaction, Session.CLIENT_ACKNOWLEDGE);
            destination = session.createQueue(destinationName);

            producer = session.createProducer(destination);
            message = (Message) session.createTextMessage("Message ONE");
            producer.send(message);

            producer = session.createProducer(destination);
            message = (Message) session.createTextMessage("Message TWO");
            producer.send(message);
            
            producer = session.createProducer(destination);
            message = (Message) session.createTextMessage("Message THREE");
            producer.send(message);

        } catch (JMSException jmsEx) {

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

    public static void main (String [] args) {
        new Producer("QBrowser");
    }
}
