import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class Turtle2 {
    //class for moving the turtles, making sure it dives, and displaying its animation
    private int[] x;
    private int y;
    private Random rand;
    private Image[] turtles2;
    private int frame = 0;
    private int diveTime;
    private boolean dive;
    private Image []diveImgs;

    public Turtle2() {
        rand = new Random();
        diveTime = rand.nextInt(5)+1;
        dive = false;

        x = new int[3];
        for (int i=0; i<3; i++) {
            x[i] = i * 164;
        }
        y = 160;

        turtles2 = new Image[3];
        for(int i=0; i<3; i++) {
            turtles2[i] = new ImageIcon("turtle_two_"+i+".png").getImage();
        }
        diveImgs = new Image[5];
        for(int i=0; i<5; i++){
            diveImgs[i] = new ImageIcon("turtle_two_dive_"+i+".png").getImage();
        }
    }

    public void move(double time){//move turtle
        for(int i=0; i<3; i++){
            x[i]-=4;
            if(x[i] < -82){//out of bounds
                x[i] = 560;
            }
        }

        if (time % (diveTime*10) == 0){
            frame = 0;
            dive = true;
        }
    }

    public Rectangle[] getRect(){//get rects for all turtles
        Rectangle []turt2Rect = new Rectangle[3];
        for(int i=0; i<3; i++){
            turt2Rect[i] = new Rectangle((int)(x[i]), y, 82, 32);
        }
        return turt2Rect;
    }

    public void moveFrog(Frog frog) {//move frog on the turtle
        Rectangle frogRect = frog.getRect();
        Rectangle[] turt2Rect = getRect();
        Rectangle turt2Row = new Rectangle(0, y, 560, 32);

        if (frogRect.intersects(turt2Row)) {
            boolean onTurt = false;

            for (Rectangle turt : turt2Rect) {

                if (turt.intersects(frogRect)) {
                    if (turt == turt2Rect[0] && dive) {//frog drown if turtles are diving
                        frog.loseLife();
                    }
                    else {
                        frog.waterMove(-4);
                        onTurt = true;
                    }
                }
            }

            if (!onTurt) {
                frog.drown();
            }
        }
    }

    public void draw(Graphics g){//showing the animations
        for(int i=0; i<3; i++) {
            if(dive && i == 0) {
                g.drawImage(diveImgs[frame / 6 % 5], x[0], y, null);
                if(frame / 6 % 5 == 2){
                    dive = false; // if the animation is finished
                }
            }
            else {
                g.drawImage(turtles2[frame / 4 % 3], x[i], y, null);
            }

            frame++;
        }
    }
}