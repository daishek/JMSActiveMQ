import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {
    public Producer (String messageToSend) {
        ConnectionFactory connFactory;
        Connection conn = null;
        Session session = null;
        Destination destination;
        Message message;
        boolean useTransaction = false;
        MessageProducer producer = null;

        try {
            // So, this line of code creates an ActiveMQ connection factory configured to connect to the ActiveMQ broker at the default URL.
            // tcp://127.0.0.1:61616
            connFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
            conn = connFactory.createConnection();
            conn.start();
            session = conn.createSession(useTransaction, Session.AUTO_ACKNOWLEDGE);
            // session = conn.createSession(useTransaction, Session.CLIENT_ACKNOWLEDGE);
            destination = session.createQueue("MyQueue");
            producer = session.createProducer(destination);
            message = (Message) session.createTextMessage(messageToSend);
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
        // the consumer will reciev this messsages by order 01->02->03
        new Producer("Message 01");
        // new Producer("Message 02");
        // new Producer("Message 03");
    }

}

