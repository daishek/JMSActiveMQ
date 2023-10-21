package GUI;

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

public class SendToTopic {
    ActiveMQConnectionFactory connFactory;
    Connection conn = null;
    Session session = null;
    Destination destination;
    Message message;
    boolean useTransaction = false;
    MessageProducer producer = null;

    public SendToTopic(String inputMsg) {
        try {
            connFactory = new ActiveMQConnectionFactory();
            conn = connFactory.createConnection();
            conn.start();

            session = conn.createSession(useTransaction, Session.AUTO_ACKNOWLEDGE);
            destination = session.createTopic("MyTopicForQT");
            producer = session.createProducer(destination);
            
            publish(inputMsg);
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
    

    public void publish(String inputMsg) throws JMSException {

            try {
                MyMsg msg = new MyMsg();
                msg.setTexte(inputMsg);
                ObjectMessage _message = session.createObjectMessage(msg);
                producer.send(_message);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
