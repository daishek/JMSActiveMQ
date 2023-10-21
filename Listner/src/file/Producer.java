package file;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {
    ConnectionFactory connFactory;
    Connection conn = null;
    Session session = null;
    Destination destination;
    BytesMessage message;
    boolean useTransaction = false;
    MessageProducer producer = null;
    byte[] fileData;
    
    public Producer(String filePath) {
        try {
            connFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
            conn = connFactory.createConnection();
            conn.start();

            session = conn.createSession(useTransaction, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("QueueForFiles");
            producer = session.createProducer(destination);

            message = session.createBytesMessage();
            System.out.println("# File Path -> " + filePath);
            
            fileData = Files.readAllBytes(Paths.get(filePath));
            System.out.println("# File Data -> " + fileData);
            
            message.writeBytes(fileData);

            producer.send(message);
        }  catch (Exception e) {

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

    public static void main (String[] args) {
        new Producer("C:\\Users\\Dai_Shek\\Desktop\\IOT\\test\\test.txt");
        // new Producer("C:\\Users\\Dai_Shek\\Downloads\\wallpapers\\dxk.jpg");
    }
}
