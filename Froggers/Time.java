import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class Time {
    //clas that deals with increasing time and displaying how much time is left
    private double time;//how much time has passed
    private double width;//width of timer

    public Timed(){
        restart();
    }

    public void restart(){//restart timer
        time = 0;//time starts at 0
        width = 300;//full length is 300
    }

    public void increase(){//increase time
        time++;
    }

    public double get(){//returns value of time
        return time;
    }

    public void countDown(SoundEffect death, SoundEffect lowTime){//finds what the width of the countdown should be
        width = 300 - (time/60000.0)*100*300;
        if(width == 0) {
            death.play();
        }
        if(width == 30){
            lowTime.play();
        }
    }

    public void draw(Graphics g,Font fnt){//displays countdown
        g.setFont(fnt);
        g.setColor(Color.white);
        g.drawString("TIME", 100, 628);
        if(width <= 30){
            g.setColor(Color.red);
        }
        else {
            g.setColor(Color.green);
        }
        g.fillRect(150, 610, (int)(width), 20);
    }
}