package GUI;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class ConsumerMsgListner implements MessageListener {
    // private String msgFrom;

    // public ConsumerMsgListner (String msgFrom) {
    //     this.msgFrom=msgFrom;
    // }

    @Override
    public void onMessage(Message message) {
        ObjectMessage oMsg = (ObjectMessage) message;
        MyMsg msg = null;
        try {
            msg = (MyMsg) oMsg.getObject();
            // System.out.println("Message recieved " + msgFrom + ": " + msg.getTexte());
            System.out.println("Message recieved: " + msg.getTexte());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
