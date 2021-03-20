import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Frog {
    //controls movement of the main character and checks whether if is dead and give points and displays frog
    private int x, y;//(x,y) of frog
    private int [] controls;
    private int frogPos;//direction frog is facing
    public static final int UP=0, LEFT=1, RIGHT=2, DOWN=3;
    private Image up,left,right,down;//images for each direction
    private Image lifePic;//image for number of lives left
    private int life;//number of lives left
    private int points;//score
    private boolean dying;//whether to frog is on the process of dying
    private boolean drowning;//whether to frog is on the process of drowning
    private Image []deathImgs;//death on road animation
    private Image []drownImgs;//drown animation
    private int frame;

    public Frog(int [] con){
        controls = con;
        winReset();
    }

    public void winReset(){//reset for when frog reaches goal
        life = 3;
        points = 0;
        reset();
    }

    public void reset() {//reset after frog loses life
        x=280;//starts in bottom middle of screen
        y=570;

        up = new ImageIcon("up.png").getImage();
        left = new ImageIcon("left.png").getImage();
        right = new ImageIcon("right.png").getImage();
        down = new ImageIcon("down.png").getImage();
        lifePic = new ImageIcon("life.png").getImage();
        frogPos = UP;//default facing up

        dying = false;//whether frog is dying
        drowning = false;//whther frog is drowning
        deathImgs = new Image [3];
        drownImgs = new Image [3];
        for(int i=0; i<3; i++){
            deathImgs[i] = new ImageIcon("death_road_"+i+".png").getImage();
            drownImgs[i] = new ImageIcon("death_water_"+i+".png").getImage();
        }
        frame = 0;
    }

    public void move(KeyEvent e){//move frog based on keyboard
        if(!dying && !drowning){
            if (e.getKeyCode() == controls[UP] && y > 50) {
                y -= 41;//moving diferent directions on  the screen
                frogPos = UP;
                up = new ImageIcon("up_move.png").getImage();
                points += 10;//gains points everytime it is able to go up
            }
            if (e.getKeyCode() == controls[LEFT] && x > 40) {
                x -= 40;
                frogPos = LEFT;
                left = new ImageIcon("left_move.png").getImage();
            }
            if (e.getKeyCode() == controls[RIGHT] && x < 520) {
                x += 40;
                frogPos = RIGHT;
                right = new ImageIcon("right_move.png").getImage();
            }
            if (e.getKeyCode() == controls[DOWN] && y < 565) {
                y += 41;
                frogPos = DOWN;
                down = new ImageIcon("down_move.png").getImage();
            }
        }
    }

    public Rectangle getRect(){//returns rectangle of frog
        if(frogPos == UP || frogPos == DOWN) {//has different width and height whether up or down or sideways
            return new Rectangle(x, y, 30, 28);//rectangle of the where frog is
        }
        return new Rectangle(x, y, 28, 30);
    }

    public void loseLife(){//when frog loses a life
        life--;
        reset();
    }

    public boolean died(){//whether all frog lives have been used
        if(life <= 0){
            return true;
        }
        return false;
    }

    public void carCollide(Cars cars){//checks if cars has hit frog
        Rectangle frogRect = getRect();
        Rectangle []carRects = cars.getRect();//rectangles of all the cars

        for(int i=0;i<carRects.length;i++){
            if(carRects[i].intersects(frogRect)){//if it has hit
                dying = true;
            }
        }
    }

    public void waterMove(double speed){//movement on logs and turtles, speed is how fast that object is
        x+=speed;
        if(x > 560 || x < -30){//if frog goes out of bounds
            loseLife();
        }
    }

    public void drown() {
        drowning = true;
    }

    public void changePoints(double inc){//increse points by different amounts based on the situation
        points += inc;
    }

    public int getPoints(){//returns how many points
        return points;
    }

    public void draw(Graphics g, SoundEffect deathSound){//displaying everything
        if (dying) {
            deathSound.play();
            for(int i=0; i<3; i++){
                g.drawImage(deathImgs[frame], x, y, null);
            }
            frame ++;
            if (frame >= 3) {
                loseLife();//after animation is done frog loses life
            }
        }

        else if (drowning) {
            deathSound.play();
            for(int i=0; i<3; i++){
                g.drawImage(drownImgs[frame], x, y, null);
            }
            frame ++;
            if (frame >= 3) {
                loseLife();
            }
        }

        else{
            if (frogPos == UP) {//shows proper frog based on direction
                g.drawImage(up, x, y, null);
                up = new ImageIcon("up.png").getImage();
            }
            if (frogPos == LEFT) {
                g.drawImage(left, x, y, null);
                left = new ImageIcon("left.png").getImage();
            }
            if (frogPos == RIGHT) {
                g.drawImage(right, x, y, null);
                right = new ImageIcon("right.png").getImage();
            }
            if (frogPos == DOWN) {
                g.drawImage(down, x, y, null);
                down = new ImageIcon("down.png").getImage();
            }
        }

        for(int i=0; i<life; i++){//displaying lives
            g.drawImage(lifePic, i*15+5, 605, null);
        }
    }

}