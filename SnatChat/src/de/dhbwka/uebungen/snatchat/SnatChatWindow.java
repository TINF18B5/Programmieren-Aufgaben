package de.dhbwka.uebungen.snatchat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SnatChatWindow extends JFrame implements SnatChatFrontend {
    
    private final Account account;
    private final ChatMessagesComponent chatMessagesComponent;
    
    public SnatChatWindow(SnatChatRoom room, Account account) {
        super(String.format(Locale.ENGLISH, "%s (%s)", account.getName(), room.getRoomName()));
        this.account = account;
        
        final JLabel comp = new JLabel(account.getName());
        comp.setForeground(account.getColor());
        this.add(comp, BorderLayout.NORTH);
        
        
        final JPanel bottom = new JPanel(new BorderLayout(5, 5));
        final ButtonGroup buttonGroup = new ButtonGroup();
        
        
        final JPanel radioButtons = new JPanel();
        for(State value : State.values()) {
            final JRadioButton jRadioButton = new JRadioButton(value.getLabel(), value == this.account.getState());
            buttonGroup.add(jRadioButton);
            radioButtons.add(jRadioButton);
            jRadioButton.addActionListener(e -> {
                account.setState(value);
                room.sendMessage(String.format(Locale.ENGLISH, "The state of '%s' is now '%s'", account.getName(), account.getState().getLabel()));
            });
        }
        
        bottom.add(radioButtons, BorderLayout.CENTER);
        
        final JTextField messageText = new JTextField(10);
        final JButton send = new JButton("Send");
        final ActionListener sendMessageAction = e -> {
            final String text = messageText.getText();
            if(text.isEmpty()) {
                JOptionPane.showMessageDialog(this, String.format(Locale.ENGLISH, "Dear %s, please enter a message", this.account.getName()), "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                room.sendMessage(new Message(text, SnatChatWindow.this.account));
            }
            
            messageText.setText("");
        };
        send.addActionListener(sendMessageAction);
        messageText.addActionListener(sendMessageAction);
        
        final JPanel textInput = new JPanel();
        textInput.add(messageText);
        textInput.add(send);
        
        bottom.add(textInput, BorderLayout.SOUTH);
        this.chatMessagesComponent = new ChatMessagesComponent();
        this.add(chatMessagesComponent, BorderLayout.CENTER);
        this.add(bottom, BorderLayout.SOUTH);
    
    
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(800, 600);
        
        EventQueue.invokeLater(() -> SnatChatWindow.this.setVisible(true));
    }
    
    @Override
    public void receiveMessage(Message msg) {
        this.chatMessagesComponent.add(new MessageComponent(msg.getText(), msg.getSender()));
    }
    
    @Override
    public void receiveMessage(String msg) {
        this.chatMessagesComponent.add(new MessageComponent(msg));
    }
    
    @Override
    public Account getAccount() {
        return this.account;
    }
    
    private final class MessageComponent extends JPanel {
    
        private int countdown = 31;
        
        private MessageComponent(String text) {
            this(text, null);
        }
        
        private MessageComponent(String text, Account account) {
            super(new GridBagLayout());
            JLabel text1 = new JLabel(account == null ? text : account.getName() + ": " + text, SwingConstants.LEFT);
            JLabel counter = new JLabel(String.format("[%d]", countdown), SwingConstants.RIGHT);
            final Color color = account == null ? Color.GRAY : account.getColor();
            text1.setForeground(color);
            counter.setForeground(color);
            this.add(text1, new GridBagConstraints(0, 0, 1, 1, 10, 10, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 5, 5));
            this.add(counter, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 5, 5));
            new Thread(() -> {
                while(--countdown > 0) {
                    try {
                        counter.setText(String.format("[%d]", countdown));
                        Thread.sleep(1000, 0);
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                SnatChatWindow.this.chatMessagesComponent.remove(this);
            }).start();
        }
        
        
    }
}
