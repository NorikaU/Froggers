import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class Goal {
    //class that deals with the final row nd checks if user land in safe are or not, displays lilypads and safe turtles
    private Image lily;//image of lilypad
    private Rectangle []safe;//where the frog is safe
    private Image []safeFrogs;//pics of frogs that are safe
    private Image happyFrog;//image of safe turtle

    public Goal() {
        lily = new ImageIcon("lilypad.png").getImage();
        reset();
    }

    public void reset(){
        safe = new Rectangle[5];
        safeFrogs = new Image[5];
        for(int i=0; i<5; i++){
            safe[i] = new Rectangle(i*112+52, 68, 10, 40);//
            safeFrogs[i] = new ImageIcon("invis.png").getImage();//filled with transparent pictures, changes to frog after it is safe
        }
        happyFrog = new ImageIcon("goal_frog.png").getImage();
    }

    public void safety(Frog frog, Time time, SoundEffect goalSound){//checks if frog is in a safe region
        Rectangle frogRect = frog.getRect();
        Rectangle goalRow = new Rectangle(0, 68, 560, 40);
        boolean isSafe = false;//whether the frog is in a safe area

        if(frogRect.intersects(goalRow)) {
            for (int i = 0; i < 5; i++) {
                if (safe[i].intersects(frogRect)) {
                    if (safeFrogs[i] != happyFrog){//if a frog hasn't already landed there
                        frog.changePoints(50 + time.get());//50 points for reaching safety + how much time user had left
                        safeFrogs[i] = happyFrog;
                        isSafe = true;
                        goalSound.play();
                    }
                    else{
                        frog.loseLife();
                    }
                    frog.reset();//back to starting position
                    time.restart();//back to full time
                }
            }
            if (!isSafe) {
                frog.drown();
            }
        }
    }

    public boolean complete(){//check if all 5 froge made it home
        int frogs = 0;//how many safe frogs
        for(Image f: safeFrogs){
            if (f.equals(happyFrog)){
                frogs++;
            }
        }
        if(frogs == 5){//if all five are safe then it is complete
            reset();
            return true;
        }
        return false;
    }

    public void highScore(Frog frog){
        String name = JOptionPane.showInputDialog("Name:");
        try {
            File oldHighs = new File("highScore.txt");
            File highs = File.createTempFile("blankHS", ".txt");

            BufferedReader reader = new BufferedReader(new FileReader(oldHighs));
            BufferedWriter output = new BufferedWriter(new FileWriter(highs, true));

            String line = reader.readLine();
            while (line != null) {
                if (Integer.parseInt(line.split(" ")[1]) < frog.getPoints()) {
                    output.append(name + " " + frog.getPoints());
                    output.newLine();
                    frog.changePoints(0-frog.getPoints());
                } else {
                    output.append(line);
                    output.newLine();
                    line = reader.readLine();
                }
            }

            reader.close();
            output.close();
            oldHighs.delete();
            highs.renameTo(oldHighs);

        }catch (Exception e) {
            System.out.println("error");
        }
    }

    public void draw(Graphics g) {//display lilypads and safe frogs
        for(int i=0; i<5; i++) {
            g.drawImage(lily, i*112+38, 72, null);
            g.drawImage(safeFrogs[i], i*112+40, 68, null);
        }
    }
}