import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class Log1 {
    //moves logs in second row and moves frog if it is on any of the logs and displays logs
    private int[] x;//x coords of this row's logs
    private int y;//y coord of this row logs
    private Image log1;//picture of log

    public Log1() {
        x = new int[3];
        for (int i=0; i<3; i++) {
            x[i] = i * 200;//spacing them apart
        }
        y = 240;

        log1 = new ImageIcon("log_smol.png").getImage();
    }

    public void move(){//moving logs
        for(int i=0; i<3; i++){//goes through each log and moves it at a certain speed
            x[i]+=2;
            if(x[i] > 560){//out of bounds
                x[i] = -96;
            }
        }
    }

    public Rectangle[] getRect(){//returns rectangles of where all the logs are
        Rectangle []log1Rect = new Rectangle[3];
        for(int i=0; i<3; i++){
            log1Rect[i] = new Rectangle(x[i], y, 96, 32);
        }
        return log1Rect;
    }

    public void moveFrog(Frog frog){//moves the frog with the log
        Rectangle frogRect = frog.getRect();
        Rectangle []log1Rect = getRect();
        Rectangle log1Row = new Rectangle(0,y,560,32);

        if(frogRect.intersects(log1Row)) {//when the frog reaches this row
            boolean onLog = false;//whether the frog is on any log

            for (Rectangle log : log1Rect) {
                if (log.intersects(frogRect)) {
                    frog.waterMove(2);//if it is on a log, it moves with it
                    onLog = true;
                }
            }

            if (!onLog) {//if not on a log it falls into water
                frog.drown();
            }
        }
    }

    public void draw(Graphics g){//displaying logs
        for(int i=0; i<3; i++){
            g.drawImage(log1, x[i], y, null);
        }
    }
}