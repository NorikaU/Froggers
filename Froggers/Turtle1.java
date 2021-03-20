import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class Turtle1 {
    //class for moving the turtles, making sure it dives, and displaying its animation
    private int[] x;//x coords of all turtles
    private int y;//y coord of this row
    private Random rand;
    private Image[] turtles1;//images of turtle animations
    private int frame = 0;//frame of animation
    private int diveTime;//when a group of turtles will dive
    private boolean dive;//whether the turtles are diving
    private Image []diveImgs;//diving animation

    public Turtle1() {
        rand = new Random();
        diveTime = rand.nextInt(5)+1;
        dive = false;

        x = new int[3];
        for (int i=0; i<3; i++) {
            x[i] = i * 194;//spacing them apart
        }
        y = 280;

        turtles1 = new Image[3];
        for(int i=0; i<3; i++) {
            turtles1[i] = new ImageIcon("3turt"+i+".png").getImage();//each fram of animation
        }
        diveImgs = new Image[5];
        for(int i=0; i<5; i++){
            diveImgs[i] = new ImageIcon("turtle_three_dive_"+i+".png").getImage();
        }
    }

    public void move(double time){//goes through each turtle and moves it at a certain speed. if it is diving time it will dive
        for(int i=0; i<3; i++){
            x[i]-=2;
            if(x[i] < -124){//out of bounds
                x[i] = 560;
            }
        }

        if (time % (diveTime*10) == 0){//after a certain amount of time they will dive
            frame = 0;//starts from beginning of animation
            dive = true;
        }
    }

    public Rectangle[] getRect(){//rectangles of all turtles in this row
        Rectangle []turt1Rect = new Rectangle[3];
        for(int i=0; i<3; i++){
            turt1Rect[i] = new Rectangle((int)(x[i]), y, 124, 32);
        }
        return turt1Rect;
    }

    public void moveFrog(Frog frog) {//move frog on turtle
        Rectangle frogRect = frog.getRect();
        Rectangle[] turt1Rect = getRect();
        Rectangle turt1Row = new Rectangle(0, y, 560, 32);

        if (frogRect.intersects(turt1Row)) {
            boolean onTurt = false;

            for (Rectangle turt : turt1Rect) {
                if (turt.intersects(frogRect)) {
                    if (turt == turt1Rect[0] && dive){//if it's diving then it does not hold the frog
                        frog.loseLife();
                    }
                    else {
                        frog.waterMove(-2);
                        onTurt = true;
                    }
                }
            }

            if (!onTurt) {
                frog.drown();
            }
        }
    }

    public void draw(Graphics g){//showing animations
        for(int i=0; i<3; i++) {
            if(dive && i == 0) {
                g.drawImage(diveImgs[frame / 6 % 5], x[0], y, null);
                if(frame / 6 % 5 == 4){
                    dive = false; // if the animation is finished
                }
            }
            else {
                g.drawImage(turtles1[frame / 4 % 3], x[i], y, null);
            }

            frame++;
        }
    }
}