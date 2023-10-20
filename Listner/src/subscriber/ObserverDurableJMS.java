package subscriber;

import java.util.Random;

import javax.jms.Connection;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

public class ObserverDurableJMS {
    private ActiveMQTopic destination;
    private MessageConsumer msgConsumer;
    private ActiveMQConnectionFactory connFactory;
    private Connection conn;
    private Session session;

    public ObserverDurableJMS(int clientNumber) {
        try {
            connFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
            connFactory.setTrustAllPackages(true);
            conn = connFactory.createConnection();
            conn.setClientID("dxk");
            conn.start();
            
            session = conn.createSession(false, session.AUTO_ACKNOWLEDGE);
            destination = new ActiveMQTopic("durableListner");
            connFactory.setTrustAllPackages(true);
            msgConsumer = session.createDurableSubscriber(destination, "dxk");
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


     public static void main (String[] args) throws InterruptedException {
        new ObserverDurableJMS(new Random().nextInt());
    }

}
