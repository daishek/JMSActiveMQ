import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ProducerTopic {
    public ProducerTopic(String messageToSend, String topicName) {
        ConnectionFactory connFactory;
        Connection conn = null;
        Session session = null;
        Topic topic;
        Message message;
        boolean useTransaction = false;
        MessageProducer publisher = null;

        try {
            connFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
            conn = connFactory.createConnection();
            conn.start();
            session = conn.createSession(useTransaction, Session.AUTO_ACKNOWLEDGE);
            topic = session.createTopic(topicName);
            publisher = session.createProducer(topic);
            message = (Message) session.createTextMessage(messageToSend);
            publisher.send(message);
        } catch (JMSException jmsEx) {

        }finally {
            try {
                publisher.close();
                session.close();
                conn.close();
            } catch (JMSException jmsEx) {
                jmsEx.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Creating topic message...");
        new ProducerTopic("Hello Cosumers from TopicByDai", "TopicByDai");
    }
}
