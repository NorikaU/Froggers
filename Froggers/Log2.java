import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class Log2 {
    //moves logs in third row and moves frog if it is on any of the logs and displays logs
    private int[] x;//all x positions
    private int y;
    private Image log2;

    public Log2() {
        x = new int[2];
        for (int i=0; i<2; i++) {
            x[i] = i * 280;
        }
        y = 200;

        log2 = new ImageIcon("log_big.png").getImage();
    }

    public void move(){//moving logs
        for(int i=0; i<2; i++){
            x[i]+=6;//faster than other logs
            if(x[i] > 560){
                x[i] = -192;
            }
        }
    }

    public Rectangle[] getRect(){//rectangle of logs in this row
        Rectangle []log2Rect = new Rectangle[2];
        for(int i=0; i<2; i++){
            log2Rect[i] = new Rectangle(x[i], y, 192, 32);
        }
        return log2Rect;
    }

    public void moveFrog(Frog frog){//move frog on log
        Rectangle frogRect = frog.getRect();
        Rectangle []log2Rect = getRect();
        Rectangle log2Row = new Rectangle(0,y,560,32);

        if(frogRect.intersects(log2Row)) {
            boolean onLog = false;

            for (Rectangle log : log2Rect) {
                if (log.intersects(frogRect)) {
                    frog.waterMove(6);
                    onLog = true;
                }
            }

            if (!onLog) {
                frog.drown();
            }
        }
    }

    public void draw(Graphics g){//display logs
        for(int i=0; i<2; i++){
            g.drawImage(log2, x[i], y, null);
        }
    }
}