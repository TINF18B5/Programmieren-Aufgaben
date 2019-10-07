package de.dhbwka.uebungen.snatchat;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

public class SnatChatRoom {
    
    private final String roomName;
    private final Set<SnatChatFrontend> frontends;
    
    public SnatChatRoom(String roomName) {
        this.roomName = roomName;
        frontends = new HashSet<>();
    }
    
    public String getRoomName() {
        return roomName;
    }
    
    public void register(SnatChatFrontend frontend) {
        if(frontends.add(frontend)) {
            this.readMessages().stream().map(Message::rot13).forEach(frontend::receiveMessage);
        }
    }
    
    public void unregister(SnatChatFrontend frontend) {
        frontends.remove(frontend);
    }
    
    public void sendMessage(Message msg) {
        for(SnatChatFrontend frontend : frontends) {
            frontend.receiveMessage(msg);
        }
        logMessage(msg.toString());
    }
    
    public void sendMessage(String text) {
        for(SnatChatFrontend frontend : frontends) {
            frontend.receiveMessage(text);
        }
        logMessage(text);
    }
    
    private void logMessage(String msg) {
        try(final BufferedWriter writer = Files.newBufferedWriter(Paths.get(this.getRoomName() + ".txt"), StandardCharsets.UTF_16, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(Message.rot13(msg));
            writer.newLine();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    private List<String> readMessages() {
        final Path path = Paths.get(this.getRoomName() + ".txt");
        
        try {
            final List<String> strings = Files.readAllLines(path, StandardCharsets.UTF_16);
            final int size = strings.size();
            return strings.subList(Math.max(0, size - 10), size);
        } catch(IOException e) {
            return Collections.emptyList();
        }
    }
}
