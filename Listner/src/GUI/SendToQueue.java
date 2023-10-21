package GUI;


import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;


public class SendToQueue {
    ActiveMQConnectionFactory connFactory;
    Connection conn = null;
    Session session = null;
    Destination destination;
    Message message;
    boolean useTransaction = false;
    MessageProducer producer = null;

    public SendToQueue(String inputMsg) {
        try {
            connFactory = new ActiveMQConnectionFactory();
            conn = connFactory.createConnection();
            conn.start();

            session = conn.createSession(useTransaction, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("MyQueueForQT");
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


    public void publish(String inputMeg) throws JMSException {

        long timeToLive;

        try {
            MyMsg msg = new MyMsg();
            msg.setTexte(inputMeg);
            ObjectMessage _message = session.createObjectMessage(msg);
            
            /* delete a message after a certain time if no clients retrieve the message.*/
            timeToLive = 1000; // expire after 5 seconds
            producer.setTimeToLive(timeToLive);
            
            producer.send(_message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
