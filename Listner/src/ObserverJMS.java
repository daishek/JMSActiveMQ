import java.util.Properties;
import java.util.Random;

import javax.jms.Connection;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TopicSession;
import javax.naming.Context;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

public class ObserverJMS {
    private ActiveMQTopic destination;
    private MessageConsumer msgConsumer;
    protected Connection conn;
    
    public ObserverJMS(int clientNumber) {
        try {
            Properties props = new Properties();
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
            ActiveMQConnectionFactory connFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connFactory.setTrustAllPackages(true);
            conn = connFactory.createConnection();
            conn.start();

            destination = new ActiveMQTopic("durableListner");
            Session session = conn.createSession(true, TopicSession.AUTO_ACKNOWLEDGE);
            msgConsumer = session.createConsumer(destination);
            msgConsumer.setMessageListener(new ConsumerMsgListner());

            System.out.println("// Client // #" + clientNumber);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        System.out.println(clientNumber + "------------");
                        try {
                            Thread.sleep(1500);
                            
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            new Runnable() {
                @Override
                public void run() {
                    System.out.println(clientNumber + "------------");
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                }
            };

        } catch (Exception e) {
            e.printStackTrace();
        }
    } 

    public static void main (String[] args) {
        new ObserverJMS(new Random().nextInt());
    }
    
}
