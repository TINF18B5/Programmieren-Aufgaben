package de.dhbw_ka.exam.stadtlandfluss;

import java.util.concurrent.atomic.*;

public class TimerThread extends Thread {
    
    
    private final int defaultTime;
    private final Game game;
    private final AtomicBoolean isStopped;
    private final AtomicInteger remainingTime;
    
    public TimerThread(int defaultTime, Game game) {
        this.defaultTime = defaultTime;
        this.game = game;
        this.remainingTime = new AtomicInteger(defaultTime);
        this.isStopped = new AtomicBoolean(true);
    }
    
    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                e.printStackTrace();
                return;
            }
            
            if(isStopped.get())
                continue;
            
            final int time = this.remainingTime.decrementAndGet();
            for(Sheet sheet : game.getSheets()) {
                sheet.setRemainingTime(time);
            }
            
            if(this.remainingTime.get() <= 0) {
                this.game.stopGame();
            }
        }
    }
    
    public void stopMe() {
        this.isStopped.set(true);
    }
    
    public void restartMe() {
        this.isStopped.set(false);
        this.remainingTime.set(defaultTime);
    }
}
