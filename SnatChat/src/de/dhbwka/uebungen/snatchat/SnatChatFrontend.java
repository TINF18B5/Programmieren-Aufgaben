package de.dhbwka.uebungen.snatchat;

public interface SnatChatFrontend {
    
    void receiveMessage(Message msg);
    
    void receiveMessage(String msg);
    
    Account getAccount();
}
