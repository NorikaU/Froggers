import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class Log3 {
    //moves logs in final row and moves frog if it is on any of the logs and displays logs
    private int[] x;
    private int y;
    private Image log3;

    public Log3() {
        x = new int[3];
        for(int i=0; i<3; i++){
            x[i] = i * 205;
        }
        y = 117;

        log3 = new ImageIcon("log_med.png").getImage();
    }

    public void move(){//moving logs
        for(int i=0; i<3; i++){
            x[i]+=4;
            if(x[i] > 560){
                x[i] = -144;
            }
        }
    }

    public Rectangle[] getRect(){//rectangle of all logs
        Rectangle []log3Rect = new Rectangle[3];
        for(int i=0; i<3; i++){
            log3Rect[i] = new Rectangle(x[i], y, 144, 32);
        }
        return log3Rect;
    }

    public void moveFrog(Frog frog) {//move frog when its on a log
        Rectangle frogRect = frog.getRect();
        Rectangle[] log3Rect = getRect();
        Rectangle log3Row = new Rectangle(0, y, 560, 32);

        if (frogRect.intersects(log3Row)) {
            boolean onLog = false;

            for (Rectangle log : log3Rect) {
                if (log.intersects(frogRect)) {
                    frog.waterMove(4);
                    onLog = true;
                }
            }

            if (!onLog) {
                frog.drown();
            }
        }
    }

    public void draw(Graphics g){//display logs
        for(int i=0; i<3; i++){
            g.drawImage(log3, x[i], y, null);
        }
    }
}