import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Cars {
    //moves all cars annd displays them
    private Image[] cars;//image of all cars
    private int [][]carsPos;//x positions of all cars
    private Random rand;//for generating random numbers
    public static final int LEFT=1, RIGHT=2;

    public Cars() {
        Random rand = new Random();

        carsPos = new int[5][3];//five rows with 3 cars each
        for(int i=0; i<carsPos.length; i++){//going through each item in array
            for(int j=0; j<carsPos[i].length;j++) {
                if (i % 2 == 0) {//for even rows
                    carsPos[i][j] = rand.nextInt(1120);//1120 is double the width of the screen
                    if(j>0) {
                        while (Math.abs(carsPos[i][j] - carsPos[i][j - 1]) < 75) {//makes sure random number won't be too close to car behind it
                            carsPos[i][j] = rand.nextInt(1120);
                        }
                    }
                    else{
                        while (Math.abs(carsPos[i][0] - carsPos[i][2]) < 75) {
                            carsPos[i][0] = rand.nextInt(1120);
                        }
                    }
                }
                else {
                    carsPos[i][j] = 560 - rand.nextInt(1120);//starts on left side instead of right
                    if(j>0) {
                        while (Math.abs(carsPos[i][j] - carsPos[i][j - 1]) < 75) {
                            carsPos[i][j] = 560 - rand.nextInt(1120);
                        }
                    }
                    else{
                        while (Math.abs(carsPos[i][0] - carsPos[i][2]) < 75) {
                            carsPos[i][0] = 560 - rand.nextInt(1120);
                        }
                    }
                }
            }
        }
        cars = new Image [5];
        for(int i=1; i<=5; i++){
            Image pic = new ImageIcon("car"+i+".png").getImage();//each row has different cars
            cars[i-1] = pic;
        }
    }

    public void move(){//moving all the cars
        for(int i=0; i<carsPos.length; i++){
            for(int j=0; j<carsPos[i].length;j++){
                if(i%2 == 0) {
                    carsPos[i][j]-=2;//moving left
                    if (carsPos[i][j] < -70){//if it is out of bounds goes back to beginning
                        carsPos[i][j] = 560;
                    }
                }

                else{
                    carsPos[i][j]+=2;//moving right
                    if(carsPos[i][j] > 560){
                        carsPos[i][j] = -50;//start at -50 so user doesn't see car going back
                    }
                }
            }
        }
    }

    public Rectangle[] getRect(){//gets rectangles of where all cars are
        Rectangle []carsRect = new Rectangle[15];//15 cars in total
        int pos = 0;
        for(int i=0; i<carsPos.length; i++){
            for(int carx: carsPos[i]){
                if(i == 0) {//add different sizes for each row
                    carsRect[pos] = new Rectangle(carx, 365, 59, 26);
                    pos++;
                }
                if(i == 1) {
                    carsRect[pos] = new Rectangle(carx, 405, 33, 29);
                    pos++;
                }
                if(i == 2) {
                    carsRect[pos] = new Rectangle(carx, 445, 34, 25);
                    pos++;
                }
                if(i == 3) {
                    carsRect[pos] = new Rectangle(carx, 485, 31, 30);
                    pos++;
                }
                if(i == 4) {
                    carsRect[pos] = new Rectangle(carx, 525, 36, 31);
                    pos++;
                }
            }
        }
        return carsRect;
    }

    public void draw(Graphics g){//displaying cars
        for(int i=0; i < carsPos.length; i++) {
            int y = (i+1) * 40 + 325;//y coord of each row
            for (int j=0; j<carsPos[i].length; j++) {
                g.drawImage(cars[i], carsPos[i][j], y, null);
            }
        }

    }

}