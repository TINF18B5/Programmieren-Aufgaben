package de.dhbwka.klausuren.stadtlandfluss;

import java.util.concurrent.atomic.*;

public class MyTimerThread extends Thread {
    
    private final int defaultTime;
    private final Game game;
    private final AtomicInteger currentTime;
    private final AtomicBoolean running;
    
    public MyTimerThread(int defaultTime, Game game) {
        super("SLF Timer");
        this.defaultTime = defaultTime;
        this.game = game;
        this.running = new AtomicBoolean(false);
        this.currentTime = new AtomicInteger(defaultTime);
    }
    
    public void startMyTimer() {
        this.currentTime.set(defaultTime);
        this.running.set(true);
    }
    
    public void stopMyTimer() {
        this.running.set(false);
    }
    
    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException ignored) {
            }
            
            if(!running.get()) {
                continue;
            }
            
            final int i = currentTime.decrementAndGet();
            game.updateTime(i);
            
            
            if(currentTime.get() <= 0) {
                game.stopGame();
                this.running.set(false);
            }
        }
    }
    
    public boolean isRunning() {
        return running.get();
    }
}
