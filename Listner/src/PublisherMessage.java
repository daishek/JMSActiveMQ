import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class PublisherMessage {

    ActiveMQConnectionFactory connFactory;
    Connection conn = null;
    Session session = null;
    Destination destination;
    Message message;
    boolean useTransaction = false;
    MessageProducer producer = null;

    public PublisherMessage() {
        try {
            connFactory = new ActiveMQConnectionFactory();
            conn = connFactory.createConnection();
            conn.start();

            session = conn.createSession(useTransaction, Session.AUTO_ACKNOWLEDGE);
            destination = session.createTopic("durableListner");
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

        while (true) {
            System.out.println("Input text =>");
            try {
                MyMsg msg = new MyMsg();
                ligne = entree.readLine();
                msg.setTexte(ligne);
                ObjectMessage _message = session.createObjectMessage(msg);
                producer.send(_message);
                // break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new PublisherMessage();
    }
}
