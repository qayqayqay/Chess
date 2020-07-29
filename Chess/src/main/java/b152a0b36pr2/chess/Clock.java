/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package b152a0b36pr2.chess;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Chess clock
 * @author uzivatel
 */
public class Clock extends Thread {

    private int currentTime;
    private final int initialTime;
    private volatile boolean isRunning = true;
    private JPanel clock = new JPanel();
    private boolean endThread = false;
/**
 * Creates countdown for desired time
 * @param time desired maximal time in seconds
 */
    public Clock(int time) {
        initialTime = time;
        currentTime = time;
        clock.add(new JLabel(currentTime / 60 + ":" + currentTime % 60), 0);
    }

    @Override
    public synchronized void run() {
        while (!endThread) {
            while (isRunning) {
                clock.validate();
                clock.repaint();
                currentTime--;
                clock.remove(0);
                clock.add(new JLabel(currentTime / 60 + ":" + currentTime % 60));
                clock.validate();
                clock.repaint();
                try {
                    Clock.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Clock.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
/**
 * Pauses the countdown
 */
    public void stopClock() {
        this.isRunning = false;
    }
/**
 * Resumes the countdown
 */
    public void resumeClock() {
        this.isRunning = true;

    }
    /**
     * resets the countdown to miximal value set in constructor and pauses it
     */
    public void reset(){
        isRunning = false;
        clock.remove(0);
        clock.add(new JLabel(initialTime / 60 + ":" + initialTime % 60), 0);
        currentTime = initialTime;
    }
    public void endThread(){
        stopClock();
        endThread = true;
    }
/**
 * 
 * @return return the JPanel with countdown 
 */
    public JPanel getClock() {
        return clock;
    }

}
