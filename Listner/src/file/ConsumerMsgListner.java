package file;

import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class ConsumerMsgListner implements MessageListener {
    @Override
    public void onMessage(Message message) {
        if (message instanceof BytesMessage) {
            BytesMessage msg = (BytesMessage) message;

            try {
                byte[] fileData = new byte[(int) msg.getBodyLength()];
                
                
                
                if (msg.readBytes(fileData) == fileData.length) {
                    System.out.println("File received successfuly");
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
