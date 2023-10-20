package listner;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class ConsumerMsgListner implements MessageListener {
    @Override
    public void onMessage(Message message) {
        ObjectMessage oMsg = (ObjectMessage) message;
        MyMsg msg = null;
        try {
            msg = (MyMsg) oMsg.getObject();
            System.out.println("Message recieved: " + msg.getTexte());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
