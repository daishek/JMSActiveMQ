package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame {
    private JPanel          panel;
    private JTextField      messageField;
    private JRadioButton    queueRadioButton;
    private JRadioButton    topicRadioButton;
    private ButtonGroup     radioGroup;
    private JButton         sendButton;

    public App() {
        setTitle("JMS App");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new FlowLayout());

        messageField = new JTextField(50);
        panel.add(messageField);

        queueRadioButton = new JRadioButton("Queue");
        topicRadioButton = new JRadioButton("Topic");
        radioGroup = new ButtonGroup();
        radioGroup.add(queueRadioButton);
        radioGroup.add(topicRadioButton);
        panel.add(queueRadioButton);
        panel.add(topicRadioButton);

        sendButton = new JButton("Send");
        panel.add(sendButton);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        getContentPane().add(panel);
    }


    private void sendMessage() {
        String msg = messageField.getText();

        if (queueRadioButton.isSelected()) {
            new SendToQueue(msg);
        }else if (topicRadioButton.isSelected()) {
            new SendToTopic(msg);
        } else {
            JOptionPane.showMessageDialog(this, "Select either Topic or Queue.");   
        }
    }

    public static void main (String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                new App().setVisible(true);
            }
        });
    }
}
