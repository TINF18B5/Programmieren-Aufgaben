package de.dhbwka.uebungen.snatchat;

import java.util.*;

public class Message {
    
    private final String text;
    private final Account sender;
    
    public Message(String text, Account sender) {
        this.text = text;
        this.sender = sender;
    }
    
    public static String rot13(String message) {
        final StringBuilder sb = new StringBuilder();
        message.chars().map(myChar -> {
            final int lowerCase = Character.toLowerCase(myChar);
            if(lowerCase >= 'a' && lowerCase <= 'm') {
                return lowerCase + 13;
            } else if(lowerCase >= 'n' && lowerCase <= 'z') {
                return myChar - 13;
            }
            return myChar;
        }).forEach(c -> sb.append((char) c));
        return sb.toString();
    }
    
    public String getText() {
        return text;
    }
    
    public Account getSender() {
        return sender;
    }
    
    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%s: %s", this.sender.getName(), this.text);
    }
}
